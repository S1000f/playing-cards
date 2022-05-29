package game

interface Pile {
    fun shuffle(): Pile
    fun size(): Int
}