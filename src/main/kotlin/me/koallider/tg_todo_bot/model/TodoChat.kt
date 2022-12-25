package me.koallider.tg_todo_bot.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "lists")
data class TodoChat(@Id val chatId: Long, var todoList: MutableList<Int>)