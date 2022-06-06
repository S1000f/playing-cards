package game.card.poker

import game.Game
import game.card.playingcards.*
import game.card.playingcards.FrenchRank.*

interface Poker : Game<FrenchCard>

enum class PokerRank(override val label: String, override val value: Int, override val order: Int) : Poker, Rank {
    HIGH_CARD("High card", 1, 9) {
        override fun <T : PlayingCard<FrenchSuit, FrenchRank>> match(cards: Collection<T>): Pair<PokerRank, List<T>>? {
            TODO("Not yet implemented")
        }
    },

    ONE_PAIR("One pair", 2, 8) {
        override fun <T : PlayingCard<FrenchSuit, FrenchRank>> match(cards: Collection<T>): Pair<PokerRank, List<T>>? {
            if (cards.size < 2) return null

            with(cards.groupBy { it.rank.value }
                .filterValues { it.size == 2 }.values.sortedByDescending { it.first().rank.value }) {

                if (size != 1) return null

                val kickers = cards.toMutableList()
                    .apply { removeAll(flatten()) }
                    .sortedByDescending { it.rank.value }
                    .take(3)

                return ONE_PAIR to take(1)
                    .flatten()
                    .toMutableList()
                    .apply { addAll(kickers) }
            }
        }
    },

    TWO_PAIR("Two pair", 3, 7) {
        override fun <T : PlayingCard<FrenchSuit, FrenchRank>> match(cards: Collection<T>): Pair<PokerRank, List<T>>? {
            if (cards.size < 4) return null

            with(cards.groupBy { it.rank.value }
                .filterValues { it.size == 2 }.values.sortedByDescending { it.first().rank.value }) {

                if (size < 2) return null

                val first = cards.toMutableList()
                    .apply { removeAll(flatten()) }
                    .sortedByDescending { it.rank.value }
                    .take(1)

                return TWO_PAIR to take(2)
                    .flatten()
                    .toMutableList()
                    .apply { addAll(first) }
            }
        }
    },

    THREE_OF_A_KIND("Trips", 4, 6) {
        override fun <T : PlayingCard<FrenchSuit, FrenchRank>> match(cards: Collection<T>): Pair<PokerRank, List<T>>? {
            if (cards.size < 3) return null

            with(cards.groupBy { it.rank.value }) {
                if (any { it.value.size > 3 || it.value.size == 2 } || !any { it.value.size == 3 }) {
                    return null
                }

                val trips = filterValues { it.size == 3 }
                    .values
                    .maxByOrNull { it.first().rank.value } ?: return null

                val apply = cards.toMutableList()
                    .apply { removeAll(trips) }
                    .sortedByDescending { it.rank.value }
                    .take(2)

                return THREE_OF_A_KIND to trips.toMutableList().apply { addAll(3, apply) }
            }
        }
    },

    STRAIGHT("Straight", 5, 5) {
        override fun <T : PlayingCard<FrenchSuit, FrenchRank>> match(cards: Collection<T>): Pair<PokerRank, List<T>>? {
            val distinct = cards.distinctBy { it.rank.value }

            if (distinct.size < 5) return null

            with(distinct.sortedByDescending { card -> card.rank.value }) {
                for ((index, item) in withIndex()) {
                    if (sumRankStreak(item) == sumSkipTake(5, skip = index)) {
                        return STRAIGHT to this
                    }
                }

                if (map { it.rank }.containsAll(setOf(FIVE, FOUR, THREE, TWO, ACE))) {
                    return find { it.rank == ACE }
                        ?.let {
                            STRAIGHT to sortedBy { it.rank.order }
                                .take(5)
                                .asReversed()
                                .toMutableList()
                                .apply { add(0, it) }
                        }
                }
            }

            return null
        }
    },

    FLUSH("Flush", 6, 4) {
        override fun <T : PlayingCard<FrenchSuit, FrenchRank>> match(cards: Collection<T>): Pair<PokerRank, List<T>>? {
            if (cards.size < 5) return null

            return cards.groupBy { it.suit }
                .filterValues { it.size >= 5 }
                .map { it.value }
                .map { it.sortedByDescending { card -> card.rank.value }.take(5) }
                .maxWithOrNull { a, b ->
                    compareValuesBy(
                        a,
                        b,
                        { x: List<T> -> x.component1().rank.value },
                        { x: List<T> -> x.component2().rank.value },
                        { x: List<T> -> x.component3().rank.value },
                        { x: List<T> -> x.component4().rank.value },
                        { x: List<T> -> x.component5().rank.value })
                }?.let { this to it }
        }
    },

    FULL_HOUSE("Full house", 7, 3) {
        override fun <T : PlayingCard<FrenchSuit, FrenchRank>> match(cards: Collection<T>): Pair<PokerRank, List<T>>? {
            TODO("Not yet implemented")
        }
    },

    FOUR_OF_A_KIND("Quads", 8, 2) {
        override fun <T : PlayingCard<FrenchSuit, FrenchRank>> match(cards: Collection<T>): Pair<PokerRank, List<T>>? {
            if (cards.size < 5) return null

            with(cards.sortedByDescending { it.rank.value }) {
                return withIndex()
                    .any { it.value.rank.value * 4 == sumSkipTake(4, it.index) }
                    .let { if (it) FOUR_OF_A_KIND to this else null }
            }
        }
    },

    STRAIGHT_FLUSH("Straight flush", 9, 1) {
        override fun <T : PlayingCard<FrenchSuit, FrenchRank>> match(cards: Collection<T>): Pair<PokerRank, List<T>>? {
            val filter = cards.groupBy { it.suit }
                .filterValues { it.size >= 5 }
                .map { it.value }
                .filter { STRAIGHT.match(it) != null }

            val list = filter.maxByOrNull { it.first().rank.value }
                ?.sortedByDescending { it.rank.value }
                ?.take(5)
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
                ?: TWO_PAIR.match(cardsCopy)
                ?: ONE_PAIR.match(cardsCopy)
        }
    }

}