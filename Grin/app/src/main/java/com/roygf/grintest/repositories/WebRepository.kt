package com.roygf.grintest.repositories

import android.annotation.SuppressLint
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.Volley

import android.content.Context
import com.android.volley.toolbox.JsonObjectRequest
import com.roygf.grintest.models.Device
import org.jetbrains.anko.toast
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*


class WebRepository (context : Context) {

    var url_base = "http://mock.westcentralus.cloudapp.azure.com/grin_test/bluetooth/"
    var mQueue = Volley.newRequestQueue(context)
    var successHttp = Response.Listener<JSONObject> {response-> onHttpSuccess(response) }
    var saveSuccess = Response.Listener<JSONObject> {response-> onSaveDeviceSuccess(response)}
    var errorHttp = Response.ErrorListener { error : VolleyError -> onHttpFailed(error) }
    var mContext : Context = context
    var getDevicesListener : DevicesListener? = null
    var saveListener : SaveDeviceListener? = null

    /**Methods**/
    fun fetchSavedDevices(){
        val url = url_base + "all?order=1"
        val request = JsonObjectRequest(Request.Method.GET, url, null, successHttp, errorHttp)
        mQueue.add(request)
    }

    fun saveDevice(device : Device){
        val url = url_base + "create"
        val body = JSONObject()
        body.put("name", device.name)
        body.put("strength", device.strenght)
        val request = JsonObjectRequest(Request.Method.POST, url, body, saveSuccess, errorHttp)
        mQueue.add(request)
    }

    fun onSaveDeviceSuccess(response : JSONObject){
        val jsonArray = response.getJSONObject("object")
        if (jsonArray != null)
            saveListener?.onSaveSuccess()
        else
            saveListener?.onSaveError("No se pudo guardar el dispositivo")
    }

    fun onHttpSuccess(response : JSONObject){
        val jsonArray = response.getJSONArray("objects")
        val devices = getDevices(jsonArray)
        getDevicesListener?.onGetDevicesSuccess(devices)
    }

    fun onHttpFailed(error: VolleyError){
        mContext.toast(error.message.toString())
    }

    fun getDevices(jsonArray : JSONArray) : ArrayList<Device>{
        val devices = ArrayList<Device>()

        val jsonLength = jsonArray.length() - 1

        for (i in 0..jsonLength){
            val jObject = jsonArray.getJSONObject(i)
            val id = jObject.optString("id")
            val created = getDate(jObject.optString("created"))
            val strength = jObject.optString("strength")
            val name = jObject.optString("name")
            val device = Device(id, created, name, strength, false)
            devices.add(device)
        }
        return devices

    }

    @SuppressLint("SimpleDateFormat")
    fun getDate(dateText : String) : Date{
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss'Z'")
        return formatter.parse(dateText)
    }



    /**Interfaces**/
    interface DevicesListener{
        fun onGetDevicesSuccess(devices : ArrayList<Device>)
    }

    interface SaveDeviceListener{
        fun onSaveSuccess()
        fun onSaveError(error : String)
    }
}