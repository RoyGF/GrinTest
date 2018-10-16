package com.roygf.grintest.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
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
        layout_loading.visibility = View.VISIBLE
        val webRepository = WebRepository(applicationContext)
        webRepository.getDevicesListener = this
        webRepository.fetchSavedDevices()
    }

    override fun onGetDevicesSuccess(devices: ArrayList<Device>) {
        layout_loading.visibility = View.GONE
        mAdapter = SavedDevicesAdapter(applicationContext, devices)
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
