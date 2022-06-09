package game.card.poker

import game.card.playingcards.FrenchRank.*
import game.card.playingcards.FrenchSuit.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import kotlin.test.assertEquals

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
    private val onePair1 =
        PokerHand.of(HEART to FOUR, DIAMOND to KING, SPADE to FOUR, DIAMOND to QUEEN, CLUB to ACE, CLUB to TWO)
    private val onePair2 =
        PokerHand.of(HEART to FOUR, DIAMOND to KING, SPADE to FOUR, DIAMOND to JACK, CLUB to ACE, CLUB to TWO)
    private val twoPair0 =
        PokerHand.of(HEART to TEN, DIAMOND to NINE, CLUB to TEN, SPADE to KING, CLUB to NINE, HEART to ACE)
    private val twoPair1 =
        PokerHand.of(SPADE to ACE, DIAMOND to SEVEN, HEART to ACE, CLUB to TEN, HEART to SEVEN)
    private val twoPair2 =
        PokerHand.of(SPADE to ACE, DIAMOND to SEVEN, HEART to KING, CLUB to TEN, HEART to SEVEN)
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

    @Test
    fun showdownWithTheSameRankTest() {
        assertEquals(-1, Poker.showdown(twoPair2, twoPair1))
        assertEquals(1, Poker.showdown(twoPair1, twoPair0))
        assertEquals(0, Poker.showdown(twoPair0, twoPair0))

        assertEquals(-1, Poker.showdown(onePair0, onePair1))
        assertEquals(1, Poker.showdown(onePair1, onePair2))
        assertEquals(0, Poker.showdown(onePair1, onePair1))

        assertEquals(1, Poker.showdown(highCard0, highCard1))
        assertEquals(-1, Poker.showdown(highCard2, highCard0))
        assertEquals(1, Poker.showdown(highCard1, highCard2))
        assertEquals(0, Poker.showdown(highCard1, highCard1))
    }

    @Test
    fun showdownWithOtherRanksTest() {
        assertEquals(-1, Poker.showdown(highCard1, onePair0))
        assertEquals(-1, Poker.showdown(highCard0, twoPair1))
        assertEquals(-1, Poker.showdown(highCard1, trips0))
        assertEquals(-1, Poker.showdown(highCard0, straight0))
        assertEquals(-1, Poker.showdown(highCard1, flush0))
        assertEquals(-1, Poker.showdown(highCard2, fullHouse0))
        assertEquals(-1, Poker.showdown(highCard2, quads0))
        assertEquals(-1, Poker.showdown(highCard1, straightFlush0))

        assertEquals(-1, Poker.showdown(onePair0, twoPair0))
        assertEquals(-1, Poker.showdown(onePair0, trips1))
        assertEquals(-1, Poker.showdown(onePair0, straight2))
        assertEquals(-1, Poker.showdown(onePair0, flush0))
        assertEquals(-1, Poker.showdown(onePair0, fullHouse0))
        assertEquals(-1, Poker.showdown(onePair0, quads0))
        assertEquals(-1, Poker.showdown(onePair0, straightFlush1))

        assertEquals(-1, Poker.showdown(twoPair1, trips2))
        assertEquals(-1, Poker.showdown(twoPair0, straight0))
        assertEquals(-1, Poker.showdown(twoPair1, flush0))
        assertEquals(-1, Poker.showdown(twoPair0, fullHouse0))
        assertEquals(-1, Poker.showdown(twoPair0, quads0))
        assertEquals(-1, Poker.showdown(twoPair1, straightFlush0))

        assertEquals(-1, Poker.showdown(trips1, straight2))
        assertEquals(-1, Poker.showdown(trips1, flush0))
        assertEquals(-1, Poker.showdown(trips1, fullHouse0))
        assertEquals(-1, Poker.showdown(trips1, quads0))
        assertEquals(-1, Poker.showdown(trips1, straightFlush1))

        assertEquals(-1, Poker.showdown(straight1, flush0))
        assertEquals(-1, Poker.showdown(straight0, fullHouse0))
        assertEquals(-1, Poker.showdown(straight0, quads0))
        assertEquals(-1, Poker.showdown(straight1, straightFlush0))

        assertEquals(-1, Poker.showdown(flush0, fullHouse0))
        assertEquals(-1, Poker.showdown(flush0, quads0))
        assertEquals(-1, Poker.showdown(flush0, straightFlush0))

        assertEquals(-1, Poker.showdown(fullHouse0, quads0))
        assertEquals(-1, Poker.showdown(fullHouse0, straightFlush1))

        assertEquals(-1, Poker.showdown(quads0, straightFlush0))
    }

}