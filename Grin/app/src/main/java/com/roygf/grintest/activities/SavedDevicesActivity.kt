package com.roygf.grintest.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.roygf.grintest.R
import com.roygf.grintest.adapters.SavedDevicesAdapter
import com.roygf.grintest.models.Device
import com.roygf.grintest.repositories.WebRepository
import kotlinx.android.synthetic.main.activity_saved_devices.*
import java.util.ArrayList

class SavedDevicesActivity : AppCompatActivity(), WebRepository.DevicesListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_saved_devices)
        fetchDevices()
    }


    fun fetchDevices(){
        val webRepository = WebRepository(applicationContext)
        webRepository.getDevicesListener = this
        webRepository.fetchSavedDevices()
    }


    override fun onGetDevicesSuccess(devices: ArrayList<Device>) {
        val adapter = SavedDevicesAdapter(applicationContext, devices)
        mSavedDevicesRecycler.layoutManager = LinearLayoutManager(applicationContext)
        mSavedDevicesRecycler.adapter = adapter
    }

}
