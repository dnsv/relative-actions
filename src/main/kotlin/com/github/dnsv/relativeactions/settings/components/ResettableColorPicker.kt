package com.github.dnsv.relativeactions.settings.components

import com.intellij.ui.ColorPanel
import java.awt.Color
import javax.swing.JButton
import javax.swing.JPanel

class ResettableColorPicker(
    private val defaultColor: Color,
) : JPanel() {
    private val colorPanel = ColorPanel()
    private val resetButton = JButton("Reset")

    init {
        colorPanel.selectedColor = defaultColor
        resetButton.addActionListener { colorPanel.selectedColor = defaultColor }

        add(colorPanel)
        add(resetButton)
    }

    var selectedColor: Color
        get() = colorPanel.selectedColor!!
        set(value) {
            colorPanel.selectedColor = value
        }
}
