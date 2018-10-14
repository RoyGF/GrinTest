package com.roygf.grintest.activities

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.roygf.grintest.R
import com.roygf.grintest.adapters.DevicesAdapter
import com.roygf.grintest.utils.BluetoothUtils
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var mBluetoothUtils : BluetoothUtils
    lateinit var mDevicesAdapter : DevicesAdapter


    val mBroadCastReceiver = object : BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            mDevicesAdapter.addDevice(mBluetoothUtils.getBluetoothDevices(intent))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        registerReceiver(mBroadCastReceiver, IntentFilter(BluetoothDevice.ACTION_FOUND))
        mDevicesAdapter = DevicesAdapter(applicationContext)
        mDevicesRecycler.layoutManager = LinearLayoutManager(applicationContext)
        mDevicesRecycler.adapter = mDevicesAdapter

        mBluetoothUtils = BluetoothUtils(applicationContext)
        mBluetoothUtils.fetchBluetoothDevices()

    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(mBroadCastReceiver)
    }
}
