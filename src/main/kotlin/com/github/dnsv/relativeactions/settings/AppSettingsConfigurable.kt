package com.github.dnsv.relativeactions.settings

import com.github.dnsv.relativeactions.keymap.Direction
import com.github.dnsv.relativeactions.keymap.Position
import com.github.dnsv.relativeactions.keymap.RelativeAction
import com.intellij.openapi.options.Configurable

class AppSettingsConfigurable : Configurable {
    private val panel by lazy { AppSettingsComponent() }
    private val state get() = AppSettings.getInstance().state

    override fun createComponent() = panel.mainPanel

    override fun getDisplayName() = "Relative Actions"

    override fun isModified(): Boolean =
        state.caretBackground != panel.caretBackground ||
            state.keyDirectionUp != panel.keyDirectionUp ||
            state.keyDirectionDown != panel.keyDirectionDown ||
            state.keyDirectionBoth != panel.keyDirectionBoth ||
            state.keyActionComment != panel.keyActionComment ||
            state.keyActionCopy != panel.keyActionCopy ||
            state.keyActionCut != panel.keyActionCut ||
            state.keyActionDelete != panel.keyActionDelete ||
            state.keyActionSelect != panel.keyActionSelect ||
            state.keyPositionBeginning != panel.keyPositionBeginning ||
            state.keyPositionEnd != panel.keyPositionEnd

    override fun apply() {
        state.caretBackground = panel.caretBackground
        state.keyDirectionUp = panel.keyDirectionUp
        state.keyDirectionDown = panel.keyDirectionDown
        state.keyDirectionBoth = panel.keyDirectionBoth
        state.keyActionComment = panel.keyActionComment
        state.keyActionCopy = panel.keyActionCopy
        state.keyActionCut = panel.keyActionCut
        state.keyActionDelete = panel.keyActionDelete
        state.keyActionSelect = panel.keyActionSelect
        state.keyPositionBeginning = panel.keyPositionBeginning
        state.keyPositionEnd = panel.keyPositionEnd

        Direction.invalidateCache()
        RelativeAction.invalidateCache()
        Position.invalidateCache()
    }

    override fun reset() {
        panel.caretBackground = state.caretBackground
        panel.keyDirectionUp = state.keyDirectionUp
        panel.keyDirectionDown = state.keyDirectionDown
        panel.keyDirectionBoth = state.keyDirectionBoth
        panel.keyActionComment = state.keyActionComment
        panel.keyActionCopy = state.keyActionCopy
        panel.keyActionCut = state.keyActionCut
        panel.keyActionDelete = state.keyActionDelete
        panel.keyActionSelect = state.keyActionSelect
        panel.keyPositionBeginning = state.keyPositionBeginning
        panel.keyPositionEnd = state.keyPositionEnd
    }
}
