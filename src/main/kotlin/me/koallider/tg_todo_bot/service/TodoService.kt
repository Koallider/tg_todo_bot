package me.koallider.tg_todo_bot.service

import com.pengrad.telegrambot.model.Chat
import com.pengrad.telegrambot.model.Message
import me.koallider.tg_todo_bot.dao.ChatRepository
import me.koallider.tg_todo_bot.model.TodoChat
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

interface TodoService{
    fun handleMessage(chat: Chat, message: Message) : String
}

@Service
class TodoServiceImpl : TodoService {
    @Autowired
    lateinit var chatRepository: ChatRepository

    fun getTodoList(chatId: Long): List<Int> {
        return chatRepository.findByChatId(chatId)?.todoList ?: ArrayList()
    }

    override fun handleMessage(chat: Chat, message: Message) : String {
        val todoChat = chatRepository.findByChatId(chat.id()) ?: TodoChat(chat.id(), ArrayList())
        if(message.replyToMessage() == null){
            todoChat.todoList.add(message.messageId())
            chatRepository.save(todoChat)
            return "Task added to list"
        }else if(message.text().lowercase() == "done"){
            return if(todoChat.todoList.remove(message.replyToMessage().messageId())){
                chatRepository.save(todoChat)
                "Task \"${message.replyToMessage().text()}\" is finished"
            }else{
                "This task is finished or it's not a task"
            }
        }
        return "NOT SUPPORTED"
    }
}