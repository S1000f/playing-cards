package game.card.poker

import game.card.Hand
import game.card.playingcards.Rank
import game.card.playingcards.FrenchRank
import game.card.playingcards.FrenchSuit
import game.card.playingcards.FrenchCard

data class PokerHand(private val cards: MutableList<FrenchCard>) : Hand<FrenchCard> {
    private val ranks: Pair<PokerRank, List<FrenchCard>>? = PokerRank.rank(cards)

    companion object {
        fun of(vararg pair: Pair<FrenchSuit, FrenchRank>): PokerHand =
            PokerHand(pair.map { FrenchCard(it) }.toMutableList())
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