package game.card.playingcards

import game.card.Deck

data class FrenchCardDeck(private val list: MutableList<FrenchCard>) : Deck<FrenchCard> {
    override fun shuffle() = FrenchCardDeck(list.shuffled().toMutableList())

    override fun size() = list.size

    override fun draw(count: Int, index: Int) = list.filterIndexed { i, _ -> i >= index }
        .take(count)
        .toList()
        .also { list.removeAll(it) }

    override fun toString(): String = list.toString()

    companion object {
        fun of(vararg pair: Pair<FrenchSuit, FrenchRank>) = FrenchCardDeck(pair.map { FrenchCard(it) }.toMutableList())
    }
}
