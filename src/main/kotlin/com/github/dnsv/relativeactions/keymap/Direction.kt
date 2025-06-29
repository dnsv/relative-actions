package com.github.dnsv.relativeactions.keymap

import com.github.dnsv.relativeactions.settings.AppSettings

enum class Direction {
    UP,
    DOWN,
    BOTH,
    ;

    companion object : AbstractKeymapEnum<Direction>() {
        override fun createKeymap(): Map<Char, Direction> {
            val settings = AppSettings.getInstance()

            return mapOf(
                settings.keyDirectionUp to UP,
                settings.keyDirectionDown to DOWN,
                settings.keyDirectionBoth to BOTH,
            )
        }
    }
}
