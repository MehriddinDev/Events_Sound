package uz.gita.broadcast_mehriddinn

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore.Audio.Media
import android.widget.PopupMenu
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import uz.gita.broadcast_mehriddinn.adapter.MyAdapter
import uz.gita.broadcast_mehriddinn.databinding.ActivityMainBinding
import uz.gita.broadcast_mehriddinn.recivers.*
import uz.gita.broadcast_mehriddinn.storage.MyPref
import uz.gita.broadcast_mehriddinn.util.logger
import uz.gita.broadcast_mehriddinn.viewModel.MainViewModel

class MainActivity : AppCompatActivity() {

    private val pref = MyPref.getInstance()

    private val myPermissions =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted){
                createService()
            }
        }

    private val myAdapter = MyAdapter()

    private val viewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    private lateinit var binding: ActivityMainBinding

    private fun createService(){
        val intent = Intent(this@MainActivity, EventService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(intent)
        } else startService(intent)

    }

    fun showPopupMenu() {
        val popUpMenu = PopupMenu(this, binding.btnMore)
        popUpMenu.menuInflater.inflate(R.menu.popup_menu, popUpMenu.menu)
        popUpMenu.setOnMenuItemClickListener {
            when (it.itemId) {

                R.menu.popup_menu->{

                }
                else->{AlertDialog.Builder(this)
                    .setTitle("Events app")
                    .setMessage("Created by Mehriddin Sobirov.\nGita academy of programmmers")
                    .show()}
            }
            true
        }
        popUpMenu.show()
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnMore.setOnClickListener {
            showPopupMenu()
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            if (ContextCompat.checkSelfPermission(this@MainActivity, android.Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED){
                createService()
            }else{
                myPermissions.launch(android.Manifest.permission.POST_NOTIFICATIONS)
            }
        }else{
            createService()
        }

        val intent = Intent(this, EventService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            this.startForegroundService(intent)
        } else {
            this.startService(intent)
        }



        /*registerReceiver(reciver, IntentFilter( Intent.ACTION_POWER_CONNECTED))
        registerReceiver(reciver, IntentFilter( Intent.ACTION_POWER_DISCONNECTED))
        registerReceiver(reciver, IntentFilter( Intent.ACTION_TIME_CHANGED))
        registerReceiver(reciver, IntentFilter( Intent.ACTION_SHUTDOWN))
        registerReceiver(reciver, IntentFilter(  ConnectivityManager.CONNECTIVITY_ACTION))*/

        binding.rvList.adapter = myAdapter
        binding.rvList.layoutManager = LinearLayoutManager(this)

        logger(viewModel.getEvents().size.toString()+"soni")
        myAdapter.submitList(viewModel.getEvents())
        myAdapter.setCheckListener{ isChecked, eventdata ->
            logger(isChecked.toString())
            if (isChecked) {
                viewModel.enableEvent(eventdata.id)
                viewModel.changeDataState(isChecked, eventdata)
                myAdapter.submitList(viewModel.getEvents())
                pref.saveIsChacked(eventdata.action,isChecked)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
                    if (ContextCompat.checkSelfPermission(this@MainActivity, android.Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED){
                        createService()
                    }else{
                        myPermissions.launch(android.Manifest.permission.POST_NOTIFICATIONS)
                    }
                }else{
                    createService()
                }
            } else {
                viewModel.disableEvent(eventdata.id)
                viewModel.changeDataState(isChecked, eventdata)
                myAdapter.submitList(viewModel.getEvents())
                pref.saveIsChacked(eventdata.action,isChecked)
            }
            logger(eventdata.id.toString())
          /*  when (eventdata.id) {

                1 -> {//air
                    if(isChecked){
                        registerReceiver(airReciver, IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED))
                    }else{
                        unregisterReceiver(airReciver)
                    }

                }
                2 -> {//sc off
                    if(isChecked){
                        registerReceiver(screenOfReciver, IntentFilter(Intent.ACTION_SCREEN_OFF))
                    }else{
                        unregisterReceiver(screenOfReciver)
                    }
                }
                3 -> {// sc onn
                    if(isChecked){
                        registerReceiver(screenOnReciver, IntentFilter(Intent.ACTION_SCREEN_ON))
                    }else{
                        unregisterReceiver(screenOnReciver)
                    }
                }
                4 -> {// bt
                    if(isChecked){
                        logger("bt register")
                       // registerReceiver(BTreciver, IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED))
                    }else{
                        logger("bt unRegister")
                       // unregisterReceiver(BTreciver)
                    }
                }
                5-> {
                    if(isChecked){
                        registerReceiver(reciver,IntentFilter(eventdata.action))
                    }else{
                        unregisterReceiver(reciver)
                    }
                }
            }*/
        }
    }

}