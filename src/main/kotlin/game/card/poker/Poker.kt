package game.card.poker

import game.Game
import game.card.playingcards.*
import game.card.playingcards.FrenchRank.*

interface Poker : Game<FrenchCard> {

    companion object {
        fun showdown(hand1: PokerHand, hand2: PokerHand): Int {
            val toList = hand1.kicker()
                .indices
                .map { { x: PokerHand -> x.kicker()[it] } }
                .toList()

            return compareValuesBy(hand1, hand2, { it.rank()?.value }, *toList.toTypedArray())
        }
    }
}

enum class PokerRank(override val label: String, override val value: Int, override val order: Int) : Poker, Rank {
    HIGH_CARD("High card", 1, 9) {
        override fun <T : PlayingCard<FrenchSuit, FrenchRank>> match(cards: Collection<T>): Pair<PokerRank, List<T>>? {
            with(cards) {
                if (size < 5) return null

                if (groupBy { it.rank.value }.filterValues { it.size >= 2 }.any()) return null

                if (groupBy { it.suit }.filterValues { it.size == 5 }.any()) return null

                val sorted = sortedByDescending { it.rank.value }

                for ((index, item) in sorted.withIndex()) {
                    if (sumRankStreak(item) == sorted.sumSkipTake(5, skip = index)) {
                        return null
                    }
                }

                return HIGH_CARD to sorted
            }
        }

        override fun <T : PlayingCard<FrenchSuit, FrenchRank>> getKicker(cards: List<T>): List<Int> {
            return match(cards)
                ?.second
                ?.map { it.rank.value }
                ?.toList() ?: emptyList()
        }
    },

