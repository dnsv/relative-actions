package com.github.dnsv.relativeactions.keymap

import com.github.dnsv.relativeactions.settings.AppSettings
import com.intellij.openapi.application.ApplicationManager

enum class Direction {
    UP,
    DOWN,
    BOTH,
    ;

    companion object : AbstractKeymapEnum<Direction>() {
        override fun createKeymap(): Map<Char, Direction> {
            val settings = ApplicationManager.getApplication().getService(AppSettings::class.java)

            return mapOf(
                settings.keyDirectionUp to UP,
                settings.keyDirectionDown to DOWN,
                settings.keyDirectionBoth to BOTH,
            )
        }
    }
}
