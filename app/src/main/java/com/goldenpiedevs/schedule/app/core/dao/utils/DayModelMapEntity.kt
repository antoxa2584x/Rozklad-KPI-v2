package com.goldenpiedevs.schedule.app.core.dao.utils

import com.goldenpiedevs.schedule.app.core.dao.timetable.DayModel
import io.realm.RealmObject

/**
 * Created by Anton. A on 01.08.2018.
 * Version 1.0
 */
open class DayModelMapEntity:RealmObject(){
    var key: String? = null
    var value: DayModel? = null
}