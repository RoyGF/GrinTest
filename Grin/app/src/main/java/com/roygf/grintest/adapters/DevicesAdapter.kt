package com.roygf.grintest.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.roygf.grintest.models.Device
import com.roygf.grintest.R
import kotlinx.android.synthetic.main.item_device.view.*

class DevicesAdapter (val context: Context)
    : RecyclerView.Adapter<DevicesAdapter.ViewHolder>() {

    private var devices : ArrayList<Device> = arrayListOf()

    class ViewHolder (view : View) : RecyclerView.ViewHolder(view){
        val mDeviceName = view.mDeviceName
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_device, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mDeviceName?.text = devices[position].name
    }

    override fun getItemCount(): Int {
        return devices.size
    }

    fun addDevice(device : Device){
        devices.add(device)
        notifyDataSetChanged()
    }

}