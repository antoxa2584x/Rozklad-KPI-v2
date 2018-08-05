package com.goldenpiedevs.schedule.app.ui.view.adapter

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.goldenpiedevs.schedule.app.R
import com.goldenpiedevs.schedule.app.core.dao.timetable.LessonModel
import io.realm.OrderedRealmCollection
import io.realm.RealmRecyclerViewAdapter
import kotlinx.android.synthetic.main.timetable_card_content.view.*

class LessonsAdapter(data: OrderedRealmCollection<LessonModel>)
    : RealmRecyclerViewAdapter<LessonModel, LessonsAdapter.ViewHolder>(data, true) {

    override fun onCreateViewHolder(parent: ViewGroup, type: Int): ViewHolder {
        return LessonsAdapter.ViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.timetable_card_content, parent, false))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = getItem(position)!!
        holder.currentLesson.visibility = if (model.hasNote) View.VISIBLE else View.INVISIBLE
        holder.lessonTitle.text = model.lessonFullName!!
        holder.time.text = "${model.timeStart}-${model.timeEnd}"
        holder.location.text = "${model.lessonRoom} ${model.lessonType}"
        holder.number.text = model.lessonNumber
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val currentLesson = itemView.currentLesson
        val number = itemView.lessonNumber
        val lessonTitle = itemView.lessonTitle
        val time = itemView.time
        val location = itemView.location
    }
}


