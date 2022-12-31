package me.koallider.tg_todo_bot.service

import com.github.kshashov.telegram.api.bind.annotation.BotPathVariable
import com.github.kshashov.telegram.api.bind.annotation.request.MessageRequest
import com.pengrad.telegrambot.model.Chat
import com.pengrad.telegrambot.model.Message
import me.koallider.tg_todo_bot.dao.ChatRepository
import me.koallider.tg_todo_bot.model.TodoChat
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


interface TodoService {
    fun handleTaskMessage(chat: Chat, message: Message): String
    fun handleDoneMessage(chat: Chat, message: Message): String
    fun getTaskList(chat: Chat): List<Int>
}

@Service
class TodoServiceImpl : TodoService {
    @Autowired
    lateinit var chatRepository: ChatRepository

    override fun handleTaskMessage(chat: Chat, message: Message): String {
        val todoChat = chatRepository.findByChatId(chat.id()) ?: TodoChat(chat.id(), ArrayList())
        if (message.replyToMessage() == null) {
            todoChat.todoList.add(message.messageId())
            chatRepository.save(todoChat)
            return "Task added to list"
        }
        return "NOT SUPPORTED"
    }

    override fun handleDoneMessage(chat: Chat, message: Message): String {
        if(message.replyToMessage() == null){
            return "To finish the task send \"done\" in reply to the original task message"
        }
        val replyRegex = """\d*. id: (\d*)""".toRegex()
        val todoChat = chatRepository.findByChatId(chat.id()) ?: TodoChat(chat.id(), ArrayList())
        val messageId = message.replyToMessage().messageId()

        return if (todoChat.todoList.remove(messageId)) {
            chatRepository.save(todoChat)
            "Task \"${message.replyToMessage().text()}\" is finished"
        } else if (message.replyToMessage().text().matches(replyRegex)) {
            val regexMessageId =
                replyRegex.find(message.replyToMessage().text())?.groups?.get(1)?.value?.toInt() ?: 0
            if (todoChat.todoList.remove(regexMessageId)) {
                chatRepository.save(todoChat)
                "Task with id $regexMessageId is finished"
            } else {
                "This task is finished or it's not a task"
            }
        } else {
            "This task is finished or it's not a task"
        }
    }

    override fun getTaskList(chat: Chat): List<Int> {
        return chatRepository.findByChatId(chat.id())?.todoList ?: ArrayList()
    }
}