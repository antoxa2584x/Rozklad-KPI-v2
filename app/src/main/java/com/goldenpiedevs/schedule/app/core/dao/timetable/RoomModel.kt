package com.goldenpiedevs.schedule.app.core.dao.timetable

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import org.osmdroid.util.GeoPoint

@RealmClass
open class RoomModel : RealmObject() {
    @PrimaryKey
    @SerializedName("room_id")
    var roomId: String? = null

    @SerializedName("room_longitude")
    var roomLongitude: Double? = null
    @SerializedName("room_name")
    var roomName: String? = null
    @SerializedName("room_latitude")
    var roomLatitude: Double? = null

    fun getGeoPoint():GeoPoint= GeoPoint(roomLatitude!!, roomLongitude!!)
}