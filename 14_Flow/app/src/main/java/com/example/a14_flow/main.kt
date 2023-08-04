package com.example.a14_flow

import android.app.job.JobParameters
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.cancel


data class Gamer(val name: String, val card: List<List<Int>>, var numberFaund: Int)

val gamer1 = Gamer("Игрок 1", generateRandomCard(), 0)
val gamer2 = Gamer("Игрок 2", generateRandomCard(), 0)

val gamers = listOf<Gamer>(gamer1, gamer2)

var finish = false


suspend fun lotoGame() {
    val numbersFlow = MutableSharedFlow<Int>()

    // Создаем мешок с бочонками лото от 1 до 90 и перемешиваем их
    val lotoKegs = mutableListOf<Int>()
    for (i in 1..90) {
        lotoKegs.add(i)
    }
    lotoKegs.shuffle()
//    println(lotoKegs)

    runBlocking {
        //Достаем случайный бочонок из мешка и эмитим его в поток
        launch {
            while(!finish) {
                val randomLotoKeg = lotoKegs.random()
                delay(500)
                numbersFlow.emit(randomLotoKeg)
                lotoKegs.remove(randomLotoKeg)
                println("Выпал бочонок с номером $randomLotoKeg")
//                println("Остались бочонки с номерами $lotoKegs")
            }
            cancel()
            println("Finish $finish")
        }

        launch {
            numbersFlow.collect() { number ->
                gamers.forEach { gamer ->
                    if (gamer.card.flatten().contains(number)) {
                        gamer.numberFaund++
                        if (gamer.numberFaund == 15) {
                            finish = true
                            cancel()
//                            return@forEach
                            println("${gamer.name} выиграл")
                            println("Игра закончилась - $finish")
                        }
                        println("${gamer.name} закрыл поле с номером $number")
                        println("${gamer.name} закрыл ${gamer.numberFaund} полей")
                    }
                }
            }
        }
    }
}


fun main() {
    runBlocking {
        launch {
            delay(500)
            lotoGame()
        }
        launch {
            println()
            gamers.forEach { gamer ->
                println(gamer)
            }
        }
    }
}

fun generateRandomCard(): List<List<Int>> {
    val card = mutableListOf<List<Int>>()
    val numbers = mutableListOf<Int>()
    for (i in 1..90) {
        numbers.add(i)
    }
    numbers.shuffle()

    repeat(3) {
        val row = numbers.subList(it * 9, (it + 1) * 9).sorted().take(5)
        card.add(row)
    }
//    println(card)
    return card
}