package game.card

interface Deck<Card> {
    fun shuffle(): Deck<Card>
    fun size(): Int
    fun draw(count: Int = 1, index: Int = 0): Pair<Deck<Card>, List<Card>>
}