package com.github.dnsv.relativeactions.session

import com.intellij.openapi.actionSystem.DataContext
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.actionSystem.TypedAction
import com.intellij.openapi.editor.actionSystem.TypedActionHandler

object EditorTypedActionHandler : TypedActionHandler {
    private val handlers = mutableMapOf<Editor, TypedActionHandler>()
    private var originalHandler: TypedActionHandler? = null

    override fun execute(
        editor: Editor,
        charTyped: Char,
        dataContext: DataContext,
    ) {
        val handler = handlers[editor] ?: originalHandler

        handler?.execute(editor, charTyped, dataContext)
    }

    fun addHandler(
        editor: Editor,
        handler: TypedActionHandler,
    ) {
        if (handlers.isEmpty()) {
            originalHandler = TypedAction.getInstance().rawHandler
            TypedAction.getInstance().setupRawHandler(this)
        }

        handlers[editor] = handler
    }

    fun removeHandler(editor: Editor) {
        handlers.remove(editor)

        if (handlers.isEmpty()) {
            originalHandler?.let { TypedAction.getInstance().setupRawHandler(it) }
            originalHandler = null
        }
    }
}
