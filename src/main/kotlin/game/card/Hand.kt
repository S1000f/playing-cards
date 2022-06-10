package game.card

interface Hand<Card> {
    fun count(): Int
    fun add(element: Card): Hand<Card>
    fun addAll(elements: Collection<Card>): Hand<Card>
}