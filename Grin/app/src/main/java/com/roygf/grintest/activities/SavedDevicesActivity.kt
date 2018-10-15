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

class SavedDevicesActivity :
        BaseActivity(),
        WebRepository.DevicesListener {

    var mAdapter : SavedDevicesAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_saved_devices)
        fetchDevices()
        button_sort_name.setOnClickListener {sortListByName()}
        button_sort_date.setOnClickListener{sortListByDate()}
    }


    fun fetchDevices(){
        val webRepository = WebRepository(applicationContext)
        webRepository.getDevicesListener = this
        webRepository.fetchSavedDevices()
    }


    override fun onGetDevicesSuccess(devices: ArrayList<Device>) {
        mAdapter = SavedDevicesAdapter(applicationContext, devices)
        //var sortedList = devices.sortedWith(compareBy {it.created})

        mSavedDevicesRecycler.layoutManager = LinearLayoutManager(applicationContext)
        mSavedDevicesRecycler.adapter = mAdapter
    }

    private fun sortListByName(){
        mAdapter?.sortByName()
    }

    private fun sortListByDate(){
        mAdapter?.sortByDate()
    }

}
