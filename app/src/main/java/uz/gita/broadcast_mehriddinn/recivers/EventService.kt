package uz.gita.broadcast_mehriddinn.recivers

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.app.ServiceCompat
import uz.gita.broadcast_mehriddinn.R
import uz.gita.broadcast_mehriddinn.util.logger

class EventService:Service() {
    private val reciver = EventReciver()
    override fun onBind(intent: Intent?): IBinder? = null
    companion object{
        val CHANNEL_ID = "MY_CHANNEL"
        val STOP_SERVICE = "SERVICE"
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()

    }

    private fun registerIntents() {
        registerReceiver(reciver, IntentFilter().apply {
            addAction(Intent.ACTION_SCREEN_ON)
            addAction(Intent.ACTION_SCREEN_OFF)
            addAction(BluetoothAdapter.ACTION_STATE_CHANGED)
            addAction(Intent.ACTION_POWER_DISCONNECTED)
            addAction(Intent.ACTION_POWER_CONNECTED)
            addAction(Intent.ACTION_SHUTDOWN)
            addAction(Intent.ACTION_BATTERY_OKAY)
            addAction(Intent.ACTION_TIME_CHANGED)
            addAction(WifiManager.WIFI_STATE_CHANGED_ACTION)

            /*
            * action = Intent.ACTION_AIRPLANE_MODE_CHANGED
*  action = Intent.ACTION_SCREEN_OFF,
* \action = Intent.ACTION_SCREEN_ON,
* action = BluetoothAdapter.ACTION_STATE_CHANGED,
* action = Intent.ACTION_POWER_DISCONNECTED,
* action = Intent.ACTION_POWER_CONNECTED,
* action = Intent.ACTION_SHUTDOWN,
*  action = Intent.ACTION_TIME_CHANGED,
* action = ConnectivityManager.CONNECTIVITY_ACTION*/
        })
    }

    private fun myStartService() {
        val stopIntent = Intent(this, EventService::class.java)
        stopIntent.putExtra(STOP_SERVICE, true)
        val stopPendingIntent = PendingIntent
            .getService(
                this,
                1,
                stopIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.iconn)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentText("Events working !")
            .addAction(R.drawable.bt,"Cancel",stopPendingIntent)
            .setAutoCancel(false)
            .setOngoing(true)
            .build()

        startForeground(1, notification)
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =
                NotificationChannel(CHANNEL_ID, "EVENT", NotificationManager.IMPORTANCE_DEFAULT)
            channel.setSound(null, null)
            val service = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            service.createNotificationChannel(channel)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(reciver)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val logic = intent?.extras?.getBoolean(STOP_SERVICE)
        logger(logic.toString()+   "logic")

        if (logic == true) {
            logger("service")
            unregisterReceiver(reciver)
            ServiceCompat.stopForeground(this, ServiceCompat.STOP_FOREGROUND_REMOVE)
        }else{
            myStartService()
            registerIntents()
        }

        return START_NOT_STICKY
    }


}