package ru.iscoach.service.handler

import jakarta.annotation.Priority
import mu.KLogging
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.EntityType
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.api.objects.Update
import ru.iscoach.config.BotCredentialsConfig
import ru.iscoach.extrensions.addActionReceived
import ru.iscoach.extrensions.getBotCommand
import ru.iscoach.extrensions.isNotForward
import ru.iscoach.service.PayService
import ru.iscoach.service.model.Actions
import ru.iscoach.service.model.BotCommands

@Component
@Priority(1)
class CommandHandler(
    private val payService: PayService,
    private val botCredentials: BotCredentialsConfig
) : Handler, KLogging() {
    fun Message.isMyCommand() =
        this.isCommand && this.isNotForward() &&
                (this.chat.isUserChat ||
                        this.entities.firstOrNull { it.type == EntityType.BOTCOMMAND }?.text
                            ?.contains(botCredentials.botUsername) == true)

    override fun canHandle(update: Update): Boolean {
        return update.message.isMyCommand().also { if (it) logger.addActionReceived(Actions.COMMAND) }
    }

    override fun handle(update: Update) {
        when (
            BotCommands.fromCommand(update.message.getBotCommand())
                .also { logger.info { "Received command ${it ?: "UNDEFINED"}" } }
        ) {
            BotCommands.START -> payService.sendInvoice(update)
            BotCommands.PAY -> payService.sendInvoice(update)
            else -> {}
        }
    }
}