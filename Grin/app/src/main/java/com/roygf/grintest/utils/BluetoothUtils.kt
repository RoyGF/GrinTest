package com.roygf.grintest.utils

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.widget.Toast
import com.roygf.grintest.models.Device
import org.jetbrains.anko.toast
import kotlin.properties.Delegates

class BluetoothUtils (context : Context) {

    lateinit var mBluetoothAdapter : BluetoothAdapter
    var mDevices : ArrayList<Device> = arrayListOf()
    var mContext = context

    fun fetchBluetoothDevices(){
        val packageManager = mContext.packageManager
        if (packageManager.hasSystemFeature(PackageManager.FEATURE_BLUETOOTH)) {
            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
            mBluetoothAdapter.startDiscovery()
        } else
            mContext.toast("Dispositivo no tiene soporte bluetooth")

    }

    fun getBluetoothDevices(intent: Intent?) : Device{
        val availableDevice : Device = Device(null, null, "Thinkpad", "-20db", true)
        val action = intent?.action
        if (action == BluetoothDevice.ACTION_FOUND){
            val rssi = intent.getShortExtra(BluetoothDevice.EXTRA_RSSI, Short.MIN_VALUE)
            val name = intent.getStringExtra(BluetoothDevice.EXTRA_NAME)
            availableDevice.name = name
            availableDevice.strenght = rssi.toString()
            availableDevice.isAvailable = true
        }

        return availableDevice
    }


}