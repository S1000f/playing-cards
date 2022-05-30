package game.card.poker

import game.card.Hand
import game.card.Rank
import game.card.playingcards.FrenchRank
import game.card.playingcards.FrenchSuit
import game.card.playingcards.PlayingCard

data class PokerHand(private val cards: MutableList<PlayingCard>) : Hand<PlayingCard> {
    private val ranks: Pair<PokerRank, List<PlayingCard>>? = PokerRank.rank(cards)

    companion object {
        fun of(vararg pair: Pair<FrenchSuit, FrenchRank>): PokerHand =
            PokerHand(pair.map { PlayingCard(it) }.toMutableList())
    }

    fun rank(): Rank? = ranks?.first

    override fun size(): Int = cards.size

    override fun toString(): String {
        return """
            ${cards}
            ranking: ${ranks}
        """.trimIndent()
    }
}