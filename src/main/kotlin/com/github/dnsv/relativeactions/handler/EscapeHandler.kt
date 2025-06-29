package com.github.dnsv.relativeactions.handler

import com.github.dnsv.relativeactions.session.SessionManager
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.actionSystem.EditorActionHandler

class EscapeHandler(
    nextHandler: EditorActionHandler,
) : AbstractEditorActionHandler(nextHandler) {
    override fun run(editor: Editor) = SessionManager.end()
}
