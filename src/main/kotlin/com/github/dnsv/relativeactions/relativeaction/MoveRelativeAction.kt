package com.github.dnsv.relativeactions.relativeaction

import com.github.dnsv.relativeactions.keymap.Position
import com.github.dnsv.relativeactions.util.LineRange
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.ScrollType
import com.intellij.openapi.util.TextRange

class MoveRelativeAction(
    editor: Editor,
    lineRange: LineRange,
    private val position: Position,
) : AbstractRelativeAction(editor, lineRange) {
    override fun execute() {
        if (getCurrentLine() !in listOf(lineRange.start, lineRange.end)) return

        val targetLine = if (lineRange.start != getCurrentLine()) lineRange.start else lineRange.end
        val targetStartOffset = getDocument().getLineStartOffset(targetLine)
        val targetEndOffset = getDocument().getLineEndOffset(targetLine)

        val targetOffset =
            when (position) {
                Position.INITIAL -> {
                    val currentColumn = editor.caretModel.offset - getDocument().getLineStartOffset(getCurrentLine())
                    val maxColumn = targetEndOffset - targetStartOffset

                    targetStartOffset + minOf(currentColumn, maxColumn)
                }

                Position.BEGINNING -> {
                    val lineText = getDocument().getText(TextRange(targetStartOffset, targetEndOffset))
                    val firstNonWhitespaceColumn = lineText.indexOfFirst { !it.isWhitespace() }

                    targetStartOffset + firstNonWhitespaceColumn
                }

                Position.END -> targetEndOffset
            }

        editor.caretModel.moveToOffset(targetOffset)
        editor.scrollingModel.scrollToCaret(ScrollType.RELATIVE)
    }
}
