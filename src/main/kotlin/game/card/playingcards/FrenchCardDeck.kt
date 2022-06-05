package game.card.playingcards

import game.card.Deck

data class FrenchCardDeck(private val list: MutableList<FrenchCard>) : Deck<FrenchCard> {
    override fun shuffle(): FrenchCardDeck = FrenchCardDeck(list.shuffled().toMutableList())

    override fun size(): Int = list.size

    override fun draw(count: Int, index: Int): List<FrenchCard> {
        val draws = list.filterIndexed { i, _ -> i >= index }
            .take(count)
            .toList()

        list.removeAll(draws)

        return draws
    }

    override fun toString(): String = list.toString()

    companion object {
        fun of(vararg pair: Pair<FrenchSuit, FrenchRank>): FrenchCardDeck =
            FrenchCardDeck(pair.map { FrenchCard(it) }.toMutableList())
    }
}
