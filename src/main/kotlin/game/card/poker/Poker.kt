package game.card.poker

import game.Game
import game.card.playingcards.*
import game.card.playingcards.FrenchRank.*

interface Poker : Game<FrenchCard>

enum class PokerRank(override val label: String, override val order: Int) : Poker, Rank {
    HIGH_CARD("High card", 1) {
        override fun <T : PlayingCard<FrenchSuit, FrenchRank>> match(cards: Collection<T>): Pair<PokerRank, List<T>>? {
            TODO("Not yet implemented")
        }
    },

    ONE_PAIR("One pair", 2) {
        override fun <T : PlayingCard<FrenchSuit, FrenchRank>> match(cards: Collection<T>): Pair<PokerRank, List<T>>? {
            TODO("Not yet implemented")
        }
    },

    TWO_PAIR("Two pair", 3) {
        override fun <T : PlayingCard<FrenchSuit, FrenchRank>> match(cards: Collection<T>): Pair<PokerRank, List<T>>? {
            TODO("Not yet implemented")
        }
    },

    THREE_OF_A_KIND("Trips", 4) {
        override fun <T : PlayingCard<FrenchSuit, FrenchRank>> match(cards: Collection<T>): Pair<PokerRank, List<T>>? {
            if (cards.size < 5) return null

            with(cards.groupBy { it.rank.order }) {
                if (any { it.value.size > 3 || it.value.size == 2 } || !any { it.value.size == 3 }) {
                    return null
                }

                val trips = filterValues { it.size == 3 }
                    .values
                    .maxByOrNull { it.first().rank.order } ?: return null

                val apply = cards.toMutableList()
                    .apply { removeAll(trips) }
                    .sortedByDescending { it.rank.order }
                    .take(2)

                return THREE_OF_A_KIND to trips.toMutableList().apply { addAll(3, apply) }
            }
        }
    },

    STRAIGHT("Straight", 5) {
        override fun <T : PlayingCard<FrenchSuit, FrenchRank>> match(cards: Collection<T>): Pair<PokerRank, List<T>>? {
            val distinct = cards.distinctBy { it.rank.order }

            if (distinct.size < 5) return null

            with(distinct.sortedByDescending { card -> card.rank.order }) {
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
        override fun <T : PlayingCard<FrenchSuit, FrenchRank>> match(cards: Collection<T>): Pair<PokerRank, List<T>>? {
            if (cards.size < 5) return null

            return cards.groupBy { it.suit }
                .filterValues { it.size >= 5 }
                .map { it.value }
                .map { it.sortedByDescending { card -> card.rank.order }.take(5) }
                .maxWithOrNull { a, b ->
                    compareValuesBy(
                        a,
                        b,
                        { x: List<T> -> x.component1().rank.order },
                        { x: List<T> -> x.component2().rank.order },
                        { x: List<T> -> x.component3().rank.order },
                        { x: List<T> -> x.component4().rank.order },
                        { x: List<T> -> x.component5().rank.order })
                }?.let { this to it }
        }
    },

    FULL_HOUSE("Full house", 7) {
        override fun <T : PlayingCard<FrenchSuit, FrenchRank>> match(cards: Collection<T>): Pair<PokerRank, List<T>>? {
            TODO("Not yet implemented")
        }
    },

    FOUR_OF_A_KIND("Quads", 8) {
        override fun <T : PlayingCard<FrenchSuit, FrenchRank>> match(cards: Collection<T>): Pair<PokerRank, List<T>>? {
            if (cards.size < 5) return null

            with(cards.sortedByDescending { it.rank.order }) {
                return withIndex()
                    .any { it.value.rank.order * 4 == sumSkipTake(4, it.index) }
                    .let { if (it) FOUR_OF_A_KIND to this else null }
            }
        }
    },

    STRAIGHT_FLUSH("Straight flush", 9) {
        override fun <T : PlayingCard<FrenchSuit, FrenchRank>> match(cards: Collection<T>): Pair<PokerRank, List<T>>? {
            val filter = cards.groupBy { it.suit }
                .filterValues { it.size >= 5 }
                .map { it.value }
                .filter { STRAIGHT.match(it) != null }

            val list = filter.find { it.first().rank == ACE }
                ?: filter.maxByOrNull { it.first().rank.order }
                ?: return null

            return this to list
        }
    };

    abstract fun <T : PlayingCard<FrenchSuit, FrenchRank>> match(cards: Collection<T>): Pair<PokerRank, List<T>>?

    companion object {
        fun <T : PlayingCard<FrenchSuit, FrenchRank>> rank(cards: Collection<T>): Pair<PokerRank, List<T>>? {
            val cardsCopy = cards.toMutableList()

            return STRAIGHT_FLUSH.match(cardsCopy)
                ?: FOUR_OF_A_KIND.match(cardsCopy)
                ?: FLUSH.match(cardsCopy)
                ?: STRAIGHT.match(cardsCopy)
                ?: THREE_OF_A_KIND.match(cardsCopy)
        }
    }

}