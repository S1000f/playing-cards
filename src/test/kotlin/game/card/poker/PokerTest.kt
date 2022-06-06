package game.card.poker

import game.card.playingcards.FrenchRank.*
import game.card.playingcards.FrenchSuit.*
import game.card.poker.PokerRank.*
import game.card.playingcards.FrenchCard
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PokerTest {

    @DisplayName("these hand-ranking should be a High-card")
    @Test
    fun highCardRankTest() {
        val hand = PokerHand.of(
            CLUB to ACE,
            DIAMOND to THREE,
            DIAMOND to QUEEN,
            HEART to SEVEN,
            SPADE to KING
        )

        println(hand)

        assertEquals(HIGH_CARD, hand.rank())
    }

    @DisplayName("these hand-ranking should be a One-pair")
    @Test
    fun onePairTest() {
        val hand = PokerHand.of(
            HEART to THREE,
            DIAMOND to KING,
            SPADE to THREE,
            DIAMOND to QUEEN,
            CLUB to ACE,
            CLUB to TWO
        )

        println(hand)

        assertEquals(ONE_PAIR, hand.rank())
    }

    @DisplayName("these hand-ranking should be a Two-pair")
    @Test
    fun twoPairTest() {
        val hand = PokerHand.of(
            HEART to TEN,
            DIAMOND to NINE,
            CLUB to TEN,
            SPADE to KING,
            CLUB to NINE,
            HEART to ACE
        )

        val hand1 = PokerHand.of(
            SPADE to ACE,
            DIAMOND to SEVEN,
            HEART to ACE,
            CLUB to TEN,
            HEART to SEVEN
        )

        println(hand)
        println(hand1)

        assertEquals(TWO_PAIR, hand.rank())
        assertEquals(TWO_PAIR, hand1.rank())
    }

    @DisplayName("these hand-ranking should be a Trips")
    @Test
    fun tripsRankTest() {
        val hand = PokerHand.of(
            HEART to KING,
            DIAMOND to JACK,
            HEART to JACK,
            HEART to TEN,
            SPADE to JACK,
            CLUB to QUEEN
        )

        val hand1 = PokerHand.of(
            HEART to TEN,
            DIAMOND to TEN,
            HEART to THREE,
            CLUB to TEN,
            SPADE to NINE
        )

        val hand2 = PokerHand.of(
            CLUB to TWO,
            SPADE to FOUR,
            SPADE to EIGHT,
            CLUB to KING,
            HEART to TWO,
            DIAMOND to TWO
        )

        assertEquals(THREE_OF_A_KIND, hand.rank())
        assertEquals(THREE_OF_A_KIND, hand1.rank())
        assertEquals(THREE_OF_A_KIND, hand2.rank())
    }

    @DisplayName("these hand-rankings should not be a Trips")
    @Test
    fun tripsRankTest2() {
        val other = PokerHand.of(
            CLUB to TEN,
            SPADE to KING,
            HEART to TEN,
            SPADE to TEN,
            HEART to KING
        )

        val other1 = PokerHand.of(
            SPADE to FOUR,
            CLUB to FIVE,
            SPADE to FIVE,
            HEART to FIVE,
            DIAMOND to FIVE
        )

        assertNotEquals(THREE_OF_A_KIND, other.rank())
        assertNotEquals(THREE_OF_A_KIND, other1.rank())
    }

    @DisplayName("this hand-ranking should be a FLUSH")
    @Test
    fun flushRankTest() {
        val hand = PokerHand(
            mutableListOf(
                FrenchCard(CLUB to KING),
                FrenchCard(CLUB to QUEEN),
                FrenchCard(CLUB to FIVE),
                FrenchCard(CLUB to TEN),
                FrenchCard(CLUB to JACK),
                FrenchCard(CLUB to THREE),
                FrenchCard(CLUB to SEVEN),
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

        val hand2 = PokerHand.of(
            SPADE to KING,
            HEART to SEVEN,
            DIAMOND to FIVE,
            CLUB to SIX,
            CLUB to EIGHT,
            HEART to FOUR
        )

        assertEquals(STRAIGHT, hand.rank())
        assertEquals(STRAIGHT, hand1.rank())
        assertEquals(STRAIGHT, hand2.rank())
    }

    @DisplayName("these hand-rankings should be a Quads")
    @Test
    fun fourOfAKindRankTest() {
        val hand = PokerHand.of(
            DIAMOND to JACK,
            CLUB to JACK,
            HEART to KING,
            SPADE to JACK,
            HEART to JACK
        )

        assertEquals(FOUR_OF_A_KIND, hand.rank())
    }

    @DisplayName("these hand-rankings should be a STRAIGHT_FLUSH")
    @Test
    fun straightFlushRankTest() {
        val hand = PokerHand.of(
            CLUB to QUEEN,
            CLUB to KING,
            DIAMOND to JACK,
            CLUB to JACK,
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

        println(hand)
        println(hand2)

        assertEquals(STRAIGHT_FLUSH, hand.rank())
        assertEquals(STRAIGHT_FLUSH, hand2.rank())
    }
}