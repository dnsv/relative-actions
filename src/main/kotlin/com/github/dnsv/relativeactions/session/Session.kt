package com.github.dnsv.relativeactions.session

import com.github.dnsv.relativeactions.input.KeyProcessor
import com.intellij.openapi.Disposable
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.EditorFactory
import com.intellij.openapi.editor.event.EditorFactoryEvent
import com.intellij.openapi.editor.event.EditorFactoryListener
import com.intellij.openapi.util.Disposer

class Session(
    val editor: Editor,
    private val listener: SessionListener,
) : Disposable {
    private val sessionDisposable: Disposable
    private val relativeActionCaret = RelativeActionCaret.create(editor)
    private val keyProcessor = KeyProcessor(editor)

    init {
        EditorTypedActionHandler.addHandler(
            editor,
        ) { editor, charTyped, dataContext ->
            val isCommandExecuted = keyProcessor.executeKey(charTyped)

            if (isCommandExecuted) {
                listener.onSessionEnd()
            }
        }

        sessionDisposable = Disposer.newDisposable(this)
        EditorFactory.getInstance().addEditorFactoryListener(
            object : EditorFactoryListener {
                override fun editorReleased(event: EditorFactoryEvent) {
                    listener.onSessionEnd()
                }
            },
            sessionDisposable,
        )

        relativeActionCaret.activate()
    }

    override fun dispose() {
        EditorTypedActionHandler.removeHandler(editor)
        Disposer.dispose(sessionDisposable)
        relativeActionCaret.restore()
    }
}
