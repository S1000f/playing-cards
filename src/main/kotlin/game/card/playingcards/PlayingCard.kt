package game.card.playingcards

import game.card.Card
import game.card.Rank
import game.card.Suit

data class PlayingCard(val suit: Suit, val rank: Rank) : Card {

    constructor(pair: Pair<Suit, Rank>) : this(pair.first, pair.second)

    override fun toString(): String = "${rank.label}${suit.unicode}"
}