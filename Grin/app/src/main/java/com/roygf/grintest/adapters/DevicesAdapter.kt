package com.roygf.grintest.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.roygf.grintest.models.Device
import com.roygf.grintest.R
import kotlinx.android.synthetic.main.item_device.view.*
import kotlinx.android.synthetic.main.item_device_texts.view.*

class DevicesAdapter (val context: Context)
    : RecyclerView.Adapter<DevicesAdapter.ViewHolder>(), View.OnClickListener {

    private var devices : ArrayList<Device> = arrayListOf()
    var listener : DeviceListener? = null

    class ViewHolder (view : View, listener : View.OnClickListener)
        : RecyclerView.ViewHolder(view){
        val name = view.device_name
        val strength = view.device_strength
        val button = view.button_save
        init {
            button.setOnClickListener(listener)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_device, parent, false), this)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val device = devices[position]
        holder.name.text = context.getString(R.string.text_device_name, device.name)
        holder.strength.text = context.getString(R.string.text_device_strenght_2, device.strength)
        holder.button.tag = device
    }

    override fun getItemCount(): Int {
        return devices.size
    }

    override fun onClick(v: View?) {
        val device : Device = v?.tag as Device
        listener?.onDeviceSelected(device)
    }

    fun addDevice(device : Device){
        devices.add(device)
        notifyDataSetChanged()
    }

    fun emptyList(){
        devices.clear()
        notifyDataSetChanged()
    }

    interface DeviceListener{
        fun onDeviceSelected(device : Device)
    }

}