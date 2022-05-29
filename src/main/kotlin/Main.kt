
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

    val hand1 = PokerHand(
        mutableListOf(
            PlayingCard(FrenchSuit.CLUB to FrenchRank.TEN),
            PlayingCard(FrenchSuit.DIAMOND to FrenchRank.NINE),
            PlayingCard(FrenchSuit.DIAMOND to FrenchRank.SEVEN),
            PlayingCard(FrenchSuit.CLUB to FrenchRank.SIX),
            PlayingCard(FrenchSuit.SPADE to FrenchRank.EIGHT),
        )
    )

    val hand2 = PokerHand(
        mutableListOf(
            PlayingCard(FrenchSuit.CLUB to FrenchRank.KING),
            PlayingCard(FrenchSuit.DIAMOND to FrenchRank.JACK),
            PlayingCard(FrenchSuit.DIAMOND to FrenchRank.TEN),
            PlayingCard(FrenchSuit.CLUB to FrenchRank.QUEEN),
            PlayingCard(FrenchSuit.SPADE to FrenchRank.ACE),
        )
    )

    println(hand)
    println(hand1)
    println(hand2)
}