package uz.gita.broadcast_mehriddinn.util

import android.content.Intent
import androidx.annotation.StringRes
import uz.gita.broadcast_mehriddinn.R

data class Event(
    val id: Int,
//    @DrawableRes
//    val iconRes: Int,
    @StringRes
    val nameRes: Int,
    val intent: String,
    var enabled: Boolean = false
)
/*
val allEvents = listOf<Event>(
    Event(
        id = 1,
        nameRes = R.string.screen_on,
        intent = Intent.ACTION_SCREEN_ON
    ),
    Event(
        id = 2,
        nameRes = R.string.screen_off,
        intent = Intent.ACTION_SCREEN_OFF
    ),
    Event(
        id = 3,
        nameRes = R.string.power_connected,
        intent = Intent.ACTION_POWER_CONNECTED
    ),
    Event(
        id = 4,
        nameRes = R.string.power_disconnected,
        intent = Intent.ACTION_POWER_DISCONNECTED
    ),
    Event(
        id = 5,
        nameRes = R.string.device_storage_low,
        intent = Intent.ACTION_DEVICE_STORAGE_LOW
    ),
    Event(
        id = 6,
        nameRes = R.string.airplane_mode_changed,
        intent = Intent.ACTION_AIRPLANE_MODE_CHANGED
    ),
    Event(
        id = 7,
        nameRes = R.string.battery_ok,
        intent = Intent.ACTION_BATTERY_OKAY
    ),
    Event(
        id = 8,
        nameRes = R.string.battery_low,
        intent = Intent.ACTION_BATTERY_LOW
    ),
    Event(
        id = 9,
        nameRes = R.string.action_shutdown,
        intent = Intent.ACTION_SHUTDOWN
    ),
    Event(
        id = 10,
        nameRes = R.string.action_timezone_changed,
        intent = Intent.ACTION_TIMEZONE_CHANGED
    ),
    Event(
        id = 11,
        nameRes = R.string.action_time_changed,
        intent = Intent.ACTION_TIME_CHANGED
    ),
    Event(
        id = 12,
        nameRes = R.string.action_date_changed,
        intent = Intent.ACTION_DATE_CHANGED
    )
)*/
/*

fun retrieveEnabledEvents() = allEvents.filter { it.enabled }

fun retrieveEnabledEventsAsArrayList(): ArrayList<String> {
    val enabledEvents = allEvents.filter { it.enabled }

    if(enabledEvents.isNullOrEmpty()) return arrayListOf()

    val result = arrayListOf<String>()
    enabledEvents.map { it.intent }.forEach { event ->
        result.add(event)
    }
    return result
}*/
