package com.goldenpiedevs.schedule.app.ui.view.adapter

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.goldenpiedevs.schedule.app.R
import com.goldenpiedevs.schedule.app.core.dao.timetable.DaoLessonModel
import kotlinx.android.synthetic.main.timetable_card_content.view.*

class LessonsAdapter() : RecyclerView.Adapter<LessonsAdapter.ViewHolder>() {


    lateinit var listener: (Int) -> Unit
    lateinit var data: ArrayList<DaoLessonModel>

    constructor(data: ArrayList<DaoLessonModel>, listener: (Int) -> Unit) : this() {
        this.listener = listener
        this.data = data
    }

    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(parent: ViewGroup, type: Int): ViewHolder {
        return LessonsAdapter.ViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.timetable_card_content, parent, false))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = data[position]

        holder.apply {
            currentLesson.visibility = if (model.hasNote) View.VISIBLE else View.INVISIBLE
            lessonTitle.text = model.lessonFullName
            time.text = model.getTime()
            location.text = "${model.lessonRoom} ${model.lessonType}"
            number.text = model.lessonNumber
            itemView.setOnClickListener { listener(model.lessonId) }
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val currentLesson = itemView.currentLesson!!
        val number = itemView.lessonNumber!!
        val lessonTitle = itemView.lessonTitle!!
        val time = itemView.time!!
        val location = itemView.location!!
    }
}


