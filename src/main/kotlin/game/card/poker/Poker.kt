package game.card.poker

import game.Game
import game.card.Rank
import game.card.playingcards.FrenchRank.*
import game.card.playingcards.PlayingCard
import tool.sumSkipTake
import java.lang.Integer.max

interface Poker : Game<PlayingCard>

fun sumRankStreak(card: PlayingCard, streak: Int = 5): Int {
    val rank = card.rank.order
    var sum = 0
    for (i in rank downTo max(rank - (streak - 1), 0)) {
        sum += i
    }

    return sum
}

fun Collection<PlayingCard>.sumSkipTake(take: Int, skip: Int = 0) = sumSkipTake(take, skip) { x -> x.rank.order }

enum class PokerRank(override val label: String, override val order: Int) : Poker, Rank {
    HIGH_CARD("High card", 1) {
        override fun match(cards: Collection<PlayingCard>): Pair<PokerRank, List<PlayingCard>>? {
            TODO("Not yet implemented")
        }
    },

    ONE_PAIR("One pair", 2) {
        override fun match(cards: Collection<PlayingCard>): Pair<PokerRank, List<PlayingCard>>? {
            TODO("Not yet implemented")
        }
    },

    TWO_PAIR("Two pair", 3) {
        override fun match(cards: Collection<PlayingCard>): Pair<PokerRank, List<PlayingCard>>? {
            TODO("Not yet implemented")
        }
    },

    THREE_OF_A_KIND("Trips", 4) {
        override fun match(cards: Collection<PlayingCard>): Pair<PokerRank, List<PlayingCard>>? {
            TODO("Not yet implemented")
        }
    },

    STRAIGHT("Straight", 5) {
        override fun match(cards: Collection<PlayingCard>): Pair<PokerRank, List<PlayingCard>>? {
            if (cards.size < 5) return null

            with(cards.sortedByDescending { card -> card.rank.order }) {
                for ((index, item) in withIndex()) {
                    if (sumRankStreak(item) == sumSkipTake(5, skip = index)) {
                        return STRAIGHT to this
                    }
                }

                if (map { it.rank }.containsAll(setOf(ACE, KING))
                    && sumRankStreak(first(), 4) == sumSkipTake(4)
                ) {
                    return find { it.rank == ACE }
                        ?.let { STRAIGHT to take(4).toMutableList().apply { add(0, it) } }
                }
            }

            return null
        }
    },

    FLUSH("Flush", 6) {
        override fun match(cards: Collection<PlayingCard>): Pair<PokerRank, List<PlayingCard>>? {
            if (cards.size < 5) return null

            return cards.groupBy { it.suit }
                .filterValues { it.size >= 5 }
                .map { it.value }
                .map { it.sortedByDescending { card -> card.rank.order }.take(5) }
                .maxWithOrNull { a, b ->
                    compareValuesBy(
                        a,
                        b,
                        { x: List<PlayingCard> -> x.component1().rank.order },
                        { x: List<PlayingCard> -> x.component2().rank.order },
                        { x: List<PlayingCard> -> x.component3().rank.order },
                        { x: List<PlayingCard> -> x.component4().rank.order },
                        { x: List<PlayingCard> -> x.component5().rank.order })
                }?.let { this to it }
        }
    },

    FULL_HOUSE("Full house", 7) {
        override fun match(cards: Collection<PlayingCard>): Pair<PokerRank, List<PlayingCard>>? {
            TODO("Not yet implemented")
        }
    },

    FOUR_OF_A_KIND("Quads", 8) {
        override fun match(cards: Collection<PlayingCard>): Pair<PokerRank, List<PlayingCard>>? {
            if (cards.size < 5) return null

            with(cards.sortedByDescending { it.rank.order }) {
                return withIndex()
                    .any { it.value.rank.order * 4 == sumSkipTake(4, it.index) }
                    .let { if (it) FOUR_OF_A_KIND to this else null }
            }
        }
    },

    STRAIGHT_FLUSH("Straight flush", 9) {
        override fun match(cards: Collection<PlayingCard>): Pair<PokerRank, List<PlayingCard>>? {
            return STRAIGHT.match(cards)
                ?.second
                ?.let { FLUSH.match(it) }
                ?.second
                ?.let { this to it }
        }
    };

    abstract fun match(cards: Collection<PlayingCard>): Pair<PokerRank, List<PlayingCard>>?

    companion object {
        fun rank(cards: Collection<PlayingCard>): Pair<PokerRank, List<PlayingCard>>? {
            val cardsCopy = cards.toMutableList()

            return STRAIGHT_FLUSH.match(cardsCopy)
                ?: FOUR_OF_A_KIND.match(cardsCopy)
                ?: FLUSH.match(cardsCopy)
                ?: STRAIGHT.match(cardsCopy)
        }
    }

}