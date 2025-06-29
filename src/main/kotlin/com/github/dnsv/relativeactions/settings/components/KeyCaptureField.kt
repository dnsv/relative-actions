package com.github.dnsv.relativeactions.settings.components

import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent
import javax.swing.JButton
import javax.swing.JPanel
import javax.swing.JTextField
import javax.swing.SwingConstants

class KeyCaptureField(
    private val defaultKey: Char,
) : JPanel() {
    private val textField = JTextField(3)
    private val resetButton = JButton("Reset")

    init {
        textField.isEditable = false
        textField.text = defaultKey.toString()
        textField.horizontalAlignment = SwingConstants.CENTER

        textField.addKeyListener(
            object : KeyAdapter() {
                override fun keyPressed(e: KeyEvent) {
                    val keyChar = e.keyChar

                    if (keyChar.isLetter() && keyChar.isLowerCase()) {
                        textField.text = keyChar.toString()
                    }

                    e.consume()
                }
            },
        )

        resetButton.addActionListener { textField.text = defaultKey.toString() }

        add(textField)
        add(resetButton)
    }

    var selectedKey: Char
        get() = textField.text.firstOrNull() ?: defaultKey
        set(value) {
            textField.text = value.toString()
        }
}
