package com.goldenpiedevs.schedule.app.ui.timetable.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.goldenpiedevs.schedule.app.R
import com.goldenpiedevs.schedule.app.core.dao.timetable.DaoLessonModel
import kotlinx.android.synthetic.main.day_list_lesson_item.view.*

class LessonsAdapter() : androidx.recyclerview.widget.RecyclerView.Adapter<LessonsAdapter.ViewHolder>() {
    lateinit var listener: (String) -> Unit

    var data: List<DaoLessonModel> = listOf()
        set(value) {
        field = value
        notifyDataSetChanged()
    }

    constructor(listener: (String) -> Unit) : this() {
        this.listener = listener
    }

    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(parent: ViewGroup, type: Int): ViewHolder {
        return LessonsAdapter.ViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.day_list_lesson_item, parent, false))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = data[position]

        model.let {
            holder.apply {
                if (it.haveNote) {
                    number.setTextColor(Color.WHITE)
                    hasNote.visibility = View.VISIBLE
                } else {
                    number.setTextColor(ContextCompat.getColor(number.context, R.color.secondary_text))
                    hasNote.visibility = View.INVISIBLE
                }

                lessonTitle.text = it.lessonName
                time.text = it.getTime()
                location.text = "${it.lessonRoom} ${it.lessonType}"
                number.text = it.lessonNumber
                itemView.setOnClickListener { listener(model.id) }
            }
        }
    }

    class ViewHolder(view: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view) {
        val hasNote = itemView.teimetable_lesson_has_note!!
        val number = itemView.teimetable_lesson_number!!
        val lessonTitle = itemView.teimetable_lesson_title!!
        val time = itemView.teimetable_lesson_time!!
        val location = itemView.teimetable_lesson_location!!
    }
}


