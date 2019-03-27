package com.goldenpiedevs.schedule.app.ui.lesson

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.core.content.ContextCompat
import com.goldenpiedevs.schedule.app.R
import com.goldenpiedevs.schedule.app.ui.base.BaseActivity
import com.r0adkll.slidr.Slidr
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
            setFragmentManager(supportFragmentManager)
            showLessonData(intent.extras!!)
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

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        menu?.findItem(R.id.edit_note)?.isVisible = !presenter.isInEditMode()

        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item!!.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            R.id.edit_note -> {
                presenter.onEditNoteClick()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun saveNote() {
        presenter.onNoteSaved()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.lessons_menu, menu)
        return true
    }

    override fun onBackPressed() {
        presenter.onBackPressed()
        super.onBackPressed()
    }

    override fun onDestroy() {
        map.overlays.remove(startMarker)
        startMarker = null

        super.onDestroy()
    }

    fun deleteNote() {
        presenter.onNoteDeleted()
    }
}