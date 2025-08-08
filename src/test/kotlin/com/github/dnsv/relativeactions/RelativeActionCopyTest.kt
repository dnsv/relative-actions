package com.github.dnsv.relativeactions

import com.github.dnsv.relativeactions.util.BaseTestCase
import com.intellij.openapi.editor.LogicalPosition
import com.intellij.testFramework.PlatformTestUtil
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class RelativeActionCopyTest : BaseTestCase() {
    @ParameterizedTest
    @CsvSource(
        // Copy down without a number copies the current line
        "yl, '5\n'",
        // Copy 3 lines down
        "y3l, '5\n6\n7\n8\n'",
        // Same as above, but with reversed command order (order doesn't matter)
        "3yl, '5\n6\n7\n8\n'",
        // Copy down beyond the last line — copies all to the end
        "y10l, '5\n6\n7\n8\n9\n10\n'",
        // Copy up without a number copies the current line
        "yk, '5\n'",
        // Copy 2 lines up
        "y2k, '3\n4\n5\n'",
        // Copy up beyond the first line — copies all to the top
        "y10k, '1\n2\n3\n4\n5\n'",
    )
    fun `test basic copy text`(
        command: String,
        expectedText: String,
    ) {
        makeEditor("1\n2\n3\n4\n5\n6\n7\n8\n9\n10\n")
        moveCaret(LogicalPosition(4, 0)) // At "5\n"

        performCommand(command)

        assertClipboard(expectedText)
    }

    @ParameterizedTest
    @CsvSource(
        // Copy lines in both directions
        "'y3,2b', '2\n3\n4\n5\n6\n7\n'",
        // Copy lines 1 through 3 below the current line
        "'y1,3l', '6\n7\n8\n'",
        // Same as above, but with reversed command order (order doesn't matter)
        "'3,1yl', '6\n7\n8\n'",
        // Copy lines 2 through 10 below the current line (caps at file end)
        "'y2,10l', '7\n8\n9\n10\n'",
        // Copy lines 1 through 3 above the current line
        "'y3,1k', '2\n3\n4\n'",
        // Copy lines 3 through 10 above the current line (caps at file start)
        "'y10,3k', '1\n2\n'",
    )
    fun `test multi-line copy text`(
        command: String,
        expectedText: String,
    ) {
        makeEditor("1\n2\n3\n4\n5\n6\n7\n8\n9\n10\n")
        moveCaret(LogicalPosition(4, 0)) // At "5\n"

        performCommand(command)

        assertClipboard(expectedText)
    }

    @Test
    fun `test copy text one line shortcut`() {
        makeEditor("1\n2\n3\n4\n5\n6\n7\n8\n9\n10\n")
        moveCaret(LogicalPosition(4, 0)) // At "5\n"

        // Copy line 3 above the current line
        performCommand("3,yk")

        assertClipboard("2\n")
    }

    @Test
    fun `test copied text is temporarily highlighted`() {
        makeEditor("1\n2\n3\n4\n5\n6\n7\n8\n9\n10\n")
        moveCaret(LogicalPosition(4, 0)) // At "5\n"

        // Copy 3 lines down
        performCommand("y3l")

        val editor = getEditor()
        val highlighters = editor.markupModel.allHighlighters.toList()

        Assertions.assertEquals(1, highlighters.size)

        val highlighter = highlighters.first()
        val highlightedText = editor.document.text.substring(highlighter.startOffset, highlighter.endOffset)

        Assertions.assertEquals("5\n6\n7\n8\n", highlightedText)
        assertClipboard(highlightedText)

        // Check that the highlight is removed after a short delay
        PlatformTestUtil.waitForAlarm(150)
        val highlightersAfter = editor.markupModel.allHighlighters.toList()

        Assertions.assertEquals(0, highlightersAfter.size)
    }
}
