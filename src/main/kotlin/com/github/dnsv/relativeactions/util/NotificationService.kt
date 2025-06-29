package com.github.dnsv.relativeactions.util

import com.intellij.notification.NotificationGroupManager
import com.intellij.notification.NotificationType
import com.intellij.openapi.project.Project

object NotificationService {
    const val NOTIFICATION_TITLE = "Relative Actions"

    private val notificationGroup = NotificationGroupManager.getInstance().getNotificationGroup("relative-actions")

    fun info(
        project: Project?,
        message: String,
    ) {
        createNotification(project, message, NotificationType.INFORMATION)
    }

    private fun createNotification(
        project: Project?,
        message: String,
        type: NotificationType,
    ) {
        notificationGroup.createNotification(NOTIFICATION_TITLE, message, type).notify(project)
    }
}
