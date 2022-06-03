package game.card.playingcards

import game.card.Card

interface PlayingCard<Suit, Rank> : Card {
    val suit: Suit
    val rank: Rank
}