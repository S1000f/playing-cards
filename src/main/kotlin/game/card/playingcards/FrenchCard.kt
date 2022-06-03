package game.card.playingcards

data class FrenchCard(override val suit: FrenchSuit, override val rank: FrenchRank) :
    PlayingCard<FrenchSuit, FrenchRank> {

    constructor(pair: Pair<FrenchSuit, FrenchRank>) : this(pair.first, pair.second)

    override fun toString(): String = "${rank.label}${suit.unicode}"
}