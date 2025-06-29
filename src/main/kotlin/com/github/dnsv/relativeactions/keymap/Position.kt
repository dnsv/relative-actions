package com.github.dnsv.relativeactions.keymap

import com.github.dnsv.relativeactions.settings.AppSettings

enum class Position {
    // The initial position isn't mapped to a key
    INITIAL,
    BEGINNING,
    END,
    ;

    companion object : AbstractKeymapEnum<Position>() {
        override fun createKeymap(): Map<Char, Position> {
            val settings = AppSettings.getInstance()

            return mapOf(
                settings.keyPositionBeginning to BEGINNING,
                settings.keyPositionEnd to END,
            )
        }
    }
}
