package com.goldenpiedevs.schedule.app.ui.lesson

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.View
import com.goldenpiedevs.schedule.app.R
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

    override fun showLessonTeachers(string: String) {
        teacher.apply {
            visibility = View.VISIBLE
            subText = string
        }
    }

    override fun showLessonRoom(string: String) {
        location.apply {
            visibility = View.VISIBLE
            subText = string
        }
    }

    override fun showLessonType(string: String) {
        type.apply {
            visibility = View.VISIBLE
            subText = string
        }
    }

    override fun showLessonLocation(geoPoint: GeoPoint) {
        val startMarker = Marker(map).apply {
            position = geoPoint
            setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
            icon = ContextCompat.getDrawable(this@LessonActivity, R.drawable.ic_room_location)

            //Ignore marker click
            setOnMarkerClickListener { _, _ -> true }
        }

        map.apply {
            visibility = View.VISIBLE
            setBuiltInZoomControls(false)
            overlays.add(startMarker)
            controller.apply {
                setZoom(19.0)
                setCenter(geoPoint)
            }
        }
    }

    override fun showLessonTime(string: String) {
        time.apply {
            visibility = View.VISIBLE
            subText = string
        }
    }

    override fun showNoteText(string: String) {
    }

    override fun showNotePhotos(fileNames: OrderedRealmCollection<String>) {
    }

}