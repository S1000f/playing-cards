package game.card.poker

import game.card.Card
import game.card.Hand
import game.card.playingcards.FrenchRank
import game.card.playingcards.FrenchSuit
import game.card.playingcards.FrenchCard

data class PokerHand(private val cards: List<FrenchCard>) : Hand<FrenchCard>, List<FrenchCard> by cards {
    private val ranking: Pair<PokerRank, List<FrenchCard>>? = PokerRank.rank(cards)

    companion object {
        fun of(vararg pair: Pair<FrenchSuit, FrenchRank>) = PokerHand(pair.map { FrenchCard(it) }.toList())

        fun of(cards: List<FrenchCard>) = PokerHand(cards.toList())

        fun empty() = PokerHand(listOf())
    }

    fun rank(): PokerRank? = ranking?.first

    fun kicker(): List<Int> = rank()?.getKicker(ranking?.second ?: emptyList()) ?: emptyList()
    fun add(card: Pair<FrenchSuit, FrenchRank>) = add(FrenchCard(card))
    fun addAll(vararg cards: Pair<FrenchSuit, FrenchRank>) = addAll(cards.map { FrenchCard(it) })

    override fun add(card: FrenchCard) = of(this.cards.toMutableList().apply { add(card) }.toList())

    override fun addAll(cards: Collection<FrenchCard>) = of(this.cards.toMutableList().apply { addAll(cards) }.toList())

    override fun count() = cards.size

    override fun toString(): String {
        return """
            ${cards}
            ranking: ${ranking}
        """.trimIndent()
    }
}

operator fun PokerHand.plus(x: FrenchCard) = this.add(x)
operator fun FrenchCard.plus(x: PokerHand) = x.add(this)
