package game.card.playingcards

import game.card.playingcards.FrenchSuit.*
import game.card.playingcards.FrenchRank.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import kotlin.test.assertEquals

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FrenchKtTest {

    @Test
    fun standard52DeckTest() {
        val standard52 = FrenchCardDeck.standard52()

        val deck = FrenchCardDeck.of(
            SPADE to ACE,
            SPADE to TWO,
            SPADE to THREE,
            SPADE to FOUR,
            SPADE to FIVE,
            SPADE to SIX,
            SPADE to SEVEN,
            SPADE to EIGHT,
            SPADE to NINE,
            SPADE to TEN,
            SPADE to JACK,
            SPADE to QUEEN,
            SPADE to KING,
            DIAMOND to ACE,
            DIAMOND to TWO,
            DIAMOND to THREE,
            DIAMOND to FOUR,
            DIAMOND to FIVE,
            DIAMOND to SIX,
            DIAMOND to SEVEN,
            DIAMOND to EIGHT,
            DIAMOND to NINE,
            DIAMOND to TEN,
            DIAMOND to JACK,
            DIAMOND to QUEEN,
            DIAMOND to KING,
            CLUB to KING,
            CLUB to QUEEN,
            CLUB to JACK,
            CLUB to TEN,
            CLUB to NINE,
            CLUB to EIGHT,
            CLUB to SEVEN,
            CLUB to SIX,
            CLUB to FIVE,
            CLUB to FOUR,
            CLUB to THREE,
            CLUB to TWO,
            CLUB to ACE,
            HEART to KING,
            HEART to QUEEN,
            HEART to JACK,
            HEART to TEN,
            HEART to NINE,
            HEART to EIGHT,
            HEART to SEVEN,
            HEART to SIX,
            HEART to FIVE,
            HEART to FOUR,
            HEART to THREE,
            HEART to TWO,
            HEART to ACE,
        )

        assertEquals(52, standard52.size())
        assertEquals(deck, standard52)
    }

}