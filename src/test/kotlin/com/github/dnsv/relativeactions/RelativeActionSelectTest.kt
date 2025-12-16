package com.github.dnsv.relativeactions

import com.github.dnsv.relativeactions.util.BaseTestCase
import com.intellij.openapi.editor.LogicalPosition
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class RelativeActionSelectTest : BaseTestCase() {
    companion object {
        @JvmStatic
        fun caretPositionProvider(): Stream<Arguments> =
            Stream.of(
                // Caret stays as-is if the logical position is inside the selection
                Arguments.of(LogicalPosition(3, 9), "s2l", LogicalPosition(3, 9)),
                Arguments.of(LogicalPosition(3, 9), "s1,2b", LogicalPosition(3, 9)),
                // Caret moves to the start of the selection if it's not in the selection
                Arguments.of(LogicalPosition(5, 9), "s2,1l", LogicalPosition(3, 0)),
            )
    }

    @ParameterizedTest
    @CsvSource(
        // Select down without a number selects the current line
        "sk, '5\n'",
        // Select 2 lines down
        "s2k, '5\n6\n7\n'",
        // Same as above, but with reversed command order (order doesn't matter)
        "2sk, '5\n6\n7\n'",
        // Select down beyond the last line — selects all to the end
        "s10k, '5\n6\n7\n8\n9\n10\n'",
        // Select up without a number selects the current line
        "sl, '5\n'",
        // Select 2 lines up
        "s2l, '3\n4\n5\n'",
        // Select up beyond the first line — selects all to the top
        "s10l, '1\n2\n3\n4\n5\n'",
    )
    fun `test basic select text`(
        command: String,
        expectedText: String,
    ) {
        makeEditor("1\n2\n3\n4\n5\n6\n7\n8\n9\n10\n")
        moveCaret(LogicalPosition(4, 0)) // At "5\n"

        performCommand(command)

        assertSelection(expectedText)
    }

    @ParameterizedTest
    @CsvSource(
        // Select lines in both directions
        "'s3,2b', '2\n3\n4\n5\n6\n7\n'",
        // Select lines 1 through 3 below the current line
        "'s1,3k', '6\n7\n8\n'",
        // Same as above, but with reversed command order (order doesn't matter)
        "'3,1sk', '6\n7\n8\n'",
        // Select lines 1 through 10 below the current line (caps at file end)
        "'s1,10k', '6\n7\n8\n9\n10\n'",
        // Select lines 1 through 3 above the current line
        "'s3,1l', '2\n3\n4\n'",
        // Select lines 1 through 10 above the current line (caps at file start)
        "'s10,1l', '1\n2\n3\n4\n'",
    )
    fun `test multi-line select text`(
        command: String,
        expectedText: String,
    ) {
        makeEditor("1\n2\n3\n4\n5\n6\n7\n8\n9\n10\n")
        moveCaret(LogicalPosition(4, 0)) // At "5\n"

        performCommand(command)

        assertSelection(expectedText)
    }

    @ParameterizedTest
    @MethodSource("caretPositionProvider")
    fun `test select text caret position`(
        initialPosition: LogicalPosition,
        command: String,
        expectedPosition: LogicalPosition,
    ) {
        makeEditor(
            """
            Out of college, money spent
            See no future, pay no rent
            All the money's gone, nowhere to go
            Any jobber got the sack
            Monday morning, turning back
            Yellow lorry slow, nowhere to go
            """.trimIndent(),
        )
        moveCaret(initialPosition)

        performCommand(command)

        assertCaretPosition(expectedPosition)
    }

    @Test
    fun `test select text one line shortcut`() {
        makeEditor("1\n2\n3\n4\n5\n6\n7\n8\n9\n10\n")
        moveCaret(LogicalPosition(4, 0)) // At "5\n"

        // Select line 3 above the current line
        performCommand("s3,l")

        assertSelection("2\n")
    }
}
