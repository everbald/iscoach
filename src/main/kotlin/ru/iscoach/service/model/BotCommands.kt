package ru.iscoach.service.model

enum class BotCommands(val command: String) {
    START("/start"),
    PAY("/pay");

    companion object {
        private val map = BotCommands.values().associateBy(BotCommands::command)
        fun fromCommand(command: String?) = map[command]
    }
}