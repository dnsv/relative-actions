package com.github.dnsv.relativeactions.settings

import com.github.dnsv.relativeactions.keymap.Direction
import com.github.dnsv.relativeactions.keymap.Position
import com.github.dnsv.relativeactions.keymap.RelativeAction
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.options.Configurable

class AppSettingsConfigurable : Configurable {
    private val panel by lazy { AppSettingsComponent() }
    private val settings get() = ApplicationManager.getApplication().getService(AppSettings::class.java)

    override fun createComponent() = panel.mainPanel

    override fun getDisplayName() = "Relative Actions"

    override fun isModified(): Boolean =
        settings.caretBackground != panel.caretBackground ||
            settings.keyDirectionUp != panel.keyDirectionUp ||
            settings.keyDirectionDown != panel.keyDirectionDown ||
            settings.keyDirectionBoth != panel.keyDirectionBoth ||
            settings.keyActionComment != panel.keyActionComment ||
            settings.keyActionCopy != panel.keyActionCopy ||
            settings.keyActionCut != panel.keyActionCut ||
            settings.keyActionDelete != panel.keyActionDelete ||
            settings.keyActionSelect != panel.keyActionSelect ||
            settings.keyPositionBeginning != panel.keyPositionBeginning ||
            settings.keyPositionEnd != panel.keyPositionEnd

    override fun apply() {
        settings.caretBackground = panel.caretBackground
        settings.keyDirectionUp = panel.keyDirectionUp
        settings.keyDirectionDown = panel.keyDirectionDown
        settings.keyDirectionBoth = panel.keyDirectionBoth
        settings.keyActionComment = panel.keyActionComment
        settings.keyActionCopy = panel.keyActionCopy
        settings.keyActionCut = panel.keyActionCut
        settings.keyActionDelete = panel.keyActionDelete
        settings.keyActionSelect = panel.keyActionSelect
        settings.keyPositionBeginning = panel.keyPositionBeginning
        settings.keyPositionEnd = panel.keyPositionEnd

        Direction.invalidateCache()
        RelativeAction.invalidateCache()
        Position.invalidateCache()
    }

    override fun reset() {
        panel.caretBackground = settings.caretBackground
        panel.keyDirectionUp = settings.keyDirectionUp
        panel.keyDirectionDown = settings.keyDirectionDown
        panel.keyDirectionBoth = settings.keyDirectionBoth
        panel.keyActionComment = settings.keyActionComment
        panel.keyActionCopy = settings.keyActionCopy
        panel.keyActionCut = settings.keyActionCut
        panel.keyActionDelete = settings.keyActionDelete
        panel.keyActionSelect = settings.keyActionSelect
        panel.keyPositionBeginning = settings.keyPositionBeginning
        panel.keyPositionEnd = settings.keyPositionEnd
    }
}
