package com.github.dnsv.relativeactions.relativeaction

import com.github.dnsv.relativeactions.util.LineRange
import com.intellij.openapi.actionSystem.ActionManager
import com.intellij.openapi.actionSystem.IdeActions
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.ui.playback.commands.ActionCommand

class CommentRelativeAction(
    editor: Editor,
    lineRange: LineRange,
) : AbstractRelativeAction(editor, lineRange) {
    override fun execute() {
        val (startOffset, endOffset) = getLineOffsets()
        val action = ActionManager.getInstance().getAction(IdeActions.ACTION_COMMENT_LINE)
        val event = ActionCommand.getInputEvent(IdeActions.ACTION_COMMENT_LINE)

        editor.selectionModel.setSelection(startOffset, endOffset)

        ActionManager.getInstance().tryToExecute(action, event, null, null, true)

        editor.selectionModel.removeSelection()
    }
}
