package com.example.notificationmanager_compose

//not important class

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class CounterReceiver:BroadcastReceiver() {

    private lateinit var counterNotification: CounterNotification

    private var counterJob:Job? = null

    override fun onReceive(context: Context?, intent: Intent?) {

        counterNotification = CounterNotification(context!!)

        when(intent?.action){
            CounterNotification.CounterActions.START.toString() -> start()
            CounterNotification.CounterActions.STOP.toString() -> stop()
        }
    }

    fun start(){
        stop()
        counterJob = CoroutineScope(SupervisorJob() + Dispatchers.IO).launch {
            Counter.start().collect{ counterValue ->
                counterNotification.counterNotification(counterValue) //just calling a function from CounteNotification class with counterNotification class's object
            }
        }
    }

    fun stop(){
        counterJob?.cancel()
        Counter.stop()
    }

}

