package com.github.dnsv.relativeactions.relativeaction

import com.github.dnsv.relativeactions.util.LineRange
import com.intellij.openapi.editor.Editor

abstract class AbstractRelativeAction(
    protected val editor: Editor,
    protected val lineRange: LineRange,
) {
    abstract fun execute()

    protected fun getLineOffsets(): Pair<Int, Int> {
        val startOffset = getDocument().getLineStartOffset(lineRange.start)
        var endOffset = getDocument().getLineEndOffset(lineRange.end)

        // Add 1 to include the newline after the end line (if present), ensuring the range covers the entire line
        endOffset = minOf(endOffset + 1, getMaxLineOffset())

        return Pair(startOffset, endOffset)
    }

    protected fun getDocument() = editor.document

    protected fun getCurrentLine() = editor.caretModel.logicalPosition.line

    protected fun getCurrentLogicalPosition() = editor.caretModel.logicalPosition

    private fun getMaxLineOffset() = getDocument().getLineEndOffset(getDocument().lineCount - 1)
}
