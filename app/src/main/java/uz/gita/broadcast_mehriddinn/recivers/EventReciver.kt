package uz.gita.broadcast_mehriddinn.recivers

import android.bluetooth.BluetoothAdapter
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import android.util.Log
import uz.gita.broadcast_mehriddinn.R
import uz.gita.broadcast_mehriddinn.storage.MyPref
import uz.gita.broadcast_mehriddinn.util.logger

class EventReciver : BroadcastReceiver() {
    private lateinit var charging_on: MediaPlayer
    private lateinit var charging_off: MediaPlayer
    private lateinit var screen_on: MediaPlayer
    private lateinit var screen_off: MediaPlayer
    private lateinit var btOff: MediaPlayer
    private lateinit var shut_down: MediaPlayer
    private lateinit var btOn: MediaPlayer
    private lateinit var wifi_off: MediaPlayer
    private lateinit var wifi_on: MediaPlayer
    private lateinit var time_changed: MediaPlayer
    private lateinit var sound : MediaPlayer
    private val pref = MyPref.getInstance()

    override fun onReceive(context: Context?, intent: Intent?) {

        when (intent?.action) {
            BluetoothAdapter.ACTION_STATE_CHANGED -> {
                val state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR)
                when (state) {
                    BluetoothAdapter.STATE_OFF -> {
                        // Bluetooth o'chirilgan
                        logger("blueth ochdi")
                        if(pref.getIsChackd(BluetoothAdapter.ACTION_STATE_CHANGED)){
                            btOff = MediaPlayer.create(context, R.raw.blueth_off)
                            btOff.start()
                        }

                    }
                    BluetoothAdapter.STATE_ON -> {
                        // Bluetooth yoqilgan
                        logger("blueth yondi")
                        if(pref.getIsChackd(BluetoothAdapter.ACTION_STATE_CHANGED)){
                            btOn = MediaPlayer.create(context, R.raw.blueth_on)
                            btOn.start()
                        }
                    }
                }
            }
            Intent.ACTION_POWER_DISCONNECTED -> {
                logger("power of")
                if (pref.getIsChackd(Intent.ACTION_POWER_DISCONNECTED)){
                    charging_off = MediaPlayer.create(context,R.raw.charging_off)
                    charging_off.start()
                }

            }

            Intent.ACTION_POWER_CONNECTED -> {
                logger("power on")
                if (pref.getIsChackd(Intent.ACTION_POWER_CONNECTED)) {
                    charging_on = MediaPlayer.create(context,R.raw.charging_on)
                    charging_on.start()
                }
            }

            Intent.ACTION_SHUTDOWN -> {
                logger("wut down")
                if (pref.getIsChackd(Intent.ACTION_SHUTDOWN)){
                    shut_down = MediaPlayer.create(context,R.drawable.shutdown)
                    shut_down.start()
                }
            }

            Intent.ACTION_TIME_CHANGED -> {
                logger("time change")
                if (pref.getIsChackd(Intent.ACTION_TIME_CHANGED)){
                    time_changed = MediaPlayer.create(context,R.raw.time_changed)
                    time_changed.start()
                }
            }

            WifiManager.WIFI_STATE_CHANGED_ACTION-> {

                val state = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, WifiManager.WIFI_STATE_UNKNOWN)
                when (state) {
                    WifiManager.WIFI_STATE_DISABLED -> {
                        // Wi-Fi o'chirilgan
                        logger("Wi-Fi o'chirilgan","HHH")
                        if (pref.getIsChackd(WifiManager.WIFI_STATE_CHANGED_ACTION)){
                            wifi_off = MediaPlayer.create(context,R.raw.wi_fi_off)
                            wifi_off.start()
                        }
                    }
                    WifiManager.WIFI_STATE_ENABLED -> {
                        // Wi-Fi yoqilgan
                        logger("Wi-Fi yoqilgan","HHH")
                        if (pref.getIsChackd(WifiManager.WIFI_STATE_CHANGED_ACTION)){
                            wifi_on = MediaPlayer.create(context,R.raw.wi_fi_on)
                            wifi_on.start()
                        }
                    }
                }
            }

            Intent.ACTION_SCREEN_ON -> {
                if (pref.getIsChackd(Intent.ACTION_SCREEN_ON)){
                    screen_on = MediaPlayer.create(context,R.raw.screen_on)
                    screen_on.start()
                }
                logger("screen on")
            }

            Intent.ACTION_SCREEN_OFF -> {
                if (pref.getIsChackd(Intent.ACTION_SCREEN_OFF)){
                    screen_off = MediaPlayer.create(context,R.raw.screen_off)
                    screen_off.start()
                }
                logger("screen of")
            }

            Intent.ACTION_AIRPLANE_MODE_CHANGED -> {
                if (pref.getIsChackd(Intent.ACTION_AIRPLANE_MODE_CHANGED)){
                    sound = MediaPlayer.create(context,R.raw.sound)
                    sound.start()
                }

            }
        }
    }
}