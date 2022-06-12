package game.card

import game.card.playingcards.FrenchCard
import game.card.playingcards.FrenchRank.*
import game.card.playingcards.FrenchSuit.*
import game.card.poker.PokerHand
import game.card.poker.plus
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertDoesNotThrow
import kotlin.test.assertEquals
import kotlin.test.assertNotSame

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class HandTest {
    private val handSupertype: Hand<FrenchCard> = PokerHand.of(DIAMOND to KING)
    private val handConcreteType: PokerHand = PokerHand.of(DIAMOND to KING)

    @DisplayName("PokerHand is a Hand<FrenchCard>")
    @Test
    fun subtypingEqualsTest() {
        assertEquals(handSupertype, handConcreteType)

        val addSupertype = handSupertype.add(FrenchCard(CLUB to SEVEN))
        val addConcrete = handConcreteType.add(CLUB to SEVEN)

        assertEquals(addSupertype, addConcrete)

        val addAllSupertype = handSupertype.addAll(listOf(FrenchCard(HEART to FIVE), FrenchCard(SPADE to ACE)))
        val addAllConcrete = handConcreteType.addAll(HEART to FIVE, SPADE to ACE)

        assertEquals(addAllSupertype, addAllConcrete)
    }

    @DisplayName("a smart-casting from Hand<FrenchCard> to PokerHand should be success")
    @Test
    fun subtypingSmartCastTest() {
        assertDoesNotThrow { handSupertype as PokerHand }
    }

    @DisplayName("test Hand add(), addAll()")
    @Test
    fun immutableTest() {
        val addSupertype = handSupertype.add(FrenchCard(CLUB to SEVEN))

        assertEquals(1, handSupertype.count())
        assertEquals(2, addSupertype.count())
        assertNotSame(handSupertype, addSupertype)

        val addAllSupertype = handSupertype.addAll(listOf(FrenchCard(HEART to FIVE), FrenchCard(SPADE to ACE)))

        assertEquals(1, handSupertype.count())
        assertEquals(3, addAllSupertype.count())
        assertNotSame(handSupertype, addAllSupertype)
    }

    @DisplayName("test operator plus card")
    @Test
    fun operatorPlusTest() {
        val card = FrenchCard(HEART to KING)
        val plusHand = handSupertype + card

        assertNotSame(handSupertype, plusHand)
        assertEquals(2, plusHand.count())
        assertEquals(plusHand, PokerHand.of(DIAMOND to KING, card.suit to card.rank))

        val yetAnother = card + handSupertype

        assertNotSame(handSupertype, yetAnother)
        assertEquals(2, yetAnother.count())
        assertEquals(yetAnother, PokerHand.of(DIAMOND to KING, card.suit to card.rank))

        val plusConcrete: PokerHand = handConcreteType + card

        assertNotSame(handSupertype, plusConcrete)
        assertEquals(2, plusConcrete.count())
        assertEquals(plusConcrete, PokerHand.of(DIAMOND to KING, card.suit to card.rank))

        val yetAnotherConcrete: PokerHand = card + handConcreteType

        assertNotSame(handSupertype, yetAnotherConcrete)
        assertEquals(2, yetAnotherConcrete.count())
        assertEquals(yetAnotherConcrete, PokerHand.of(DIAMOND to KING, card.suit to card.rank))
    }

}