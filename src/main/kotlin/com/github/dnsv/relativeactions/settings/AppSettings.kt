@file:Suppress("ktlint:standard:no-wildcard-imports")

package com.github.dnsv.relativeactions.settings

import com.intellij.openapi.components.*
import com.intellij.ui.JBColor
import java.awt.Color

@State(name = "RelativeActionsSettings", storages = [(Storage("relative-actions.xml"))])
class AppSettings : PersistentStateComponent<AppSettings.State> {
    companion object {
        @JvmStatic
        fun getInstance(): AppSettings = service()
    }

    val caretBackground
        get() = state.caretBackground

    val keyDirectionUp
        get() = state.keyDirectionUp

    val keyDirectionDown
        get() = state.keyDirectionDown

    val keyDirectionBoth
        get() = state.keyDirectionBoth

    val keyActionComment
        get() = state.keyActionComment

    val keyActionCopy
        get() = state.keyActionCopy

    val keyActionCut
        get() = state.keyActionCut

    val keyActionDelete
        get() = state.keyActionDelete

    val keyActionSelect
        get() = state.keyActionSelect

    val keyPositionBeginning
        get() = state.keyPositionBeginning

    val keyPositionEnd
        get() = state.keyPositionEnd

    private var state: State = State()

    override fun getState(): State = state

    override fun loadState(state: State) {
        this.state = state
    }

    data class State(
        var caretBackground: Color = JBColor.MAGENTA,
        var keyDirectionUp: Char = 'k',
        var keyDirectionDown: Char = 'l',
        var keyDirectionBoth: Char = 'b',
        var keyActionComment: Char = 'c',
        var keyActionCopy: Char = 'y',
        var keyActionCut: Char = 'x',
        var keyActionDelete: Char = 'd',
        var keyActionSelect: Char = 's',
        var keyPositionBeginning: Char = 'w',
        var keyPositionEnd: Char = 'e',
    )
}
