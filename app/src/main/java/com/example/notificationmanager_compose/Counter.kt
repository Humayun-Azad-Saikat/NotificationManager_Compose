package com.example.notificationmanager_compose

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

object Counter {

    private var counterValue:Int = 0
    private var isRunning:Boolean = true

    fun start() = flow<Int> {
        isRunning = true
        while (isRunning){
            emit(counterValue)
            delay(1000)
            counterValue++
        }
    }

    fun stop(){
        isRunning = false
    }
}