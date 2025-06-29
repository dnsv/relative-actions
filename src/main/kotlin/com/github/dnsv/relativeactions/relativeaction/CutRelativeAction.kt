package com.github.dnsv.relativeactions.relativeaction

import com.github.dnsv.relativeactions.util.LineRange
import com.intellij.openapi.editor.Editor

class CutRelativeAction(
    editor: Editor,
    lineRange: LineRange,
) : AbstractRelativeAction(editor, lineRange) {
    override fun execute() {
        CopyRelativeAction(editor, lineRange).execute()
        DeleteRelativeAction(editor, lineRange).execute()
    }
}
