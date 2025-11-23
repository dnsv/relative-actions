package com.github.dnsv.relativeactions.session

import com.github.dnsv.relativeactions.settings.AppSettings
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.colors.EditorColors.CARET_COLOR
import com.intellij.openapi.editor.colors.impl.AbstractColorsScheme

internal data class RelativeActionCaret(
    private val editor: Editor,
    private val isBlockCursor: Boolean,
    private val isBlinkCaret: Boolean,
) {
    companion object {
        fun create(editor: Editor): RelativeActionCaret =
            RelativeActionCaret(
                editor = editor,
                isBlockCursor = editor.settings.isBlockCursor,
                isBlinkCaret = editor.settings.isBlinkCaret,
            )
    }

    fun activate() {
        val settings = ApplicationManager.getApplication().getService(AppSettings::class.java)

        editor.settings.isBlockCursor = true
        editor.settings.isBlinkCaret = false
        editor.colorsScheme.setColor(CARET_COLOR, settings.caretBackground)
    }

    fun restore() {
        editor.settings.isBlockCursor = isBlockCursor
        editor.settings.isBlinkCaret = isBlinkCaret
        editor.colorsScheme.setColor(CARET_COLOR, AbstractColorsScheme.INHERITED_COLOR_MARKER)
    }
}
