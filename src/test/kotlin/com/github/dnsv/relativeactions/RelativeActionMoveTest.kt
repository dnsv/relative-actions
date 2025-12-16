package com.github.dnsv.relativeactions

import com.github.dnsv.relativeactions.util.BaseTestCase
import com.intellij.openapi.editor.LogicalPosition
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class RelativeActionMoveTest : BaseTestCase() {
    companion object {
        @JvmStatic
        fun moveCaretLinePositionProvider(): Stream<Arguments> =
            Stream.of(
                // Move down without a number leaves the caret on the same line
                Arguments.of(LogicalPosition(4, 0), "k", LogicalPosition(4, 0)),
                // Move down by N lines
                Arguments.of(LogicalPosition(4, 0), "3k", LogicalPosition(7, 0)),
                // Move down beyond the last line places the caret on the last line
                Arguments.of(LogicalPosition(4, 0), "10k", LogicalPosition(10, 0)),
                // Move up by N lines
                Arguments.of(LogicalPosition(4, 0), "2l", LogicalPosition(2, 0)),
                // Move up without a number leaves the caret on the same line
                Arguments.of(LogicalPosition(4, 0), "l", LogicalPosition(4, 0)),
                // Move up beyond the first line places the caret on the first line
                Arguments.of(LogicalPosition(4, 0), "10l", LogicalPosition(0, 0)),
            )

        @JvmStatic
        fun moveCaretColumnPositionProvider(): Stream<Arguments> =
            Stream.of(
                // Caret stays on the same column if the new line is at least as long
                Arguments.of(LogicalPosition(0, 8), "1k", LogicalPosition(1, 8)),
                // Caret moves to end of line if the new line is shorter
                Arguments.of(LogicalPosition(2, 35), "1l", LogicalPosition(1, 26)),
                // Caret moves to the start of the new line
                Arguments.of(LogicalPosition(2, 10), "1wl", LogicalPosition(1, 0)),
                // Caret moves to the end of the new line
                Arguments.of(LogicalPosition(1, 10), "1ek", LogicalPosition(2, 39)),
                // Caret moves to the start of the text of the new line
                Arguments.of(LogicalPosition(1, 0), "1wk", LogicalPosition(2, 4)),
            )
    }

    @ParameterizedTest
    @MethodSource("moveCaretLinePositionProvider")
    fun `test move caret line position`(
        initialPosition: LogicalPosition,
        command: String,
        expectedPosition: LogicalPosition,
    ) {
        makeEditor("1\n2\n3\n4\n5\n6\n7\n8\n9\n10\n")
        moveCaret(initialPosition)

        performCommand(command)

        assertCaretPosition(expectedPosition)
    }

    @ParameterizedTest
    @MethodSource("moveCaretColumnPositionProvider")
    fun `test move caret column position`(
        initialPosition: LogicalPosition,
        command: String,
        expectedPosition: LogicalPosition,
    ) {
        makeEditor(
            """
            Out of college, money spent
            See no future, pay no rent
                All the money's gone, nowhere to go
            """.trimIndent(),
        )
        moveCaret(initialPosition)

        performCommand(command)

        assertCaretPosition(expectedPosition)
    }

    @Test
    fun `test move caret ignores separator`() {
        makeEditor("0\n1\n2\n3\n4\n5\n6\n7\n8\n9\n")

        performCommand("3,k")

        assertCaretPosition(LogicalPosition(0, 0))
    }
}
