package com.goldenpiedevs.schedule.app.ui.lesson

import android.os.Bundle
import androidx.core.content.ContextCompat
import android.view.View
import com.goldenpiedevs.schedule.app.R
import com.goldenpiedevs.schedule.app.ui.base.BaseActivity
import com.r0adkll.slidr.Slidr
import io.realm.OrderedRealmCollection
import kotlinx.android.synthetic.main.lesson_activity_layout.*
import org.osmdroid.config.Configuration
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.Marker


class LessonActivity : BaseActivity<LessonPresenter, LessonView>(), LessonView {
    override fun getPresenterChild(): LessonPresenter = presenter

    private lateinit var presenter: LessonPresenter

    override fun getActivityLayout(): Int = R.layout.lesson_activity_layout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Configuration.getInstance().userAgentValue = packageName
        Slidr.attach(this)

        presenter = LessonImplementation()

        with(presenter) {
            attachView(this@LessonActivity)
            showLessonData(intent.extras)
        }

        teacher.setOnClickListener { presenter.onTeacherClick() }
    }

    override fun showLessonName(string: String) {
        widget_lesson_title.text = string
    }

    override fun showLessonTeachers(string: String) {
        teacher.apply {
            visibility = View.VISIBLE
            subText = string
        }
    }

    override fun showLessonRoom(string: String) {
        widget_lesson_location.apply {
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

    private var startMarker: Marker? = null

    override fun showLessonLocation(geoPoint: GeoPoint) {
        startMarker = Marker(map).apply {
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
                setZoom(17.0)
                setCenter(geoPoint)
            }
        }
    }

    override fun showLessonTime(string: String) {
        widget_lesson_time.apply {
            visibility = View.VISIBLE
            text = string
        }
    }

    override fun showNoteText(string: String) {
    }

    override fun showNotePhotos(fileNames: OrderedRealmCollection<String>) {
    }

    override fun showTeacherSelectDialog() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    override fun onDestroy() {
        map.overlays.remove(startMarker)
        startMarker = null

        super.onDestroy()
    }
}