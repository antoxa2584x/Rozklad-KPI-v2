package com.goldenpiedevs.schedule.app.core.dao.note

import android.os.Parcel
import android.os.Parcelable
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import java.util.*

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
@RealmClass
open class DaoNotePhoto() : RealmObject(), Parcelable {
    @PrimaryKey
    var key = UUID.randomUUID().toString()

    var id: Long = -1
    var path: String = ""
    var name: String = ""

    constructor(parcel: Parcel) : this() {
        key = parcel.readString()
        id = parcel.readLong()
        path = parcel.readString()
        name = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(key)
        parcel.writeLong(id)
        parcel.writeString(path)
        parcel.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DaoNotePhoto> {
        override fun createFromParcel(parcel: Parcel): DaoNotePhoto {
            return DaoNotePhoto(parcel)
        }

        override fun newArray(size: Int): Array<DaoNotePhoto?> {
            return arrayOfNulls(size)
        }
    }
}