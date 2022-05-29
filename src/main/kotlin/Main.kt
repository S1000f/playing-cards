
import game.card.playingcards.*
import game.card.poker.PokerHand

fun main() {

    val hand = PokerHand(mutableListOf(
        PlayingCard(FrenchSuit.CLUB to FrenchRank.KING),
        PlayingCard(FrenchSuit.CLUB to FrenchRank.QUEEN),
        PlayingCard(FrenchSuit.CLUB to FrenchRank.FIVE),
        PlayingCard(FrenchSuit.CLUB to FrenchRank.TEN),
        PlayingCard(FrenchSuit.CLUB to FrenchRank.JACK),
        PlayingCard(FrenchSuit.CLUB to FrenchRank.THREE),
        PlayingCard(FrenchSuit.CLUB to FrenchRank.SEVEN),
    ))

    println(hand)

}