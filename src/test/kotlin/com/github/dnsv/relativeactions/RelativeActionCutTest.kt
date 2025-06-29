package com.github.dnsv.relativeactions

import com.github.dnsv.relativeactions.util.BaseTestCase
import com.intellij.openapi.editor.LogicalPosition
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class RelativeActionCutTest : BaseTestCase() {
    companion object {
        @JvmStatic
        fun caretPositionProvider(): Stream<Arguments> =
            Stream.of(
                // Cut up - caret moves to first line of cut text
                Arguments.of(LogicalPosition(3, 9), "x2k", LogicalPosition(1, 0)),
                // Cut down - caret moves to start of current line
                Arguments.of(LogicalPosition(3, 9), "x2l", LogicalPosition(3, 0)),
                // Cut both - caret moves to first line of cut range
                Arguments.of(LogicalPosition(3, 9), "x1,2b", LogicalPosition(2, 0)),
                // Cut above the current line - caret remains unchanged
                Arguments.of(LogicalPosition(5, 9), "x2,1k", LogicalPosition(3, 9)),
                // Cut below the current line - caret remains unchanged
                Arguments.of(LogicalPosition(3, 9), "x1,2l", LogicalPosition(3, 9)),
            )
    }

    @ParameterizedTest
    @CsvSource(
        // Cut down without a number copies the current line
        "xl, '1\n2\n3\n4\n6\n7\n8\n9\n10\n', '5\n'",
        // Cut 3 lines down
        "x3l, '1\n2\n3\n4\n9\n10\n', '5\n6\n7\n8\n'",
        // Same as above, but with reversed command order (order doesn't matter)
        "3xl, '1\n2\n3\n4\n9\n10\n', '5\n6\n7\n8\n'",
        // Cut down beyond the last line — copies all to the end
        "x10l, '1\n2\n3\n4\n', '5\n6\n7\n8\n9\n10\n'",
        // Cut up without a number copies the current line
        "xk, '1\n2\n3\n4\n6\n7\n8\n9\n10\n', '5\n'",
        // Cut 2 lines up
        "x2k, '1\n2\n6\n7\n8\n9\n10\n', '3\n4\n5\n'",
        // Cut up beyond the first line — copies all to the top
        "x10k, '6\n7\n8\n9\n10\n', '1\n2\n3\n4\n5\n'",
    )
    fun `test basic cut text`(
        command: String,
        expectedText: String,
        expectedClipboardContent: String,
    ) {
        makeEditor("1\n2\n3\n4\n5\n6\n7\n8\n9\n10\n")
        moveCaret(LogicalPosition(4, 0)) // At "5\n"

        performCommand(command)

        assertText(expectedText)
        assertClipboard(expectedClipboardContent)
    }

    @ParameterizedTest
    @CsvSource(
        // Cut lines in both directions
        "'x3,2b', '1\n8\n9\n10\n', '2\n3\n4\n5\n6\n7\n'",
        // Cut lines 1 through 3 below the current line
        "'x1,3l', '1\n2\n3\n4\n5\n9\n10\n', '6\n7\n8\n'",
        // Same as above, but with reversed command order (order doesn't matter)
        "'3,1xl', '1\n2\n3\n4\n5\n9\n10\n', '6\n7\n8\n'",
        // Cut lines 2 through 10 below the current line (caps at file end)
        "'x2,10l', '1\n2\n3\n4\n5\n6\n', '7\n8\n9\n10\n'",
        // Cut lines 1 through 3 above the current line
        "'x3,1k', '1\n5\n6\n7\n8\n9\n10\n', '2\n3\n4\n'",
        // Cut lines 3 through 10 above the current line (caps at file start)
        "'x10,3k', '3\n4\n5\n6\n7\n8\n9\n10\n', '1\n2\n'",
    )
    fun `test multi-line cut text`(
        command: String,
        expectedText: String,
        expectedClipboardContent: String,
    ) {
        makeEditor("1\n2\n3\n4\n5\n6\n7\n8\n9\n10\n")
        moveCaret(LogicalPosition(4, 0)) // At "5\n"

        performCommand(command)

        assertText(expectedText)
        assertClipboard(expectedClipboardContent)
    }

    @ParameterizedTest
    @MethodSource("caretPositionProvider")
    fun `test cut text caret position`(
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

    fun `test cut text one line shortcut`() {
        makeEditor("1\n2\n3\n4\n5\n6\n7\n8\n9\n10\n")
        moveCaret(LogicalPosition(4, 0)) // At "5\n"

        // Copy line 3 above the current line
        performCommand("3,xk")

        assertText("1\n3\n4\n5\n6\n7\n8\n9\n10\n")
        assertClipboard("2\n")
    }
}
