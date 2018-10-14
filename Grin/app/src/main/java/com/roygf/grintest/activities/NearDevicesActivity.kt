package com.roygf.grintest.activities

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.roygf.grintest.R
import com.roygf.grintest.adapters.DevicesAdapter
import com.roygf.grintest.models.Device
import com.roygf.grintest.repositories.WebRepository
import com.roygf.grintest.repositories.WebRepository.DevicesListener
import com.roygf.grintest.utils.BluetoothUtils
import kotlinx.android.synthetic.main.activity_main.*

class NearDevicesActivity :
        AppCompatActivity(),
        View.OnClickListener,
        DevicesAdapter.DeviceListener,
        WebRepository.SaveDeviceListener {


    lateinit var mDevicesAdapter : DevicesAdapter
    lateinit var mBluetoothUtils : BluetoothUtils


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
        mDevicesAdapter.listener = this
        mDevicesRecycler.layoutManager = LinearLayoutManager(applicationContext)
        mDevicesRecycler.adapter = mDevicesAdapter

        mBluetoothUtils = BluetoothUtils(applicationContext)
        mBluetoothUtils.fetchBluetoothDevices()
        mSavedDevicesButton.setOnClickListener(this)

    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(mBroadCastReceiver)
    }

    override fun onClick(v: View?) {
        val intent = Intent(this, SavedDevicesActivity::class.java)
        startActivity(intent)
    }

    override fun onDeviceSelected(device: Device) {
        val repository = WebRepository(applicationContext)
        repository.saveListener = this
        repository.saveDevice(device)
    }

    override fun onSaveSuccess() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Dispositivo guardado")
        builder.setPositiveButton("Ok", null)
        val dialog = builder.create()
        dialog.show()
    }

    override fun onSaveError(error: String) {
        val builder = AlertDialog.Builder(applicationContext)
        builder.setMessage("error")
        builder.setPositiveButton("Ok", null)
        val dialog = builder.create()
        dialog.show()
    }
}
