package game.card.poker

import game.card.Hand
import game.card.playingcards.PlayingCard

data class PokerHand(private val cards: MutableList<PlayingCard>) : Hand<PlayingCard> {
    private val ranks: Pair<PokerRank, List<PlayingCard>>? = PokerRank.rank(cards)

    override fun size(): Int = cards.size

    override fun toString(): String {
        return """
            ${cards}
            ranking: ${ranks}
        """.trimIndent()
    }
}