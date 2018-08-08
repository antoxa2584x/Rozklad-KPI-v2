package com.goldenpiedevs.schedule.app.ui.lesson

import android.os.Bundle
import com.goldenpiedevs.schedule.app.R
import com.goldenpiedevs.schedule.app.core.dao.timetable.DaoTeacherModel
import com.goldenpiedevs.schedule.app.ui.base.BaseActivity
import io.realm.OrderedRealmCollection
import org.osmdroid.util.GeoPoint

class LessonActivity : BaseActivity<LessonPresenter, LessonView>(), LessonView {
    override fun getPresenterChild(): LessonPresenter = presenter;

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

    override fun onNoteEditClick() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showLessonName(string: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showLessonTeachers(teachers: OrderedRealmCollection<DaoTeacherModel>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showLessonRoom(string: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showLessonType(string: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showLessonLocation(geoPoint: GeoPoint) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showLessonTime(string: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showNoteText(string: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showNotePhotos(fileNames: OrderedRealmCollection<String>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}