package com.github.dnsv.relativeactions.session

import com.github.dnsv.relativeactions.util.NotificationService
import com.intellij.openapi.editor.Editor

/**
 * Manages the current [Session]. Only one [Session] can be active at a time.
 */
object SessionManager : SessionListener {
    private var session: Session? = null

    override fun onSessionEnd() {
        end()
    }

    fun start(editor: Editor) {
        if (editor.caretModel.caretCount > 1) {
            NotificationService.info(editor.project, "Relative Actions can't run while multiple carets are active.")
            return
        }

        if (isActive()) end()

        session = Session(editor, this)
    }

    fun restart(editor: Editor) {
        start(editor)
    }

    fun end() {
        session?.dispose()
        session = null
    }

    fun isActive() = session != null
}
