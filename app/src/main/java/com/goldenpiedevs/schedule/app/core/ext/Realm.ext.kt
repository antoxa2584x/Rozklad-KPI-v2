package com.goldenpiedevs.schedule.app.core.ext

import io.realm.Realm
import io.realm.RealmModel


fun <T : RealmModel> ArrayList<T>.saveBodyToDB() {
    if (this.isEmpty())
        return

    val realm = Realm.getDefaultInstance()

    try {
        realm.executeTransaction {
            it.insertOrUpdate(this)
        }
    } catch (e: Exception) {
        e.printStackTrace()
    } finally {

        if (!realm.isClosed)
            realm.close()
    }
}