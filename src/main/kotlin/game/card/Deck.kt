package game.card

import game.Pile

interface Deck<T : Card> : Pile {
    fun draw(count: Int = 1, index: Int = 0): List<Card>
}