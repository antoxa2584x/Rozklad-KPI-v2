package com.goldenpiedevs.schedule.app.ui.teachers.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.goldenpiedevs.schedule.app.R
import com.goldenpiedevs.schedule.app.core.dao.timetable.DaoTeacherModel
import com.goldenpiedevs.schedule.app.ui.view.ColorHelper
import io.realm.OrderedRealmCollection
import io.realm.RealmRecyclerViewAdapter
import kotlinx.android.synthetic.main.teacher_list_item.view.*

class TeachersAdapter(collection: OrderedRealmCollection<DaoTeacherModel>, val onTeacherClick: (String) -> Unit)
    : RealmRecyclerViewAdapter<DaoTeacherModel, TeachersAdapter.ViewHolder>(collection, true) {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.teacher_list_item, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val teacher = data?.get(position)

        teacher?.let {
            viewHolder.apply {
                teacherName.text = it.teacherName
                teacherRating.rating = it.teacherRating

                val letter = it.teacherName.first().toString()
                teacherPictureLetter.text = letter
                teacherPicture.setImageDrawable(ColorHelper.generateDrawable(ColorHelper.getMaterialColor(letter), 40f))

                view.setOnClickListener { onTeacherClick(teacher.teacherId) }
            }
        }
    }

    class ViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
        val teacherName = itemView.teachers_item_name
        val teacherPicture = itemView.teachers_item_picture
        val teacherPictureLetter = itemView.teachers_item_picture_letter
        val teacherRating = itemView.teachers_item_rating
        val view = itemView.teachers_item_view
    }
}