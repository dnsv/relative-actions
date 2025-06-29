@file:Suppress("ktlint:standard:no-wildcard-imports")

package com.github.dnsv.relativeactions.settings

import com.github.dnsv.relativeactions.settings.components.KeyCaptureField
import com.github.dnsv.relativeactions.settings.components.ResettableColorPicker
import com.intellij.ui.dsl.builder.*
import javax.swing.JPanel

class AppSettingsComponent {
    private val defaults = AppSettings.State()

    private val caretBackgroundPicker = ResettableColorPicker(defaults.caretBackground)
    private val keyDirectionUpField = KeyCaptureField(defaults.keyDirectionUp)
    private val keyDirectionDownField = KeyCaptureField(defaults.keyDirectionDown)
    private val keyDirectionBothField = KeyCaptureField(defaults.keyDirectionBoth)
    private val keyActionCommentField = KeyCaptureField(defaults.keyActionComment)
    private val keyActionCopyField = KeyCaptureField(defaults.keyActionCopy)
    private val keyActionCutField = KeyCaptureField(defaults.keyActionCut)
    private val keyActionDeleteField = KeyCaptureField(defaults.keyActionDelete)
    private val keyActionSelectField = KeyCaptureField(defaults.keyActionSelect)
    private val keyPositionBeginningField = KeyCaptureField(defaults.keyPositionBeginning)
    private val keyPositionEndField = KeyCaptureField(defaults.keyPositionEnd)

    internal val mainPanel: JPanel =
        panel {
            group("Caret") {
                row("Caret background:") { cell(caretBackgroundPicker) }
            }

            group("Direction") {
                row("Up:") { cell(keyDirectionUpField) }
                row("Down:") { cell(keyDirectionDownField) }
                row("Both:") { cell(keyDirectionBothField) }
            }

            group("Action") {
                row("Comment:") { cell(keyActionCommentField) }
                row("Copy:") { cell(keyActionCopyField) }
                row("Cut:") { cell(keyActionCutField) }
                row("Delete:") { cell(keyActionDeleteField) }
                row("Select:") { cell(keyActionSelectField) }
            }

            group("Position") {
                row("Beginning:") { cell(keyPositionBeginningField) }
                row("End:") { cell(keyPositionEndField) }
            }
        }

    var caretBackground
        get() = caretBackgroundPicker.selectedColor
        set(value) {
            caretBackgroundPicker.selectedColor = value
        }

    var keyDirectionUp
        get() = keyDirectionUpField.selectedKey
        set(value) {
            keyDirectionUpField.selectedKey = value
        }

    var keyDirectionDown
        get() = keyDirectionDownField.selectedKey
        set(value) {
            keyDirectionDownField.selectedKey = value
        }

    var keyDirectionBoth
        get() = keyDirectionBothField.selectedKey
        set(value) {
            keyDirectionBothField.selectedKey = value
        }

    var keyActionComment
        get() = keyActionCommentField.selectedKey
        set(value) {
            keyActionCommentField.selectedKey = value
        }

    var keyActionCopy
        get() = keyActionCopyField.selectedKey
        set(value) {
            keyActionCopyField.selectedKey = value
        }

    var keyActionCut
        get() = keyActionCutField.selectedKey
        set(value) {
            keyActionCutField.selectedKey = value
        }

    var keyActionDelete
        get() = keyActionDeleteField.selectedKey
        set(value) {
            keyActionDeleteField.selectedKey = value
        }

    var keyActionSelect
        get() = keyActionSelectField.selectedKey
        set(value) {
            keyActionSelectField.selectedKey = value
        }

    var keyPositionBeginning
        get() = keyPositionBeginningField.selectedKey
        set(value) {
            keyPositionBeginningField.selectedKey = value
        }

    var keyPositionEnd
        get() = keyPositionEndField.selectedKey
        set(value) {
            keyPositionEndField.selectedKey = value
        }
}
