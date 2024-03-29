package ru.iscoach.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
class BotCredentialsConfig {
    @Value("\${bot.username}")
    val botUsername: String = ""

    @Value("\${bot.token}")
    val botToken: String = ""

    @Value("\${bot.provider.token}")
    val botProviderToken: String = ""

    @Value("\${bot.mail}")
    val botMail: String = ""
}