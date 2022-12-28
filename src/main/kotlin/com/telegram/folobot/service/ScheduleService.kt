package com.telegram.folobot.service

import com.telegram.folobot.IdUtils.Companion.FOLO_CHAT_ID
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class ScheduleService(
    private val taskService: TaskService,


) {
    fun whatAboutIT() {
        taskService.whatAboutIT(FOLO_CHAT_ID)
    }

    @Scheduled(cron = "0 59 23 ? * MON-FRI")
    private fun dayStats() {
        taskService.dayStats(FOLO_CHAT_ID)
    }

    @Scheduled(cron = "5 59 23 ? * *")
    private fun foloIndex() {
        taskService.foloIndex(FOLO_CHAT_ID)
    }
}