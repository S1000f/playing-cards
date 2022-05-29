package game.card.playingcards

import game.card.Card
import game.card.Deck

class PlayingCardDeck(private val list: MutableList<PlayingCard>) : Deck<PlayingCard> {
    override fun shuffle(): PlayingCardDeck = PlayingCardDeck(list.shuffled().toMutableList())

    override fun size(): Int = list.size

    override fun draw(count: Int, index: Int): List<Card> {
        val draws = list.filterIndexed { i, _ -> i >= index }
            .take(count)
            .toList()

        list.removeAll(draws)

        return draws
    }

    override fun toString(): String = list.toString()

    companion object
}
