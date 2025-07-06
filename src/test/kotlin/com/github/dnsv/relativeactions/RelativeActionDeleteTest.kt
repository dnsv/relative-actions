package com.github.dnsv.relativeactions

import com.github.dnsv.relativeactions.util.BaseTestCase
import com.intellij.openapi.editor.LogicalPosition
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class RelativeActionDeleteTest : BaseTestCase() {
    companion object {
        @JvmStatic
        fun caretPositionProvider(): Stream<Arguments> =
            Stream.of(
                // Delete up - caret moves to first line of deleted text
                Arguments.of(LogicalPosition(3, 9), "d2k", LogicalPosition(1, 0)),
                // Delete down - caret moves to start of current line
                Arguments.of(LogicalPosition(3, 9), "d2l", LogicalPosition(3, 0)),
                // Delete both - caret moves to first line of deleted range
                Arguments.of(LogicalPosition(3, 9), "d1,2b", LogicalPosition(2, 0)),
                // Delete above the current line - caret remains unchanged
                Arguments.of(LogicalPosition(5, 9), "d2,1k", LogicalPosition(3, 9)),
                // Delete below the current line - caret remains unchanged
                Arguments.of(LogicalPosition(3, 9), "d1,2l", LogicalPosition(3, 9)),
            )
    }

    @ParameterizedTest
    @CsvSource(
        // Delete down without a number deletes the current line
        "dl, '1\n2\n3\n4\n6\n7\n8\n9\n10\n'",
        // Delete 3 lines down
        "d3l, '1\n2\n3\n4\n9\n10\n'",
        // Same as above, but with reversed command order (order doesn't matter)
        "3dl, '1\n2\n3\n4\n9\n10\n'",
        // Delete down beyond the last line — deletes all to the end
        "d10l, '1\n2\n3\n4\n'",
        // Delete up without a number deletes the current line
        "dk, '1\n2\n3\n4\n6\n7\n8\n9\n10\n'",
        // Delete 3 lines up
        "d3k, '1\n6\n7\n8\n9\n10\n'",
        // Delete up beyond the first line — deletes all to the top
        "d10k, '6\n7\n8\n9\n10\n'",
    )
    fun `test basic delete text`(
        command: String,
        expectedText: String,
    ) {
        makeEditor("1\n2\n3\n4\n5\n6\n7\n8\n9\n10\n")
        moveCaret(LogicalPosition(4, 0)) // At "5\n"

        performCommand(command)

        assertText(expectedText)
    }

    @ParameterizedTest
    @CsvSource(
        // Delete lines in both directions
        "'d3,2b', '1\n8\n9\n10\n'",
        // Delete lines 1 through 3 below the current line
        "'d1,3l', '1\n2\n3\n4\n5\n9\n10\n'",
        // Same as above, but with reversed command order (order doesn't matter)
        "'3,1dl', '1\n2\n3\n4\n5\n9\n10\n'",
        // Delete lines 2 through 10 below the current line (caps at file end)
        "'d2,10l', '1\n2\n3\n4\n5\n6\n'",
        // Delete lines 1 through 3 above the current line
        "'d3,1k', '1\n5\n6\n7\n8\n9\n10\n'",
        // Delete lines 3 through 10 above the current line (caps at file start)
        "'d10,3k', '3\n4\n5\n6\n7\n8\n9\n10\n'",
    )
    fun `test multi-line delete text`(
        command: String,
        expectedText: String,
    ) {
        makeEditor("1\n2\n3\n4\n5\n6\n7\n8\n9\n10\n")
        moveCaret(LogicalPosition(4, 0)) // At "5\n"

        performCommand(command)

        assertText(expectedText)
    }

    @ParameterizedTest
    @MethodSource("caretPositionProvider")
    fun `test delete text caret position`(
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
    fun `test delete text one line shortcut`() {
        makeEditor("1\n2\n3\n4\n5\n6\n7\n8\n9\n10\n")
        moveCaret(LogicalPosition(4, 0)) // At "5\n"

        // Delete line 3 below the current line
        performCommand("d3,l")

        assertText("1\n2\n3\n4\n5\n6\n7\n9\n10\n")
    }
}
