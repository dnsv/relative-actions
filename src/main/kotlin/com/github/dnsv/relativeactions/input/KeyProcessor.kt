package com.github.dnsv.relativeactions.input

import com.github.dnsv.relativeactions.keymap.Direction
import com.github.dnsv.relativeactions.keymap.Position
import com.github.dnsv.relativeactions.keymap.RelativeAction
import com.github.dnsv.relativeactions.relativeaction.RelativeActionProcessor
import com.github.dnsv.relativeactions.util.LineRange
import com.intellij.openapi.editor.Editor

class KeyProcessor(
    private val editor: Editor,
) {
    private val lineBuffer = StringBuilder()
    private var position: Position = Position.INITIAL
    private var relativeAction: RelativeAction = RelativeAction.MOVE

    /**
     * Processes a single character input from the user.
     *
     * @param char The character to process.
     * @return `true` if the command was executed, `false` otherwise.
     */
    fun executeKey(char: Char): Boolean {
        when {
            char.isDigit() || (char == LineRange.SEPARATOR && char !in lineBuffer) -> lineBuffer.append(char)

            Position.containsKey(char) -> position = Position.getValue(char)

            RelativeAction.containsKey(char) -> relativeAction = RelativeAction.getValue(char)

            // The action is executed only when a direction key is pressed
            Direction.containsKey(char) -> {
                executeAction(Direction.getValue(char))
                return true
            }
        }

        return false
    }

    private fun executeAction(direction: Direction) {
        val lineRange = LineRange.create(editor, lineBuffer.toString(), direction)

        RelativeActionProcessor.executeAction(editor, lineRange, position, relativeAction)
    }
}
