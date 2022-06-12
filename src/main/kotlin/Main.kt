import game.card.playingcards.FrenchCardDeck
import game.card.playingcards.standard52
import game.card.poker.PokerHand

fun main() {

    val deck = FrenchCardDeck.standard52().shuffle()
    repeat(11) {
        val hand = PokerHand.of(deck.draw(5).second)
        hand.rank()?.let { println(if (it.value >= 5) "$hand =-=-=-=-=-=-=-=-" else "$hand") }
    }

}