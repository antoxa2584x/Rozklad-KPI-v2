package com.goldenpiedevs.schedule.app.ui.lesson

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.View
import com.goldenpiedevs.schedule.app.R
import com.goldenpiedevs.schedule.app.core.dao.timetable.DaoTeacherModel
import com.goldenpiedevs.schedule.app.ui.base.BaseActivity
import io.realm.OrderedRealmCollection
import kotlinx.android.synthetic.main.lesson_activity_layout.*
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.Marker


class LessonActivity : BaseActivity<LessonPresenter, LessonView>(), LessonView {
    override fun getPresenterChild(): LessonPresenter = presenter

    private lateinit var presenter: LessonPresenter

    override fun getActivityLayout(): Int = R.layout.lesson_activity_layout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        presenter = LessonImplementation()

        with(presenter) {
            attachView(this@LessonActivity)
            showLessonData(intent.extras)
        }
    }

    override fun showLessonName(string: String) {
        lessonTitle.text = string
    }

    override fun showLessonTeachers(teachers: OrderedRealmCollection<DaoTeacherModel>) {
    }

    override fun showLessonRoom(string: String) {
        location.visibility = View.VISIBLE
        location.subText = string
    }

    override fun showLessonType(string: String) {
        type.visibility = View.VISIBLE
        type.subText = string
    }

    override fun showLessonLocation(geoPoint: GeoPoint) {
        map.visibility = View.VISIBLE

        val mapController = map.controller
        map.setBuiltInZoomControls(false)
        mapController.setZoom(19.0)
        mapController.setCenter(geoPoint)

        val startMarker = Marker(map)
        startMarker.position = geoPoint
        startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
        startMarker.icon = ContextCompat.getDrawable(this, R.drawable.ic_room_location)
        map.overlays.add(startMarker)
    }

    override fun showLessonTime(string: String) {
        time.visibility = View.VISIBLE
        time.subText = string
    }

    override fun showNoteText(string: String) {
    }

    override fun showNotePhotos(fileNames: OrderedRealmCollection<String>) {
    }

}