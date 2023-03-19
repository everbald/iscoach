package ru.iscoach.extrensions

import org.telegram.telegrambots.meta.api.objects.Message

fun Message.isForward() = this.forwardDate != null
fun Message.isNotForward() = !this.isForward()
fun Message.isUserJoin() = this.newChatMembers.isNotEmpty()
fun Message.isNotUserJoin() = !this.isUserJoin()
fun Message.isUserLeft() = this.leftChatMember != null
fun Message?.getBotCommand() =
    this?.entities?.firstOrNull { it.type == "bot_command" }?.text?.substringBefore("@")