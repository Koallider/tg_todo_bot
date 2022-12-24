package me.koallider.tg_todo_bot

import com.github.kshashov.telegram.api.MessageType
import com.github.kshashov.telegram.api.TelegramMvcController
import com.github.kshashov.telegram.api.TelegramRequest
import com.github.kshashov.telegram.api.bind.annotation.BotController
import com.github.kshashov.telegram.api.bind.annotation.BotPathVariable
import com.github.kshashov.telegram.api.bind.annotation.BotRequest
import com.github.kshashov.telegram.api.bind.annotation.request.MessageRequest
import com.pengrad.telegrambot.model.Chat
import com.pengrad.telegrambot.model.User
import com.pengrad.telegrambot.request.BaseRequest
import com.pengrad.telegrambot.request.SendMessage
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component


@BotController
class MainController : TelegramMvcController {

    @Value("\${tg.apiKey}")
    private lateinit var token: String

    override fun getToken(): String {
        return token
    }

    @MessageRequest("{task}")
    fun pingPong(@BotPathVariable("task") task: String): String {
        return task.uppercase()
    }
}