    ONE_PAIR("One pair", 2, 8) {
        override fun <T : PlayingCard<FrenchSuit, FrenchRank>> match(cards: Collection<T>): Pair<PokerRank, List<T>>? {
            if (cards.size < 5) return null

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

        override fun <T : PlayingCard<FrenchSuit, FrenchRank>> getKicker(cards: List<T>): List<Int> {
            return match(cards)
                ?.second
                ?.map { it.rank.value }
                ?.drop(1)
                ?.toList() ?: emptyList()
        }
    },

    TWO_PAIR("Two pair", 3, 7) {
        override fun <T : PlayingCard<FrenchSuit, FrenchRank>> match(cards: Collection<T>): Pair<PokerRank, List<T>>? {
            if (cards.size < 5) return null

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

        override fun <T : PlayingCard<FrenchSuit, FrenchRank>> getKicker(cards: List<T>): List<Int> {
            return match(cards)
                ?.second
                ?.map { it.rank.value }
                ?.slice(0..4 step 2)
                ?.toList() ?: emptyList()
        }
    },

    THREE_OF_A_KIND("Trips", 4, 6) {
        override fun <T : PlayingCard<FrenchSuit, FrenchRank>> match(cards: Collection<T>): Pair<PokerRank, List<T>>? {
            if (cards.size < 5) return null

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

        override fun <T : PlayingCard<FrenchSuit, FrenchRank>> getKicker(cards: List<T>): List<Int> {
            return match(cards)
                ?.second
                ?.map { it.rank.value }
                ?.drop(2)
                ?.toList() ?: emptyList()
        }
    },

    STRAIGHT("Straight", 5, 5) {
        override fun <T : PlayingCard<FrenchSuit, FrenchRank>> match(cards: Collection<T>): Pair<PokerRank, List<T>>? {
            val distinct = cards.distinctBy { it.rank.value }

            if (distinct.size < 5) return null

            with(distinct.sortedByDescending { card -> card.rank.value }) {
                for ((index, item) in withIndex()) {
                    if (sumRankStreak(item) == sumSkipTake(5, skip = index)) {
                        return STRAIGHT to drop(index).take(5)
                    }
                }

                if (map { it.rank }.containsAll(setOf(FIVE, FOUR, THREE, TWO, ACE))) {
                    return find { it.rank == ACE }
                        ?.let {
                            STRAIGHT to sortedBy { it.rank.order }
                                .take(5)
                                .asReversed()
                                .toMutableList()
                        }
                }
            }

            return null
        }

        override fun <T : PlayingCard<FrenchSuit, FrenchRank>> getKicker(cards: List<T>): List<Int> {
            return match(cards)
                ?.second
                ?.first()
                ?.let { listOf(it.rank.value) } ?: emptyList()
        }
    },

    FLUSH("Flush", 6, 4) {
        override fun <T : PlayingCard<FrenchSuit, FrenchRank>> match(cards: Collection<T>): Pair<PokerRank, List<T>>? {
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

        override fun <T : PlayingCard<FrenchSuit, FrenchRank>> getKicker(cards: List<T>): List<Int> {
            return match(cards)
                ?.second
                ?.map { it.rank.value }
                ?.toList() ?: emptyList()
        }
    },

    FULL_HOUSE("Full house", 7, 3) {
        override fun <T : PlayingCard<FrenchSuit, FrenchRank>> match(cards: Collection<T>): Pair<PokerRank, List<T>>? {
            with(cards) {
                if (size < 5) return null

                if (groupBy { it.suit }.filterValues { it.size >= 5 }.any()) return null

                val pairs = groupBy { it.rank.value }

                val trips = pairs.filterValues { it.size == 3 }
                    .values
                    .maxByOrNull { it.first().rank.value } ?: return null

                val twoPair = pairs.filterValues { it.size == 2 }
                    .values
                    .maxByOrNull { it.first().rank.value } ?: return null

                return FULL_HOUSE to trips + twoPair
            }
        }

        override fun <T : PlayingCard<FrenchSuit, FrenchRank>> getKicker(cards: List<T>): List<Int> {
            return match(cards)
                ?.second
                ?.map { it.rank.value }
                ?.let { listOf(it.first(), it.last()) } ?: emptyList()
        }
    },

    FOUR_OF_A_KIND("Quads", 8, 2) {
        override fun <T : PlayingCard<FrenchSuit, FrenchRank>> match(cards: Collection<T>): Pair<PokerRank, List<T>>? {
            if (cards.size < 5) return null

            with(cards.groupBy { it.rank.value }) {
                if (!any { it.value.size == 4 }) {
                    return null
                }

                val fourCard = filterValues { it.size == 4 }
                    .values
                    .maxByOrNull { it.first().rank.value } ?: return null

                val apply = cards.toMutableList()
                    .apply { removeAll(fourCard) }
                    .sortedByDescending { it.rank.value }
                    .take(1)

                return FOUR_OF_A_KIND to fourCard.toMutableList().apply { addAll(4, apply) }
            }
        }

        override fun <T : PlayingCard<FrenchSuit, FrenchRank>> getKicker(cards: List<T>): List<Int> {
            return match(cards)
                ?.second
                ?.map { it.rank.value }
                ?.let { listOf(it.first(), it.last()) } ?: emptyList()
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

        override fun <T : PlayingCard<FrenchSuit, FrenchRank>> getKicker(cards: List<T>): List<Int> {
            return match(cards)
                ?.second
                ?.first()
                ?.let { listOf(it.rank.value) } ?: emptyList()
        }
    };

    abstract fun <T : PlayingCard<FrenchSuit, FrenchRank>> match(cards: Collection<T>): Pair<PokerRank, List<T>>?

    abstract fun <T : PlayingCard<FrenchSuit, FrenchRank>> getKicker(cards: List<T>): List<Int>

    companion object {
        fun <T : PlayingCard<FrenchSuit, FrenchRank>> rank(cards: Collection<T>): Pair<PokerRank, List<T>>? {
            val cardsCopy = cards.toMutableList()

            return STRAIGHT_FLUSH.match(cardsCopy)
                ?: FOUR_OF_A_KIND.match(cardsCopy)
                ?: FULL_HOUSE.match(cardsCopy)
                ?: FLUSH.match(cardsCopy)
                ?: STRAIGHT.match(cardsCopy)
                ?: THREE_OF_A_KIND.match(cardsCopy)
                ?: TWO_PAIR.match(cardsCopy)
                ?: ONE_PAIR.match(cardsCopy)
                ?: HIGH_CARD.match(cardsCopy)
        }
    }

}