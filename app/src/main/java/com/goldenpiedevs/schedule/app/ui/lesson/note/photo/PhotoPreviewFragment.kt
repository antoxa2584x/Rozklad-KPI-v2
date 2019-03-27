package com.goldenpiedevs.schedule.app.ui.lesson.note.photo

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.goldenpiedevs.schedule.app.R
import com.goldenpiedevs.schedule.app.core.dao.note.DaoNotePhoto
import com.goldenpiedevs.schedule.app.ui.base.BaseFragment
import kotlinx.android.synthetic.main.note_photo_fragment_layout.*

/**
 * Created by Anton. A on 23.03.2019.
 * Version 1.0
 */
class PhotoPreviewFragment : BaseFragment() {

    companion object {
        const val PHOTO_DATA = "photo_data"

        fun getInstance(daoNotePhoto: DaoNotePhoto): PhotoPreviewFragment {
            return PhotoPreviewFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(PHOTO_DATA, daoNotePhoto)
                }
            }
        }
    }

    override fun getFragmentLayout() = R.layout.note_photo_fragment_layout

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        hideSystemUI()

        with(activity as AppCompatActivity) {
            setTheme(android.R.style.Theme_Black_NoTitleBar_Fullscreen)

            note_photo_preview.setOnClickListener {
                if (!supportActionBar!!.isShowing) {
                    showSystemUI()
                } else {
                    hideSystemUI()
                }
            }
        }

        Glide.with(this)
                .load(arguments!!.getParcelable<DaoNotePhoto>(PHOTO_DATA)!!.path)
                .into(note_photo_preview)
    }

    private fun showSystemUI() {
        with(activity as AppCompatActivity) {
            supportActionBar?.show()

            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        }
    }

    private fun hideSystemUI() {
        with(activity as AppCompatActivity) {
            supportActionBar?.hide()

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE
                        or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_FULLSCREEN)
            }
        }
    }
}