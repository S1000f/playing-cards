package game.card.poker

import game.card.Hand
import game.card.playingcards.FrenchRank
import game.card.playingcards.FrenchSuit
import game.card.playingcards.FrenchCard

data class PokerHand(private val cards: MutableList<FrenchCard>) : Hand<FrenchCard>, MutableList<FrenchCard> by cards {
    private var ranking: Pair<PokerRank, List<FrenchCard>>? = PokerRank.rank(cards)

    companion object {
        fun of(vararg pair: Pair<FrenchSuit, FrenchRank>) = PokerHand(pair.map { FrenchCard(it) }.toMutableList())

        fun of(cards: List<FrenchCard>) = PokerHand(cards.toMutableList())

        fun empty() = PokerHand(mutableListOf())
    }

    fun rank(): PokerRank? {
        ranking = PokerRank.rank(cards)
        return ranking?.first
    }

    fun kicker(): List<Int> = rank()?.getKicker(ranking?.second ?: emptyList()) ?: emptyList()
    fun add(card: Pair<FrenchSuit, FrenchRank>) = add(FrenchCard(card))
    fun addAll(vararg cards: Pair<FrenchSuit, FrenchRank>) = addAll(cards.map { FrenchCard(it) })

    override fun count() = cards.size

    override fun toString(): String {
        rank()
        return """
            ${cards}
            ranking: ${ranking}
        """.trimIndent()
    }
}