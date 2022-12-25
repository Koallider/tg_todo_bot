package me.koallider.tg_todo_bot.dao

import me.koallider.tg_todo_bot.model.TodoChat
import org.springframework.data.mongodb.repository.MongoRepository

interface ChatRepository : MongoRepository<TodoChat, String>{
    fun findByChatId(chatId: Long): TodoChat?
}