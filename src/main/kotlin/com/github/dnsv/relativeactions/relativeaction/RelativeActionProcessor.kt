package com.github.dnsv.relativeactions.relativeaction

import com.github.dnsv.relativeactions.keymap.Position
import com.github.dnsv.relativeactions.keymap.RelativeAction
import com.github.dnsv.relativeactions.util.LineRange
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.command.CommandProcessor as IntellijCommandProcesor

class RelativeActionProcessor {
    companion object {
        fun executeAction(
            editor: Editor,
            lineRange: LineRange,
            position: Position,
            action: RelativeAction,
        ) {
            IntellijCommandProcesor.getInstance().executeCommand(editor.project, {
                when (action) {
                    RelativeAction.COMMENT -> CommentRelativeAction(editor, lineRange).execute()
                    RelativeAction.COPY -> CopyRelativeAction(editor, lineRange).execute()
                    RelativeAction.CUT -> CutRelativeAction(editor, lineRange).execute()
                    RelativeAction.DELETE -> DeleteRelativeAction(editor, lineRange).execute()
                    RelativeAction.MOVE -> MoveRelativeAction(editor, lineRange, position).execute()
                    RelativeAction.SELECT -> SelectRelativeAction(editor, lineRange).execute()
                }
            }, "Relative Action", null)
        }
    }
}
