package com.github.dnsv.relativeactions.input

import com.github.dnsv.relativeactions.keymap.Direction
import com.github.dnsv.relativeactions.keymap.Position
import com.github.dnsv.relativeactions.keymap.RelativeAction
import com.github.dnsv.relativeactions.relativeaction.RelativeActionProcessor
import com.github.dnsv.relativeactions.util.LineRange
import com.github.dnsv.relativeactions.util.Notify
import com.intellij.openapi.editor.Editor

class KeyProcessor(
    private val editor: Editor,
) {
    private val commandInput = StringBuilder()
    private val lineRangeInput = StringBuilder()
    private var position: Position = Position.INITIAL
    private var relativeAction: RelativeAction = RelativeAction.MOVE

    /**
     * Processes a single character input from the user.
     *
     * @param char The character to process.
     * @return `true` if the command was executed, `false` otherwise.
     */
    fun executeKey(char: Char): Boolean {
        commandInput.append(char)

        when {
            char.isDigit() || (char == LineRange.SEPARATOR && char !in lineRangeInput) -> lineRangeInput.append(char)

            Position.containsKey(char) -> position = Position.getValue(char)

            RelativeAction.containsKey(char) -> relativeAction = RelativeAction.getValue(char)

            // The action is executed only when a direction key is pressed
            Direction.containsKey(char) -> {
                executeCommand(Direction.getValue(char))
                return true
            }

            else -> {
                // A warning is shown and the char is removed from the command input if it doesn't match any binding.
                // The user can then continue with the command as if nothing happened.
                commandInput.deleteCharAt(commandInput.length - 1)
                Notify.warning(editor, "No binding for key: '$char'.")
            }
        }

        return false
    }

    private fun executeCommand(direction: Direction) {
        if (!validateCommand(direction)) return

        val lineRange = LineRange.create(editor, lineRangeInput.toString(), direction)

        RelativeActionProcessor.executeAction(editor, lineRange, position, relativeAction)
    }

    private fun validateCommand(direction: Direction): Boolean =
        when {
            direction == Direction.BOTH && relativeAction == RelativeAction.MOVE -> {
                val key = Direction.getKey(direction)
                Notify.warning(
                    editor,
                    "Invalid command '$commandInput': '$key' (bidirectional) cannot be used for movement.",
                )
                false
            }

            position == Position.BEGINNING && relativeAction != RelativeAction.MOVE -> {
                val key = Position.getKey(position)
                Notify.warning(
                    editor,
                    "Invalid command '$commandInput': '$key' (beginning of line) can only be used for movement.",
                )
                false
            }

            position == Position.END && relativeAction != RelativeAction.MOVE -> {
                val key = Position.getKey(position)
                Notify.warning(
                    editor,
                    "Invalid command '$commandInput': '$key' (end of line) can only be used for movement.",
                )
                false
            }

            else -> true
        }
}
