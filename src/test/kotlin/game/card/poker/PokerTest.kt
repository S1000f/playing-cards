package game.card.poker

import game.card.playingcards.FrenchRank.*
import game.card.playingcards.FrenchSuit.*
import game.card.poker.PokerRank.*
import game.card.playingcards.PlayingCard
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import kotlin.test.assertEquals

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PokerTest {

    @DisplayName("this hand-ranking should be a FLUSH")
    @Test
    fun flushRankTest() {
        val hand = PokerHand(
            mutableListOf(
                PlayingCard(CLUB to KING),
                PlayingCard(CLUB to QUEEN),
                PlayingCard(CLUB to FIVE),
                PlayingCard(CLUB to TEN),
                PlayingCard(CLUB to JACK),
                PlayingCard(CLUB to THREE),
                PlayingCard(CLUB to SEVEN),
            )
        )

        assertEquals(FLUSH, hand.rank())
    }

    @DisplayName("these hand-rankings should be a STRAIGHT")
    @Test
    fun straightRankTest() {
        val hand = PokerHand.of(
            CLUB to JACK,
            DIAMOND to QUEEN,
            DIAMOND to ACE,
            HEART to THREE,
            CLUB to KING,
            SPADE to TEN
        )

        val hand1 = PokerHand.of(
            SPADE to ACE,
            CLUB to FIVE,
            HEART to FOUR,
            HEART to KING,
            DIAMOND to THREE,
            SPADE to TWO
        )

        assertEquals(STRAIGHT, hand.rank())
        assertEquals(STRAIGHT, hand.rank())
    }

    @DisplayName("these hand-rankings should be a STRAIGHT_FLUSH")
    @Test
    fun straightFlushRankTest() {
        val hand = PokerHand.of(
            CLUB to QUEEN,
            CLUB to KING,
            CLUB to JACK,
            CLUB to TWO,
            CLUB to NINE,
            CLUB to TEN,
        )

        val hand2 = PokerHand.of(
            HEART to JACK,
            HEART to QUEEN,
            HEART to ACE,
            HEART to THREE,
            HEART to KING,
            HEART to TEN
        )

        assertEquals(STRAIGHT_FLUSH, hand.rank())
        assertEquals(STRAIGHT_FLUSH, hand2.rank())
    }
}