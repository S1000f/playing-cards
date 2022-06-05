package game.card.playingcards

import game.card.Card
import tool.sumSkipTake
import kotlin.math.max

fun <T : PlayingCard<Suit, out Rank>> sumRankStreak(card: T, streak: Int = 5): Int {
    val rank = card.rank.value
    var sum = 0

    for (i in rank downTo max(rank - (streak - 1), 0)) {
        sum += i
    }

    return sum
}

fun <T : PlayingCard<Suit, out Rank>> Collection<T>.sumSkipTake(take: Int, skip: Int = 0) =
    sumSkipTake(take, skip) { x -> x.rank.value }

interface PlayingCard<out Suit, Rank> : Card {
    val suit: Suit
    val rank: Rank
}