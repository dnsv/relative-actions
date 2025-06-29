package com.github.dnsv.relativeactions.handler

import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.actionSystem.EditorActionHandler

class PreventHandler(
    nextHandler: EditorActionHandler,
) : AbstractEditorActionHandler(nextHandler) {
    override fun run(editor: Editor) {
        // Prevent other handlers from running during an active session
    }
}
