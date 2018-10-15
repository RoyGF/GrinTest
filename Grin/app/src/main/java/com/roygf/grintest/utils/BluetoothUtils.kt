package com.roygf.grintest.utils

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Handler
import android.view.View
import android.widget.RelativeLayout
import com.roygf.grintest.R
import com.roygf.grintest.models.Device
import org.jetbrains.anko.toast

class BluetoothUtils (var context : Context) {

    lateinit var mBluetoothAdapter : BluetoothAdapter
    var mLoadingLayout : RelativeLayout? = null

    fun fetchBluetoothDevices(){
        val packageManager = context.packageManager
        if (packageManager.hasSystemFeature(PackageManager.FEATURE_BLUETOOTH)) {
            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
            mBluetoothAdapter.startDiscovery()
            mLoadingLayout?.visibility = View.VISIBLE
            startLayoutDelay()
        } else
            context.toast(R.string.text_unsupported_bluetooth)
    }

    fun getBluetoothDevices(intent: Intent?) : Device{
        val availableDevice = Device()
        val action = intent?.action
        if (action == BluetoothDevice.ACTION_FOUND){
            val strength = intent.getShortExtra(BluetoothDevice.EXTRA_RSSI, Short.MIN_VALUE)
            val name = intent.getStringExtra(BluetoothDevice.EXTRA_NAME)

            availableDevice.name = name
            availableDevice.strength = strength.toString()
        }
        return availableDevice
    }

    fun refresh(){
        if (mBluetoothAdapter.isDiscovering)
            mBluetoothAdapter.cancelDiscovery()

        mBluetoothAdapter.startDiscovery()
        mLoadingLayout?.visibility = View.VISIBLE
        startLayoutDelay()
    }

    fun setLoadingLayout(loadingLayout : RelativeLayout){
        this.mLoadingLayout = loadingLayout
    }

    fun startLayoutDelay(){
        Handler().postDelayed({
            mLoadingLayout?.visibility = View.GONE
            if (mBluetoothAdapter.isDiscovering)
                mBluetoothAdapter.cancelDiscovery()
        }, 20000)
    }
}