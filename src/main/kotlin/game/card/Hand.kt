package game.card

interface Hand<Card> {
    fun count(): Int
    fun add(card: Card): Hand<Card>
    fun addAll(cards: Collection<Card>): Hand<Card>
}

operator fun <T : Card> Hand<T>.plus(x: T) = this.add(x)
operator fun <T : Card> T.plus(x: Hand<T>) = x.add(this)