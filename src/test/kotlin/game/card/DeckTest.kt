package game.card

import game.card.playingcards.FrenchCard
import game.card.playingcards.FrenchCardDeck
import game.card.playingcards.FrenchSuit.*
import game.card.playingcards.FrenchRank.*
import game.card.playingcards.standard52
import game.card.playingcards.minus
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertDoesNotThrow
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertNotSame

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DeckTest {
    private val deckSupertype: Deck<FrenchCard> = FrenchCardDeck.standard52()
    private val deckConcrete: FrenchCardDeck = FrenchCardDeck.standard52()

    @DisplayName("FrenchCardDeck is a Deck<FrenchCard>")
    @Test
    fun subtypingEqualsTest() {
        assertEquals(deckSupertype, deckConcrete)
        assertDoesNotThrow { deckSupertype as FrenchCardDeck }
    }

    @DisplayName("test Deck draw()")
    @Test
    fun deckDrawTest() {
        val (deck, cards) = deckSupertype.draw()

        assertEquals(51, deck.size())
        assertEquals(52, deckSupertype.size())
        assertNotSame(deckSupertype, deck)

        assertEquals(1, cards.size)
        assertEquals(FrenchCard(SPADE to ACE), cards.first())

        val (deck42, cards10) = deckSupertype.draw(10)

        assertEquals(42, deck42.size())
        assertEquals(10, cards10.size)

        val (deck51, cards1) = deckSupertype.draw(index = 1)

        assertEquals(51, deck51.size())
        assertEquals(1, cards1.size)
        assertEquals(FrenchCard(SPADE to TWO), cards1.first())

        val (deck50, cards2) = deckSupertype.draw(2, 2)

        assertEquals(50, deck50.size())
        assertEquals(2, cards2.size)
        assertEquals(FrenchCard(SPADE to THREE), cards2.first())
        assertEquals(FrenchCard(SPADE to FOUR), cards2.component2())
    }

    @DisplayName("test Deck operator minus")
    @Test
    fun deckOperatorMinusTest() {
        val (deck, cards) = deckSupertype - 2

        assertNotSame(deck, deckSupertype)
        assertEquals(52, deckSupertype.size())
        assertEquals(50, deck.size())
        assertEquals(2, cards.size)
        assertEquals(listOf(FrenchCard(SPADE to ACE), FrenchCard(SPADE to TWO)), cards)

        val (deck1: FrenchCardDeck, frenchCards) = deckConcrete - 1

        assertNotSame(deck1, deckSupertype)
        assertEquals(52, deckSupertype.size())
        assertEquals(51, deck1.size())
        assertEquals(1, frenchCards.size)
        assertEquals(listOf(FrenchCard(SPADE to ACE)), frenchCards)
    }

    @DisplayName("test Deck shuffle()")
    @Test
    fun deckShuffleTest() {
        val shuffle = deckSupertype.shuffle()

        assertNotSame(deckSupertype, shuffle)
        assertNotEquals(deckSupertype, shuffle)
        assertNotEquals(deckSupertype.toString(), shuffle.toString())
    }

}