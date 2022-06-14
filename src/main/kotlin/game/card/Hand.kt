package game.card

/**
 * `Hand` is a pack of cards that players or a dealer hold in their hand. It offers some functions that allow to
 * add or remove cards. An instance from implements of this should be immutable.
 */
interface Hand<Card> {
    /**
     * Returns the number of cards in a `Hand`. The number may be more than five.
     */
    fun count(): Int

    /**
     * Returns a new instance with the card added. It should not alter the state of original instance.
     */
    fun add(card: Card): Hand<Card>

    /**
     * Returns a new instance with the cards added. It should not alter the state of original instance.
     */
    fun addAll(cards: Collection<Card>): Hand<Card>

    /**
     * Returns a new instance and a new `List` of cards wrapped by `Pair`. It should not alter the state of original instance.
     * @param count the number of cards to be drawn. Default is 1.
     * @param index position to start draw cards. Default is 0.
     */
    fun draw(count: Int = 1, index: Int = 0): Pair<Hand<Card>, List<Card>>
}

operator fun <T : Card> Hand<T>.plus(x: T) = this.add(x)
operator fun <T : Card> T.plus(x: Hand<T>) = x.add(this)
operator fun <T : Card> Hand<T>.minus(x: Int) = this.draw(x)