package com.github.dnsv.relativeactions.util

import com.intellij.notification.NotificationGroupManager
import com.intellij.notification.NotificationType
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project

object Notify {
    const val NOTIFICATION_TITLE = "Relative Actions"

    private val notificationGroup = NotificationGroupManager.getInstance().getNotificationGroup("relative-actions")

    fun warning(
        editor: Editor,
        message: String,
    ) = createNotification(editor.project, message, NotificationType.WARNING)

    private fun createNotification(
        project: Project?,
        message: String,
        type: NotificationType,
    ) = notificationGroup.createNotification(NOTIFICATION_TITLE, message, type).notify(project)
}
