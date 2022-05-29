package game.card.poker

import game.Game
import game.card.Rank
import game.card.playingcards.PlayingCard

interface Poker : Game<PlayingCard>

enum class PokerRank(override val label: String, override val order: Int) : Poker, Rank {
    STRAIGHT_FLUSH("Straight flush", 1) {
        override fun match(cards: Collection<PlayingCard>): Pair<PokerRank, List<PlayingCard>>? {
            TODO()
        }
    },

    FOUR_OF_A_KIND("Quads", 2) {
        override fun match(cards: Collection<PlayingCard>): Pair<PokerRank, List<PlayingCard>>? {
            TODO("Not yet implemented")
        }
    },

    FULL_HOUSE("Full house", 3) {
        override fun match(cards: Collection<PlayingCard>): Pair<PokerRank, List<PlayingCard>>? {
            TODO("Not yet implemented")
        }
    },

    FLUSH("Flush", 4) {
        override fun match(cards: Collection<PlayingCard>): Pair<PokerRank, List<PlayingCard>>? {
            cards.groupBy { it.suit }
                .filterValues { it.size >= 5 }
                .map { it.value }
                .map { it.sortedByDescending { card -> card.rank.order } }
                .
        }
    },

    STRAIGHT("Straight", 5) {
        override fun match(cards: Collection<PlayingCard>): Pair<PokerRank, List<PlayingCard>>? {
            TODO("Not yet implemented")
        }
    },

    THREE_OF_A_KIND("Trips", 6) {
        override fun match(cards: Collection<PlayingCard>): Pair<PokerRank, List<PlayingCard>>? {
            TODO("Not yet implemented")
        }
    },

    TWO_PAIR("Two pair", 7) {
        override fun match(cards: Collection<PlayingCard>): Pair<PokerRank, List<PlayingCard>>? {
            TODO("Not yet implemented")
        }
    },

    ONE_PAIR("One pair", 8) {
        override fun match(cards: Collection<PlayingCard>): Pair<PokerRank, List<PlayingCard>>? {
            TODO("Not yet implemented")
        }
    },

    HIGH_CARD("High card", 9) {
        override fun match(cards: Collection<PlayingCard>): Pair<PokerRank, List<PlayingCard>>? {
            TODO("Not yet implemented")
        }
    };

    abstract fun match(cards: Collection<PlayingCard>): Pair<PokerRank, List<PlayingCard>>?

    companion object {
        fun rank(cards: Collection<PlayingCard>): MutableList<Pair<PokerRank, List<PlayingCard>>> {

        }
    }

}