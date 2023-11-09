package uz.gita.broadcast_mehriddinn.util

import android.content.BroadcastReceiver
import android.content.Context
import android.util.Log
import android.widget.Toast

fun BroadcastReceiver.showToast(context: Context, s:String){
    Toast.makeText(context, s, Toast.LENGTH_SHORT).show()
}

fun logger(s:String,tag:String="TTT"){
    Log.d(tag, s)
}