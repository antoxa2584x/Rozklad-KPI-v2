package com.goldenpiedevs.schedule.app.ui.lesson

import android.content.Intent
import android.content.res.Resources
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.goldenpiedevs.schedule.app.R
import com.goldenpiedevs.schedule.app.core.utils.util.GlideApp
import com.goldenpiedevs.schedule.app.ui.base.BaseActivity
import com.r0adkll.slidr.Slidr
import kotlinx.android.synthetic.main.lesson_activity_layout.*
import org.osmdroid.config.Configuration
import org.osmdroid.util.GeoPoint


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
        if (string.isEmpty())
            return

        type.apply {
            visibility = View.VISIBLE
            subText = string
        }
    }

    override fun showLessonLocation(geoPoint: GeoPoint) {
        val weight = Resources.getSystem().displayMetrics.widthPixels
        val height = (weight / 16) * 8

        val url = "https://image.maps.cit.api.here.com/mia/1.6/mapview?" +
                "app_id=YOUR_CODE&app_code=YOUR_CODE&nocp&" +
                "c=${geoPoint.latitude},${geoPoint.longitude}&u=50&" +
                "h=$height&w=$weight&z=17&f=1&ml=ukr&style=mini"

        GlideApp.with(map)
                .load(url)
                .into(map)

        map.setOnClickListener {
            val intent = Intent(android.content.Intent.ACTION_VIEW,
                    Uri.parse("http://maps.google.com/maps?q=loc:${geoPoint.latitude},${geoPoint.longitude}"))
            startActivity(intent)
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

    fun deleteNote() {
        presenter.onNoteDeleted()
    }
}