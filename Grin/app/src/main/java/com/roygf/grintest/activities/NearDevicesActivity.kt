package com.roygf.grintest.activities

import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.RelativeLayout
import com.roygf.grintest.R
import com.roygf.grintest.adapters.DevicesAdapter
import com.roygf.grintest.models.Device
import com.roygf.grintest.repositories.WebRepository
import com.roygf.grintest.utils.BluetoothUtils
import kotlinx.android.synthetic.main.activity_main.*

class NearDevicesActivity :
        BaseActivity(),
        DevicesAdapter.DeviceListener,
        WebRepository.SaveDeviceListener {


    lateinit var mDevicesAdapter : DevicesAdapter
    lateinit var mBluetoothUtils : BluetoothUtils


    private val mBroadCastReceiver = object : BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            mDevicesAdapter.addDevice(mBluetoothUtils.getBluetoothDevices(intent))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        registerReceiver(mBroadCastReceiver, IntentFilter(BluetoothDevice.ACTION_FOUND))
        initUI()
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(mBroadCastReceiver)
    }


    /**Methods**/
    private fun initUI(){
        mDevicesAdapter = DevicesAdapter(applicationContext)
        recycler_devices.layoutManager = LinearLayoutManager(applicationContext)
        recycler_devices.adapter = mDevicesAdapter

        mBluetoothUtils = BluetoothUtils(applicationContext)
        mBluetoothUtils.fetchBluetoothDevices()
        mBluetoothUtils.setLoadingLayout(layout_loading as RelativeLayout)

        mDevicesAdapter.listener = this
        button_save.setOnClickListener{savedDevicesActivity()}
        button_search.setOnClickListener{refreshDevices()}

    }

    private fun refreshDevices(){
        mDevicesAdapter.emptyList()
        mBluetoothUtils.refresh()
    }

    private fun savedDevicesActivity(){
        val intent = Intent(this, SavedDevicesActivity::class.java)
        startActivity(intent)
    }

    /**Callbacks**/
    override fun onDeviceSelected(device: Device) {
        val repository = WebRepository(applicationContext)
        repository.saveListener = this
        repository.saveDevice(device)
    }

    override fun onSaveSuccess() {
        showMessageDialog(R.string.text_saved_device)
    }

    override fun onSaveError(error: String) {
        showMessageDialog(error)
    }
}
