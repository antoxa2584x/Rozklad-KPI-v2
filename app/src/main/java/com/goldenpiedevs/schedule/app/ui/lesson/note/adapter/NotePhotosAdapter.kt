package com.goldenpiedevs.schedule.app.ui.lesson.note.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.goldenpiedevs.schedule.app.R
import com.goldenpiedevs.schedule.app.core.dao.note.DaoNotePhoto
import kotlinx.android.synthetic.main.note_photo_layout.view.*

class NotePhotosAdapter(private var data: MutableList<DaoNotePhoto>,
                        private val isInEditMode: Boolean = false,
                        val listener: (DaoNotePhoto?) -> Unit) :
        RecyclerView.Adapter<NotePhotosAdapter.ViewHolder>() {

    companion object {
        private const val ADD_IMAGE = 0
        private const val IMAGE_PREVIEW = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return NotePhotosAdapter.ViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.note_photo_layout, parent, false))
    }

    override fun getItemViewType(position: Int) = if (position == 0 && isInEditMode) ADD_IMAGE else IMAGE_PREVIEW

    override fun getItemCount() = data.size + if (isInEditMode) 1 else 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (getItemViewType(position) == IMAGE_PREVIEW) {
            holder.addPhoto.visibility = View.INVISIBLE

            data[position - if (isInEditMode) 1 else 0].let { p ->
                Glide.with(holder.photoPreview)
                        .load(p.path)
                        .centerCrop()
                        .into(holder.photoPreview)

                holder.itemBackground.setOnClickListener {
                    listener(p)
                }

                holder.removePhoto.visibility = if (isInEditMode) View.VISIBLE else View.INVISIBLE
            }
        } else {
            holder.removePhoto.visibility = View.INVISIBLE
            holder.addPhoto.visibility = View.VISIBLE

            holder.itemBackground.setOnClickListener {
                listener(null)
            }
        }
    }

    fun updateData(data: List<DaoNotePhoto>) {
        this@NotePhotosAdapter.data.apply {
            clear()
            addAll(data)
            notifyDataSetChanged()
        }
    }

    fun getData() = data

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val photoPreview = view.note_photo_preview!!
        val removePhoto = view.remove_note_photo!!
        val addPhoto = view.add_note_photo!!
        val itemBackground = view.note_photo_itemview!!
    }
}
