package com.github.dnsv.relativeactions.util

import com.github.dnsv.relativeactions.keymap.Direction
import com.intellij.openapi.editor.Editor

class LineRange(
    val start: Int,
    val end: Int,
) {
    companion object {
        const val SEPARATOR = ','

        fun create(
            editor: Editor,
            range: String,
            direction: Direction,
        ): LineRange {
            val (relativeStartLine, relativeEndLine) =
                when {
                    range.isEmpty() -> 0 to 0

                    range.startsWith(SEPARATOR) || range.endsWith(SEPARATOR) -> {
                        val trimmedRange = range.trim(SEPARATOR)
                        val line = applyDirection(trimmedRange.toIntOrNull() ?: 0, direction)
                        line to line
                    }

                    SEPARATOR in range -> {
                        val (start, end) = range.split(SEPARATOR).map { it.toIntOrNull() ?: 0 }

                        when (direction) {
                            Direction.BOTH -> applyDirection(start, Direction.UP) to applyDirection(end, Direction.DOWN)
                            else -> applyDirection(start, direction) to applyDirection(end, direction)
                        }
                    }

                    // Single number
                    else -> 0 to applyDirection(range.toIntOrNull() ?: 0, direction)
                }

            val maxLine = editor.document.lineCount - 1
            val currentLine = editor.caretModel.logicalPosition.line

            val absoluteStartLine = (currentLine + relativeStartLine).coerceIn(0, maxLine)
            val absoluteEndLine = (currentLine + relativeEndLine).coerceIn(0, maxLine)

            return LineRange(
                start = minOf(absoluteStartLine, absoluteEndLine),
                end = maxOf(absoluteStartLine, absoluteEndLine),
            )
        }

        private fun applyDirection(
            line: Int,
            direction: Direction,
        ) = if (direction == Direction.DOWN) line else -line
    }

    override fun toString() = "LineRange(start=$start, end=$end)"
}
