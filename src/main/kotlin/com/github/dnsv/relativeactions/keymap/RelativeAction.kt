package com.github.dnsv.relativeactions.keymap

import com.github.dnsv.relativeactions.settings.AppSettings

enum class RelativeAction {
    COMMENT,
    COPY,
    CUT,
    DELETE,
    SELECT,
    MOVE,
    ;

    companion object : AbstractKeymapEnum<RelativeAction>() {
        override fun createKeymap(): Map<Char, RelativeAction> {
            val settings = AppSettings.getInstance()

            return mapOf(
                settings.keyActionComment to COMMENT,
                settings.keyActionCopy to COPY,
                settings.keyActionCut to CUT,
                settings.keyActionDelete to DELETE,
                settings.keyActionSelect to SELECT,
            )
        }
    }
}
