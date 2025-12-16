package com.github.dnsv.relativeactions

import com.github.dnsv.relativeactions.util.BaseTestCase
import com.github.dnsv.relativeactions.util.PlainTextCommenter
import com.intellij.lang.LanguageCommenters
import com.intellij.openapi.editor.LogicalPosition
import com.intellij.openapi.fileTypes.PlainTextLanguage
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class RelativeActionCommentTest : BaseTestCase() {
    companion object {
        @JvmStatic
        fun caretPositionProvider(): Stream<Arguments> =
            Stream.of(
                // Caret is moved by the amount of chars that the comment adds
                Arguments.of(LogicalPosition(3, 9), "c2l", LogicalPosition(3, 12)),
                Arguments.of(LogicalPosition(3, 9), "c1,2b", LogicalPosition(3, 12)),
                // The caret stays as-is when it's not inside the comment selection
                Arguments.of(LogicalPosition(5, 9), "c2,1l", LogicalPosition(5, 9)),
            )
    }

    @BeforeEach
    fun beforeEach() {
        LanguageCommenters.INSTANCE.addExplicitExtension(
            PlainTextLanguage.INSTANCE,
            PlainTextCommenter(),
        )
    }

    @ParameterizedTest
    @CsvSource(
        // Comment down without a number comments the current line
        "ck, '1\n2\n3\n4\n// 5\n6\n7\n8\n9\n10\n'",
        // Comment 2 lines down
        "c2k, '1\n2\n3\n4\n// 5\n// 6\n// 7\n8\n9\n10\n'",
        // Same as above, but with reversed command order (order doesn't matter)
        "2ck, '1\n2\n3\n4\n// 5\n// 6\n// 7\n8\n9\n10\n'",
        // Comment down beyond the last line — comments all to the end
        "c10k, '1\n2\n3\n4\n// 5\n// 6\n// 7\n// 8\n// 9\n// 10\n'",
        // Comment up without a number comments the current line
        "cl, '1\n2\n3\n4\n// 5\n6\n7\n8\n9\n10\n'",
        // Comment 3 lines up
        "c3l, '1\n// 2\n// 3\n// 4\n// 5\n6\n7\n8\n9\n10\n'",
        // Comment up beyond the first line — comments all to the top
        "c10l, '// 1\n// 2\n// 3\n// 4\n// 5\n6\n7\n8\n9\n10\n'",
    )
    fun `test basic comment text`(
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
        // Comment lines in both directions
        "'c3,2b', '1\n// 2\n// 3\n// 4\n// 5\n// 6\n// 7\n8\n9\n10\n'",
        // Comment lines 1 through 3 below the current line
        "'c1,3k', '1\n2\n3\n4\n5\n// 6\n// 7\n// 8\n9\n10\n'",
        // Same as above, but with reversed command order (order doesn't matter)
        "'3,1ck', '1\n2\n3\n4\n5\n// 6\n// 7\n// 8\n9\n10\n'",
        // Comment lines 1 through 10 below the current line (caps at file end)
        "'c1,10k', '1\n2\n3\n4\n5\n// 6\n// 7\n// 8\n// 9\n// 10\n'",
        // Comment lines 1 through 3 above the current line
        "'c3,1l', '1\n// 2\n// 3\n// 4\n5\n6\n7\n8\n9\n10\n'",
        // Comment lines 1 through 10 above the current line (caps at file start)
        "'c10,1l', '// 1\n// 2\n// 3\n// 4\n5\n6\n7\n8\n9\n10\n'",
    )
    fun `test multi-line comment text`(
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
        // Uncomment 2 lines down
        "c2k, '1\n2\n3\n4\n5\n6\n7\n// 8\n// 9\n10\n'",
        // Uncomment lines 1 through 3 below the current line
        "'c1,3k', '1\n2\n3\n4\n// 5\n6\n7\n8\n// 9\n10\n'",
    )
    fun `test uncomment text`(
        command: String,
        expectedText: String,
    ) {
        makeEditor("1\n2\n3\n4\n// 5\n// 6\n// 7\n// 8\n// 9\n10\n")
        moveCaret(LogicalPosition(4, 0)) // At "5\n"

        performCommand(command)

        assertText(expectedText)
    }

    fun `test doesn't uncomment (toggle) when only part of the selection is commented`() {
        makeEditor("1\n2\n3\n4\n// 5\n6\n7\n8\n9\n10\n")
        moveCaret(LogicalPosition(4, 0)) // At "5\n"

        performCommand("c1k")

        assertText("1\n2\n3\n4\n// // 5\n// 6\n7\n8\n9\n10\n")
    }

    @ParameterizedTest
    @MethodSource("caretPositionProvider")
    fun `test comment text caret position`(
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
    fun `test comment text one line shortcut`() {
        makeEditor("1\n2\n3\n4\n5\n6\n7\n8\n9\n10\n")
        moveCaret(LogicalPosition(4, 0)) // At "5\n"

        // Comment line 3 below the current line
        performCommand("c3,k")

        assertText("1\n2\n3\n4\n5\n6\n7\n// 8\n9\n10\n")
    }
}
