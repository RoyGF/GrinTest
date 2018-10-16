package com.roygf.grintest.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.roygf.grintest.R
import com.roygf.grintest.models.Device
import com.roygf.grintest.utils.DateUtils
import kotlinx.android.synthetic.main.item_saved_device.view.*
import java.util.*

class SavedDevicesAdapter (val context: Context, val devices : ArrayList<Device>)
    : RecyclerView.Adapter<SavedDevicesAdapter.ViewHolder>() {

    var mDevices : List<Device>  = devices

    class ViewHolder (view : View) : RecyclerView.ViewHolder(view){
        val name = view.text_name
        val strength = view.text_strength
        val created = view.text_created
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_saved_device, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val device = mDevices[position]
        val created = DateUtils.formatDate(device.created)

        holder.name?.text = context.getString(R.string.text_device_name, device.name)
        holder.created?.text = context.getString(R.string.text_device_created, created)
        holder.strength?.text = context.getString(R.string.text_device_strength, device.strength)

    }

    override fun getItemCount(): Int {
        return devices.size
    }

    fun sortByName(){
        mDevices = devices.sortedWith(compareBy {it.name})
        notifyDataSetChanged()
    }

    fun sortByDate(){
        mDevices = devices.sortedWith(compareBy {it.created})
        notifyDataSetChanged()
    }

}