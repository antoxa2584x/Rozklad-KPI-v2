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
    var roomId: String = ""

    @SerializedName("room_longitude")
    var roomLongitude: Double = 0.0
    @SerializedName("room_name")
    var roomName: String = ""
    @SerializedName("room_latitude")
    var roomLatitude: Double = 0.0

    fun getGeoPoint(): GeoPoint = GeoPoint(roomLatitude, roomLongitude)
}