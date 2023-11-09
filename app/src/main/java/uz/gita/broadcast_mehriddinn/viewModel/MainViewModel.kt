package uz.gita.broadcast_mehriddinn.viewModel

import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import uz.gita.broadcast_mehriddinn.EventData
import uz.gita.broadcast_mehriddinn.R
import uz.gita.broadcast_mehriddinn.storage.MyPref
import uz.gita.broadcast_mehriddinn.util.logger

class MainViewModel : ViewModel() {

    private val pref = MyPref.getInstance()
    val list = ArrayList<EventData>()

    fun saveIsChaked(number: String, bool: Boolean) {
        pref.saveIsChacked(number, bool)
    }
    fun getIsCheked(number: String): Boolean {
        return pref.getIsChackd(number)
    }

    fun changeDataState(isChecked: Boolean, data: EventData) {
        val gson = Gson()
        var newJson = ""
        var oldJson = ""
        if (isChecked) {
            oldJson = gson.toJson(EventData(data.id, data.name, data.action, data.icon, false))
            newJson = gson.toJson(EventData(data.id, data.name, data.action, data.icon, true))
        } else {
            oldJson = gson.toJson(EventData(data.id, data.name, data.action, data.icon, true))
            newJson = gson.toJson(EventData(data.id, data.name, data.action, data.icon, false))
        }
        val changedData = pref.getAllData().replace(oldJson, newJson)
        pref.saveAllDatas(changedData)

    }

    fun getEvents(): List<EventData> {
        val list = arrayListOf<EventData>()
        val gson = Gson()
        val alldata = pref.getAllData()
        Log.d("LLL", "json: $alldata")

        val datas = alldata.split("/")
        datas.forEach { item ->

            if (item.isNotBlank()) {
                val eventData: EventData = gson.fromJson(item, EventData::class.java)
                list.add(eventData)
            }
        }

        return list
    }

    fun enableEvent(id: Int) {
        list.filter {
            it.id == id
        }.map {
            it.state = true
        }
    }

    fun disableEvent(id: Int) {
        eventList.filter {
            it.id == id
        }.map {
            it.state = false
        }
    }

    private val eventList = listOf<EventData>(
/*
*action = Intent.ACTION_AIRPLANE_MODE_CHANGED
*  action = Intent.ACTION_SCREEN_OFF,
* \action = Intent.ACTION_SCREEN_ON,
* action = BluetoothAdapter.ACTION_STATE_CHANGED,
* action = Intent.ACTION_POWER_DISCONNECTED,
* action = Intent.ACTION_POWER_CONNECTED,
* action = Intent.ACTION_SHUTDOWN,
*  action = Intent.ACTION_TIME_CHANGED,
* action = ConnectivityManager.CONNECTIVITY_ACTION
*
* */
        EventData(
            id = 1,
            name = "Airplane Mode",
            action = Intent.ACTION_AIRPLANE_MODE_CHANGED,
            icon = R.drawable.airplane,
            false
        ),
        EventData(
            id = 2,
            name = "Screen Off",
            action = Intent.ACTION_SCREEN_OFF,
            icon = R.drawable.screeen,
            false
        ),
        EventData(
            id = 3,
            name = "Screen On",
            action = Intent.ACTION_SCREEN_ON,
            icon = R.drawable.screeen,
            false
        ),
        EventData(
            id = 4,
            name = "Bluetooth",
            action = BluetoothAdapter.ACTION_STATE_CHANGED,
            icon = R.drawable.bt,
            state = false
        ),
        EventData(
            id = 5,
            name = "Disconnect charging",
            action = Intent.ACTION_POWER_DISCONNECTED,
            icon = R.drawable.uncharger,
            state = false
        ),
        EventData(
            id = 6,
            name = "Connect charging",
            action = Intent.ACTION_POWER_CONNECTED,
            icon = R.drawable.charging,
            state = false
        ),
        EventData(
            id = 7,
            name = "Shutdown",
            action = Intent.ACTION_SHUTDOWN,
            icon = R.drawable.shutdown,
            state = false
        ),
        EventData(
            id = 8,
            name = "Time Changed",
            action = Intent.ACTION_TIME_CHANGED,
            icon = R.drawable.time,
            state = false
        ),
        EventData(
            id = 9,
            name = "Connectivity Wife",
            action = WifiManager.WIFI_STATE_CHANGED_ACTION,
            icon = R.drawable.wi_fi,
            state = false
        )
    )
    /*


        */

    init {
        logger("init","HHH")
        if (pref.getIsFirst()) {
            var s = ""
            eventList.forEach {
                val gson = Gson()
                val json = gson.toJson(it)
                s = "$s$json/"

                logger("${it.action}  ${it.state}","HHH")
            }

            pref.saveAllDatas(s)
            pref.saveFirst(false)
        }
    }
}