package com.github.dnsv.relativeactions.relativeaction

import com.github.dnsv.relativeactions.util.LineRange
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.ScrollType

class SelectRelativeAction(
    editor: Editor,
    lineRange: LineRange,
) : AbstractRelativeAction(editor, lineRange) {
    override fun execute() {
        val (startOffset, endOffset) = getLineOffsets()

        editor.selectionModel.setSelection(startOffset, endOffset)
        moveCaretToSelection(startOffset, endOffset)
    }

    private fun moveCaretToSelection(
        startOffset: Int,
        endOffset: Int,
    ) {
        val currentLogicalPositionOffset = editor.logicalPositionToOffset(getCurrentLogicalPosition())

        when {
            currentLogicalPositionOffset in startOffset..endOffset -> {
                // The caret is already inside the selection
            }

            currentLogicalPositionOffset < startOffset -> {
                editor.caretModel.moveToOffset(endOffset)
                editor.scrollingModel.scrollToCaret(ScrollType.RELATIVE)
                editor.caretModel.moveToOffset(startOffset)
            }

            else -> { // Caret is after the selection
                editor.caretModel.moveToOffset(startOffset)
                editor.scrollingModel.scrollToCaret(ScrollType.RELATIVE)
            }
        }
    }
}
