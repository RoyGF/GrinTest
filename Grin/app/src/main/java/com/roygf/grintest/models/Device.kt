package com.roygf.grintest.models

import android.os.Parcel
import android.os.Parcelable
import java.util.*

data class Device(
        var id : String?,
        var created : Date?,
        var name : String,
        var strength : String,
        var isAvailable : Boolean){

    constructor() : this(null, null, "", "", true)

}