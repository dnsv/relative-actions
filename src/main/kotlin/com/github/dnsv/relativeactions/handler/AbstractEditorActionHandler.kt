package com.github.dnsv.relativeactions.handler

import com.github.dnsv.relativeactions.session.SessionManager
import com.intellij.openapi.actionSystem.DataContext
import com.intellij.openapi.editor.Caret
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.actionSystem.EditorActionHandler

abstract class AbstractEditorActionHandler(
    private val nextHandler: EditorActionHandler,
) : EditorActionHandler() {
    override fun isEnabledForCaret(
        editor: Editor,
        caret: Caret,
        dataContext: DataContext?,
    ): Boolean = SessionManager.isActive() || nextHandler.isEnabled(editor, caret, dataContext)

    override fun doExecute(
        editor: Editor,
        caret: Caret?,
        dataContext: DataContext?,
    ) {
        if (SessionManager.isActive()) {
            run(editor)
        } else {
            nextHandler.execute(editor, caret, dataContext)
        }
    }

    protected abstract fun run(editor: Editor)
}
