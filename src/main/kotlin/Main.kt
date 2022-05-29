import card.playingcards.*
import game.card.playingcards.PlayingCardDeck
import game.card.playingcards.standard52

fun main() {

    val deck = PlayingCardDeck.standard52()
    println(deck)

    val draw = deck.draw(index = 50)
    val card2s3 = deck.draw(2, 49)

    println(draw)
    println(card2s3)

    val shuffle = deck.shuffle()
    val shuffle1 = deck.shuffle().shuffle()

    println(deck)
    println(shuffle)
    println(shuffle1)
    println(shuffle)

    val uni = "\u2660"
    println(uni)

}