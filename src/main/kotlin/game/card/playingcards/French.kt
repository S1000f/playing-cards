package game.card.playingcards

enum class FrenchSuit(override val label: String, override val unicode: String) : Suit {
    SPADE("s", "\u2660"),
    HEART("h", "\u2665"),
    DIAMOND("d", "\u2666"),
    CLUB("c", "\u2663")
}

enum class FrenchRank(override val label: String, override val order: Int) : Pip, Face {
    ACE("A", 1),
    TWO("2", 2),
    THREE("3", 3),
    FOUR("4", 4),
    FIVE("5", 5),
    SIX("6", 6),
    SEVEN("7", 7),
    EIGHT("8", 8),
    NINE("9", 9),
    TEN("10", 10),
    JACK("J", 11),
    QUEEN("Q", 12),
    KING("K", 13);

    companion object {
        fun findByOrder(order: Int): FrenchRank = FrenchRank.values().first { it.order == order }
    }
}

fun FrenchCardDeck.Companion.standard52(order: List<Pair<FrenchSuit, IntProgression>>): FrenchCardDeck {
    val list = mutableListOf<FrenchCard>()
    order.forEach {
        for (i in it.second) {
            list.add(FrenchCard(it.first to FrenchRank.findByOrder(i)))
        }
    }

    return FrenchCardDeck(list)
}

fun FrenchCardDeck.Companion.standard52(): FrenchCardDeck {
    val order = mutableListOf<Pair<FrenchSuit, IntProgression>>()
    val asc = 1..13
    val desc = 13 downTo 1

    order.add(FrenchSuit.SPADE to asc)
    order.add(FrenchSuit.DIAMOND to asc)
    order.add(FrenchSuit.CLUB to desc)
    order.add(FrenchSuit.HEART to desc)

    return standard52(order)
}