package com.goldenpiedevs.schedule.app.ui.fragment.keeper

import android.os.Bundle
import com.goldenpiedevs.schedule.app.R
import com.goldenpiedevs.schedule.app.core.dao.note.DaoNotePhoto
import com.goldenpiedevs.schedule.app.core.dao.timetable.DaoTeacherModel
import com.goldenpiedevs.schedule.app.ui.base.BasePresenterImpl
import com.goldenpiedevs.schedule.app.ui.lesson.note.photo.PhotoPreviewFragment
import com.goldenpiedevs.schedule.app.ui.timetable.TimeTableFragment

class FragmentKeeperImplementation : BasePresenterImpl<FragmentKeeperView>(), FragmentKeeperPresenter {

    lateinit var supportFragmentManager: androidx.fragment.app.FragmentManager

    override fun setFragmentManager(fragmentManager: androidx.fragment.app.FragmentManager) {
        supportFragmentManager = fragmentManager
    }

    override fun showFragmentForBundle(bundle: Bundle?, savedInstanceState: Bundle?) {
        bundle?.let {
            when {
                it.containsKey(TimeTableFragment.TEACHER_ID) -> {
                    view.setTitle(DaoTeacherModel.getTeacher(it.getString(TimeTableFragment.TEACHER_ID)!!.toInt()).teacherName)

                    if (savedInstanceState == null)
                        showTeacherTimeTableFragment(it.getString(TimeTableFragment.TEACHER_ID)!!)
                }
                it.containsKey(PhotoPreviewFragment.PHOTO_DATA) -> {
                    view.setTitle(it.getParcelable<DaoNotePhoto>(PhotoPreviewFragment.PHOTO_DATA)!!.name)

                    if (savedInstanceState == null)
                        showPhotoPreviewFragment(it.getParcelable(PhotoPreviewFragment.PHOTO_DATA)!!)

                    view.makeFullScreen()
                }
            }
        }
    }

    private fun showTeacherTimeTableFragment(string: String) {
        supportFragmentManager.beginTransaction()
                .replace(R.id.container, TimeTableFragment.getInstance(string))
                .addToBackStack(null)
                .commit()
    }

    private fun showPhotoPreviewFragment(daoNotePhoto: DaoNotePhoto) {
        supportFragmentManager.beginTransaction()
                .replace(R.id.container, PhotoPreviewFragment.getInstance(daoNotePhoto))
                .addToBackStack(null)
                .commit()
    }
}