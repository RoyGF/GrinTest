package com.roygf.grintest.utils

import com.roygf.grintest.models.Device
import org.json.JSONArray
import org.json.JSONObject
import java.util.*

class JsonUtils{

    companion object {

        fun getDevices(jsonArray : JSONArray) : ArrayList<Device> {
            val devices = ArrayList<Device>()
            val jsonLength = jsonArray.length() - 1
            for (i in 0..jsonLength){
                val jObject = jsonArray.getJSONObject(i)
                devices.add(getDeviceFromJson(jObject))
            }
            return devices
        }

        fun getDeviceFromJson(jObject: JSONObject) : Device{
            val id = jObject.optString("id")
            val created = DateUtils.getDate(jObject.optString("created"))
            val strength = jObject.optString("strength")
            val name = jObject.optString("name")
            return Device(id, created, name, strength, false)
        }
    }
}