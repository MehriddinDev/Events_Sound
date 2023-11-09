package uz.gita.broadcast_mehriddinn.storage

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import uz.gita.broadcast_mehriddinn.App.App
import uz.gita.broadcast_mehriddinn.EventData
import uz.gita.broadcast_mehriddinn.util.logger

class MyPref {
    val pref: SharedPreferences = App.context.getSharedPreferences("BROADCAST", Context.MODE_PRIVATE)
    val edit: SharedPreferences.Editor = pref.edit()
    companion object{
        private var myPref : MyPref? = null
        fun getInstance():MyPref{
            if(myPref == null){
                myPref = MyPref()
            }
            return myPref as MyPref
        }
    }

    fun saveAllDatas(eventData: String){
        logger(eventData)
        edit.putString("data",eventData).apply()
    }

    fun getAllData():String{
        val k = pref.getString("data","")!!
        Log.d("LLL", "getAllData = $k")
        return k
    }

    fun getIsFirst():Boolean{
        return pref.getBoolean("first",true)
    }

    fun saveFirst(bool:Boolean){
        edit.putBoolean("first",bool).apply()
    }

    fun saveIsChacked(number:String,b:Boolean){
        edit.putBoolean(number,b).apply()
    }

    fun getIsChackd(number: String):Boolean {
        return pref.getBoolean(number, false)
    }


}