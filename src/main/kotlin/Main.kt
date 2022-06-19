import game.card.playingcards.FrenchCardDeck
import game.card.playingcards.standard52
import game.card.poker.PokerHand

fun main() {

    val deck = FrenchCardDeck.standard52().shuffle()
    var altered: FrenchCardDeck = deck
    repeat(11) {
        val (alt, cards) = altered.draw(5)
        altered = alt
        val hand = PokerHand.of(cards)
        println(if (hand.rank().value >= 5) "$hand =-=-=-=-=-=-=-=-" else "$hand")
    }

}