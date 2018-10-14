package com.roygf.grintest.utils

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Context
import android.content.Intent
import com.roygf.grintest.models.Device
import kotlin.properties.Delegates

class BluetoothUtils (context : Context){

    var mContext : Context by Delegates.notNull()
    var mBluetoothAdapter : BluetoothAdapter
    var mDevices : ArrayList<Device>

    init {
        this.mContext = context
        this.mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        this.mDevices = arrayListOf()
    }

    fun fetchBluetoothDevices(){
        mBluetoothAdapter.startDiscovery()

    }

    fun getBluetoothDevices(intent: Intent?) : Device{
        val availableDevice : Device = Device("", "", false)
        val action = intent?.action
        if (action == BluetoothDevice.ACTION_FOUND){
            val rssi = intent.getShortExtra(BluetoothDevice.EXTRA_RSSI, Short.MIN_VALUE)
            val name = intent.getStringExtra(BluetoothDevice.EXTRA_NAME)
            availableDevice.name = name
            availableDevice.db = rssi.toString()
            availableDevice.isAvailable = true
        }

        return availableDevice
    }


}