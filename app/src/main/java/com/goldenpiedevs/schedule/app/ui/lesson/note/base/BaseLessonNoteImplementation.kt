package com.goldenpiedevs.schedule.app.ui.lesson.note.base

import android.graphics.Color
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.esafirm.imagepicker.features.ImagePicker
import com.esafirm.imagepicker.model.Image
import com.goldenpiedevs.schedule.app.core.dao.note.DaoNoteModel
import com.goldenpiedevs.schedule.app.core.dao.note.DaoNotePhoto
import com.goldenpiedevs.schedule.app.core.utils.preference.AppPreference
import com.goldenpiedevs.schedule.app.ui.base.BasePresenterImpl
import com.goldenpiedevs.schedule.app.ui.fragment.keeper.FragmentKeeperActivity
import com.goldenpiedevs.schedule.app.ui.lesson.note.adapter.NotePhotosAdapter
import com.goldenpiedevs.schedule.app.ui.lesson.note.photo.PhotoPreviewFragment
import org.jetbrains.anko.startActivity

open class BaseLessonNoteImplementation<T : BaseLessonNoteView> : BasePresenterImpl<T>(), BaseLessonNotePresenter<T> {

   protected lateinit var noteModel: DaoNoteModel
    protected  lateinit var adapter: NotePhotosAdapter
    var isInEditMode: Boolean = false

    private var images = arrayListOf<Image>()

    override fun setEditMode(inEditMode: Boolean) {
        isInEditMode = inEditMode
    }

    override fun getNote(lessonId: String) {
        noteModel = DaoNoteModel.get(lessonId, AppPreference.groupId)

        images = ArrayList(noteModel.photos.map {
            Image(it.id, it.name, it.path)
        })

        adapter = NotePhotosAdapter(noteModel.photos, isInEditMode) {
            it?.let { photo ->
                if (isInEditMode) {
                    deletePhoto(photo)
                } else {
                    showPhoto(photo)
                }
            } ?: run {
                onAddPhotoClick()
            }
        }

        view.setAdapter(adapter,
                if (isInEditMode) GridLayoutManager(view.getContext(), calculateNoOfColumns(80f))
                else LinearLayoutManager(view.getContext(), RecyclerView.HORIZONTAL, false))

        view.setNoteText(noteModel.note)
    }

    private fun deletePhoto(uri: DaoNotePhoto) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun showPhoto(notePhoto: DaoNotePhoto) {
        view.getContext().startActivity<FragmentKeeperActivity>(PhotoPreviewFragment.PHOTO_DATA to notePhoto)
    }

    private fun onAddPhotoClick() {
        ImagePicker.create(view.getIt() as BaseLessonNoteFragment)
                .folderMode(true)
                .toolbarImageTitle("Tap to select")
                .toolbarArrowColor(Color.WHITE)
                .includeVideo(false)
                .multi()
                .origin(images)
                .limit(10)
                .showCamera(true)
                .imageDirectory("Camera")
                .start()
    }

    override fun onPhotosSelected(list: ArrayList<Image>) {
        this.images = list

        adapter.updateData(list.map {
            DaoNotePhoto().apply {
                id = it.id
                path = it.path
                name = it.name
            }
        })
    }

    private fun calculateNoOfColumns(columnWidthDp: Float): Int {
        val displayMetrics = view.getContext().resources.displayMetrics
        val screenWidthDp = (displayMetrics.widthPixels - (72 * displayMetrics.density)) / displayMetrics.density
        return (screenWidthDp / columnWidthDp + 0.5).toInt()
    }
}
