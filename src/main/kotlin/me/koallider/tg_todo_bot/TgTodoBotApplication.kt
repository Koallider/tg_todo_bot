package me.koallider.tg_todo_bot

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TgTodoBotApplication

fun main(args: Array<String>) {
    println("MAIN")
    //runApplication<TgTodoBotApplication>(*args)
    SpringApplication.run(TgTodoBotApplication::class.java)
}
