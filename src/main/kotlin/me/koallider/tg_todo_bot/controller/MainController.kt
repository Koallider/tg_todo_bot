package me.koallider.tg_todo_bot.controller

import com.github.kshashov.telegram.api.TelegramMvcController
import com.github.kshashov.telegram.api.bind.annotation.BotController
import com.github.kshashov.telegram.api.bind.annotation.request.MessageRequest
import com.pengrad.telegrambot.TelegramBot
import com.pengrad.telegrambot.model.Chat
import com.pengrad.telegrambot.model.Message
import com.pengrad.telegrambot.request.SendMessage
import me.koallider.tg_todo_bot.service.TodoService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value


@BotController
class MainController : TelegramMvcController {

    @Autowired
    lateinit var todoService: TodoService

    @Value("\${tg.apiKey}")
    private lateinit var token: String

    override fun getToken(): String {
        return token
    }

    @MessageRequest
    fun handleMessage(chat: Chat, message: Message): String {
        return todoService.handleMessage(chat, message)
    }

    @MessageRequest("/list")
    fun handleList(chat: Chat, bot: TelegramBot): String? {
        val taskList = todoService.getTaskList(chat)
        return if (taskList.isEmpty()) {
            "TODO list is empty"
        } else {
            taskList.forEachIndexed { index, messageId ->
                val message = SendMessage(chat.id(), "${index + 1}. id: $messageId")
                message.replyToMessageId(messageId)
                bot.execute(message)
            }
            return null
        }
    }
}