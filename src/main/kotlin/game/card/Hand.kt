package game.card

interface Hand<Card> {
    fun count(): Int
    fun add(element: Card): Boolean
    fun addAll(elements: Collection<Card>): Boolean
}