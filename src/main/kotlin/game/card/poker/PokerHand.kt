package game.card.poker

import game.card.Hand
import game.card.playingcards.FrenchRank
import game.card.playingcards.FrenchSuit
import game.card.playingcards.FrenchCard

data class PokerHand(private val cards: MutableList<FrenchCard>) : Hand<FrenchCard> {
    private val ranking: Pair<PokerRank, List<FrenchCard>>? = PokerRank.rank(cards)

    companion object {
        fun of(vararg pair: Pair<FrenchSuit, FrenchRank>) = PokerHand(pair.map { FrenchCard(it) }.toMutableList())

        fun of(cards: List<FrenchCard>) = PokerHand(cards.toMutableList())
    }

    fun rank(): PokerRank? = ranking?.first

    fun kicker(): List<Int> = rank()?.getKicker(ranking?.second ?: emptyList()) ?: emptyList()

    override fun size(): Int = cards.size

    override fun toString(): String {
        return """
            ${cards}
            ranking: ${ranking}
        """.trimIndent()
    }
}