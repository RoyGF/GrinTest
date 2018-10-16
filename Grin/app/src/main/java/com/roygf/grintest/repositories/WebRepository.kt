package com.roygf.grintest.repositories

import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.Volley

import android.content.Context
import com.android.volley.toolbox.JsonObjectRequest
import com.roygf.grintest.models.Device
import com.roygf.grintest.utils.JsonUtils
import org.jetbrains.anko.toast
import org.json.JSONObject
import java.util.*
import com.roygf.grintest.R


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
        body.put("strength", device.strength)
        val request = JsonObjectRequest(Request.Method.POST, url, body, saveSuccess, errorHttp)
        mQueue.add(request)
    }

    fun onSaveDeviceSuccess(response : JSONObject){
        val status = response.optString("status")
        if (status == "OK")
            saveListener?.onSaveSuccess()
        else
            saveListener?.onSaveError(mContext.getString(R.string.text_save_device_fail))
    }

    fun onHttpSuccess(response : JSONObject){
        val jsonArray = response.getJSONArray("objects")
        val devices = JsonUtils.getDevices(jsonArray)
        getDevicesListener?.onGetDevicesSuccess(devices)
    }

    fun onHttpFailed(error: VolleyError){
        mContext.toast(error.message.toString())
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