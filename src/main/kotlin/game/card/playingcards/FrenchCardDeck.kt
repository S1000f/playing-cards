package game.card.playingcards

import game.card.Deck

data class FrenchCardDeck(private val cards: List<FrenchCard>) : Deck<FrenchCard> {
    companion object {
        fun of(vararg pair: Pair<FrenchSuit, FrenchRank>) = FrenchCardDeck(pair.map { FrenchCard(it) }.toList())
    }

    override fun shuffle() = FrenchCardDeck(cards.shuffled().toList())

    override fun size() = cards.size

    override fun draw(count: Int, index: Int) =
        with(cards.filterIndexed { i, _ -> i >= index }
            .take(count)
            .toList()) {
            FrenchCardDeck(cards.toMutableList().apply { removeAll(this@with) }.toList()) to this
        }

    override fun toString(): String = cards.toString()

}
