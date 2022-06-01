package tool

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import kotlin.test.assertEquals

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class HelpersTest {

    @DisplayName("it returns sum of Int values from List after skipping and taking some elements")
    @Test
    fun sumSkipTakeTest() {
        val list = listOf("1", "2", "3", "4")
        val sum = list.sumSkipTake(3) { x -> x.toInt() }

        assertEquals(sum, list.drop(0).take(3).sumOf { it.toInt() })

        val sum1 = list.sumSkipTake(2, 2) { x -> x.toInt() }

        assertEquals(sum1, list.drop(2).take(2).sumOf { it.toInt() })
    }
}