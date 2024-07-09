package com.example.notificationmanager_compose

//important class

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.core.app.NotificationCompat
import androidx.core.app.PendingIntentCompat

class CounterNotification(private val context: Context) {

    val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    fun counterNotification(counter:Int){ //important fun. but only counters are not important

        var flag = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            PendingIntent.FLAG_IMMUTABLE
        }else 0

        //creating intent for: launching app activity by clicking on the notification
        val intent = Intent(context,MainActivity::class.java)
        val notificatonClickPendingIntent = PendingIntent.getActivity(
            context,1,intent,flag
        )

        val notification = NotificationCompat
            .Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_android_black_24dp)
            .setContentTitle("Counter Title")
            .setContentText(counter.toString())
            .setStyle(NotificationCompat.BigTextStyle())
            .setOngoing(false) //if i set this true the notification will not go
            .setContentIntent(notificatonClickPendingIntent)
            .addAction(
                R.drawable.ic_launcher_foreground,
                "Start",
                getpendingIntentForAction(CounterActions.START,flag,2)
            )
            .addAction(
                R.drawable.ic_launcher_foreground,
                "Stop",
                getpendingIntentForAction(CounterActions.STOP,flag,3)
            )
            .build()

        notificationManager.notify(1,notification)

    }

    fun getpendingIntentForAction(
        action:CounterActions,
        flag:Int,
        requestCode:Int
    ):PendingIntent{
        val intent = Intent(context,CounterReceiver::class.java)

        when(action){
            CounterActions.START -> intent.action = CounterActions.START.toString()
            CounterActions.STOP -> intent.action = CounterActions.STOP.toString()
        }

        return PendingIntent.getBroadcast(context,requestCode,intent,flag)
    }


    companion object{ //important
        const val CHANNEL_ID = "counter_notification"
    }

    //notificaton counter actons emun class
    enum class CounterActions{ //not important cause counter
        START,STOP
    }

}