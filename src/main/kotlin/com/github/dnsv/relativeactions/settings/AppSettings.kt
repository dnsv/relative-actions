@file:Suppress("ktlint:standard:no-wildcard-imports")

package com.github.dnsv.relativeactions.settings

import com.intellij.openapi.components.SerializablePersistentStateComponent
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.util.xmlb.Converter
import com.intellij.util.xmlb.annotations.OptionTag
import java.awt.Color

@Service()
@State(name = "RelativeActionsSettings", storages = [Storage("relative-actions.xml")])
class AppSettings : SerializablePersistentStateComponent<AppSettings.State>(State()) {
    var caretBackground: Color
        get() = state.caretBackground
        set(value) {
            updateState { it.copy(caretBackground = value) }
        }

    var keyDirectionUp: Char
        get() = state.keyDirectionUp
        set(value) {
            updateState { it.copy(keyDirectionUp = value) }
        }

    var keyDirectionDown: Char
        get() = state.keyDirectionDown
        set(value) {
            updateState { it.copy(keyDirectionDown = value) }
        }

    var keyDirectionBoth: Char
        get() = state.keyDirectionBoth
        set(value) {
            updateState { it.copy(keyDirectionBoth = value) }
        }

    var keyActionComment: Char
        get() = state.keyActionComment
        set(value) {
            updateState { it.copy(keyActionComment = value) }
        }

    var keyActionCopy: Char
        get() = state.keyActionCopy
        set(value) {
            updateState { it.copy(keyActionCopy = value) }
        }

    var keyActionCut: Char
        get() = state.keyActionCut
        set(value) {
            updateState { it.copy(keyActionCut = value) }
        }

    var keyActionDelete: Char
        get() = state.keyActionDelete
        set(value) {
            updateState { it.copy(keyActionDelete = value) }
        }

    var keyActionSelect: Char
        get() = state.keyActionSelect
        set(value) {
            updateState { it.copy(keyActionSelect = value) }
        }

    var keyPositionBeginning: Char
        get() = state.keyPositionBeginning
        set(value) {
            updateState { it.copy(keyPositionBeginning = value) }
        }

    var keyPositionEnd: Char
        get() = state.keyPositionEnd
        set(value) {
            updateState { it.copy(keyPositionEnd = value) }
        }

    data class State(
        @OptionTag(converter = ColorConverter::class)
        var caretBackground: Color = Color.MAGENTA,
        @OptionTag(converter = CharConverter::class)
        var keyDirectionUp: Char = 'k',
        @OptionTag(converter = CharConverter::class)
        var keyDirectionDown: Char = 'l',
        @OptionTag(converter = CharConverter::class)
        var keyDirectionBoth: Char = 'b',
        @OptionTag(converter = CharConverter::class)
        var keyActionComment: Char = 'c',
        @OptionTag(converter = CharConverter::class)
        var keyActionCopy: Char = 'y',
        @OptionTag(converter = CharConverter::class)
        var keyActionCut: Char = 'x',
        @OptionTag(converter = CharConverter::class)
        var keyActionDelete: Char = 'd',
        @OptionTag(converter = CharConverter::class)
        var keyActionSelect: Char = 's',
        @OptionTag(converter = CharConverter::class)
        var keyPositionBeginning: Char = 'w',
        @OptionTag(converter = CharConverter::class)
        var keyPositionEnd: Char = 'e',
    )

    class CharConverter : Converter<Char>() {
        override fun fromString(value: String): Char = value.first()

        override fun toString(value: Char): String = value.toString()
    }

    class ColorConverter : Converter<Color>() {
        override fun fromString(value: String): Color {
            val hexString = value.removePrefix("#")
            val rgb = hexString.toInt(16)

            return Color(rgb)
        }

        override fun toString(value: Color): String = "#%06X".format(value.rgb and 0xFFFFFF)
    }
}
