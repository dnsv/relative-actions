package com.github.dnsv.relativeactions.relativeaction

import com.github.dnsv.relativeactions.util.LineRange
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.colors.EditorColors
import com.intellij.openapi.editor.colors.EditorColorsManager
import com.intellij.openapi.editor.markup.HighlighterLayer
import com.intellij.openapi.editor.markup.HighlighterTargetArea
import com.intellij.openapi.editor.markup.TextAttributes
import com.intellij.openapi.ide.CopyPasteManager
import com.intellij.openapi.util.TextRange
import com.intellij.util.Alarm
import java.awt.datatransfer.StringSelection

class CopyRelativeAction(
    editor: Editor,
    lineRange: LineRange,
) : AbstractRelativeAction(editor, lineRange) {
    companion object {
        private const val HIGHLIGH_DURATION_MS = 150
    }

    override fun execute() {
        val (startOffset, endOffset) = getLineOffsets()
        val selection = StringSelection(getDocument().getText(TextRange(startOffset, endOffset)))

        CopyPasteManager.getInstance().setContents(selection)

        highlightCopiedText(startOffset, endOffset)
    }

    /**
     * Provides visual feedback by temporarily highlighting the copied text.
     *
     * @param startOffset The start offset of the copied text.
     * @param endOffset The end offset of the copied text.
     */
    private fun highlightCopiedText(
        startOffset: Int,
        endOffset: Int,
    ) {
        val scheme = EditorColorsManager.getInstance().globalScheme
        val attributes =
            TextAttributes().apply {
                backgroundColor = scheme.getColor(EditorColors.SELECTION_BACKGROUND_COLOR)
                foregroundColor = scheme.getColor(EditorColors.SELECTION_FOREGROUND_COLOR)
            }
        val highlighter =
            editor.markupModel.addRangeHighlighter(
                startOffset,
                endOffset,
                HighlighterLayer.SELECTION,
                attributes,
                HighlighterTargetArea.EXACT_RANGE,
            )

        // Remove highlight after a short delay
        Alarm().addRequest({
            editor.markupModel.removeHighlighter(highlighter)
        }, HIGHLIGH_DURATION_MS)
    }
}
