package com.github.dnsv.relativeactions.relativeaction

import com.github.dnsv.relativeactions.util.LineRange
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.ide.CopyPasteManager
import com.intellij.openapi.util.TextRange
import java.awt.datatransfer.StringSelection

class CopyRelativeAction(
    editor: Editor,
    lineRange: LineRange,
) : AbstractRelativeAction(editor, lineRange) {
    override fun execute() {
        val (startOffset, endOffset) = getLineOffsets()
        val selection = StringSelection(getDocument().getText(TextRange(startOffset, endOffset)))

        CopyPasteManager.getInstance().setContents(selection)
    }
}
