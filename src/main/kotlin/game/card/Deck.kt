package game.card

/**
 * It is a pack of cards. Players or rooms of the game both can have a `Deck` while only players can have a `Hand`.
 * It supports a shuffle feature which is meaningless for `Hand` cause an owner of `Hand` knows all the cards in their hands.
 * @see Hand
 */
interface Deck<Card> {
    /**
     * Returns a new instance with a shuffled cards. It should not alter the state of original instance.
     */
    fun shuffle(): Deck<Card>

    /**
     * Returns the number of cards in a `Deck`.
     */
    fun size(): Int

    /**
     * Returns a new instance and a new `List` of cards wrapped by `Pair`. It should not alter the state of original instance.
     * @param count the number of cards to be drawn. Default is 1.
     * @param index position to start draw cards. Default is 0.
     */
    fun draw(count: Int = 1, index: Int = 0): Pair<Deck<Card>, List<Card>>
}

operator fun <T : Card> Deck<T>.minus(x: Int) = this.draw(x)