package game.card.poker

import game.card.playingcards.FrenchRank.*
import game.card.playingcards.FrenchSuit.*
import game.card.poker.PokerRank.*
import game.card.playingcards.FrenchCardDeck
import game.card.playingcards.standard52
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PokerTest {
    private val highCard0 =
        PokerHand.of(CLUB to ACE, DIAMOND to THREE, DIAMOND to QUEEN, HEART to SEVEN, SPADE to KING)
    private val highCard1 =
        PokerHand.of(DIAMOND to KING, HEART to JACK, CLUB to TEN, DIAMOND to SIX, DIAMOND to ACE)
    private val highCard2 =
        PokerHand.of(HEART to QUEEN, CLUB to FOUR, SPADE to TEN, CLUB to SIX, CLUB to ACE)
    private val onePair0 =
        PokerHand.of(HEART to THREE, DIAMOND to KING, SPADE to THREE, DIAMOND to QUEEN, CLUB to ACE, CLUB to TWO)
    private val twoPair0 =
        PokerHand.of(HEART to TEN, DIAMOND to NINE, CLUB to TEN, SPADE to KING, CLUB to NINE, HEART to ACE)
    private val twoPair1 =
        PokerHand.of(SPADE to ACE, DIAMOND to SEVEN, HEART to ACE, CLUB to TEN, HEART to SEVEN)
    private val trips0 =
        PokerHand.of(HEART to KING, DIAMOND to JACK, HEART to JACK, HEART to TEN, SPADE to JACK, CLUB to QUEEN)
    private val trips1 =
        PokerHand.of(HEART to TEN, DIAMOND to TEN, HEART to THREE, CLUB to TEN, SPADE to NINE)
    private val trips2 =
        PokerHand.of(CLUB to TWO, SPADE to FOUR, SPADE to EIGHT, CLUB to KING, HEART to TWO, DIAMOND to TWO)
    private val straight0 =
        PokerHand.of(CLUB to JACK, DIAMOND to QUEEN, DIAMOND to ACE, HEART to THREE, CLUB to KING, SPADE to TEN)
    private val straight1 =
        PokerHand.of(SPADE to ACE, CLUB to FIVE, HEART to FOUR, HEART to KING, DIAMOND to THREE, SPADE to TWO)
    private val straight2 =
        PokerHand.of(SPADE to KING, HEART to SEVEN, DIAMOND to FIVE, CLUB to SIX, CLUB to EIGHT, HEART to FOUR)
    private val flush0 =
        PokerHand.of(CLUB to KING, CLUB to QUEEN, CLUB to FIVE, CLUB to JACK, CLUB to THREE, CLUB to TWO)
    private val fullHouse0 =
        PokerHand.of(CLUB to SEVEN, SPADE to FIVE, HEART to SEVEN, CLUB to SIX, DIAMOND to SEVEN, HEART to FIVE)
    private val quads0 =
        PokerHand.of(DIAMOND to JACK, CLUB to JACK, HEART to KING, SPADE to JACK, HEART to JACK)
    private val straightFlush0 =
        PokerHand.of(CLUB to QUEEN, CLUB to KING, DIAMOND to JACK, CLUB to JACK, CLUB to NINE, CLUB to TEN)
    private val straightFlush1 =
        PokerHand.of(HEART to JACK, HEART to QUEEN, HEART to ACE, HEART to THREE, HEART to KING, HEART to TEN)

    @DisplayName("every hand must have a hand-ranking")
    @Test
    fun randomRankingTest() {
        var counter = 0

        repeat(100) {
            val deck = FrenchCardDeck.standard52().shuffle()
            repeat(10) { if (PokerHand.of(deck.draw(5)).rank() == null) counter++ }
        }

        assertEquals(0, counter)
    }

    @DisplayName("Straight ranking kickers")
    @Test
    fun straightKickerTest() {
        assertEquals(listOf(14), straight0.kicker())
        assertEquals(listOf(5), straight1.kicker())
        assertEquals(listOf(8), straight2.kicker())
    }

    @DisplayName("Trips ranking kickers")
    @Test
    fun tripsKickerTest() {
        assertEquals(listOf(11, 13, 12), trips0.kicker())
        assertEquals(listOf(10, 9, 3), trips1.kicker())
        assertEquals(listOf(2, 13, 8), trips2.kicker())
    }

    @DisplayName("Two-pair ranking kickers")
    @Test
    fun twoPairKickerTest() {
        assertEquals(listOf(10, 9, 14), twoPair0.kicker())
        assertEquals(listOf(14, 7, 10), twoPair1.kicker())
    }

    @DisplayName("One-pair ranking kickers")
    @Test
    fun onePairKickerTest() {
        assertEquals(listOf(3, 14, 13, 12), onePair0.kicker())
    }

    @DisplayName("High-card ranking kickers")
    @Test
    fun highCardKickerTest() {
        assertEquals(listOf(14, 13, 12, 7, 3), highCard0.kicker())
        assertEquals(listOf(14, 13, 11, 10, 6), highCard1.kicker())
        assertEquals(listOf(14, 12, 10, 6, 4), highCard2.kicker())
    }

    @DisplayName("these hand-ranking should be a High-card")
    @Test
    fun highCardRankTest() {
        assertEquals(HIGH_CARD, highCard0.rank())
        assertEquals(HIGH_CARD, highCard1.rank())
        assertEquals(HIGH_CARD, highCard2.rank())
    }

    @DisplayName("these hand-ranking should be a One-pair")
    @Test
    fun onePairTest() {
        assertEquals(ONE_PAIR, onePair0.rank())
    }

    @DisplayName("these hand-ranking should be a Two-pair")
    @Test
    fun twoPairTest() {
        assertEquals(TWO_PAIR, twoPair0.rank())
        assertEquals(TWO_PAIR, twoPair1.rank())
    }

    @DisplayName("these hand-ranking should be a Trips")
    @Test
    fun tripsRankTest() {
        assertEquals(THREE_OF_A_KIND, trips0.rank())
        assertEquals(THREE_OF_A_KIND, trips1.rank())
        assertEquals(THREE_OF_A_KIND, trips2.rank())
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

    @DisplayName("these hand-rankings should be a STRAIGHT")
    @Test
    fun straightRankTest() {
        assertEquals(STRAIGHT, straight0.rank())
        assertEquals(STRAIGHT, straight1.rank())
        assertEquals(STRAIGHT, straight2.rank())
    }

    @DisplayName("this hand-ranking should be a FLUSH")
    @Test
    fun flushRankTest() {
        assertEquals(FLUSH, flush0.rank())
    }

    @DisplayName("these hand-rankings should be a Full-house")
    @Test
    fun fullHouseRankTest() {
        assertEquals(FULL_HOUSE, fullHouse0.rank())
    }

    @DisplayName("these hand-rankings should be a Quads")
    @Test
    fun fourOfAKindRankTest() {
        assertEquals(FOUR_OF_A_KIND, quads0.rank())
    }

    @DisplayName("these hand-rankings should be a STRAIGHT_FLUSH")
    @Test
    fun straightFlushRankTest() {
        assertEquals(STRAIGHT_FLUSH, straightFlush0.rank())
        assertEquals(STRAIGHT_FLUSH, straightFlush1.rank())
    }
}