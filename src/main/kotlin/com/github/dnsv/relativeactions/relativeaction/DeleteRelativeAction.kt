package com.github.dnsv.relativeactions.relativeaction

import com.github.dnsv.relativeactions.util.LineRange
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.editor.Editor

class DeleteRelativeAction(
    editor: Editor,
    lineRange: LineRange,
) : AbstractRelativeAction(editor, lineRange) {
    override fun execute() {
        val (startOffset, endOffset) = getLineOffsets()

        WriteCommandAction.runWriteCommandAction(editor.project) {
            getDocument().deleteString(startOffset, endOffset)
        }
    }
}
