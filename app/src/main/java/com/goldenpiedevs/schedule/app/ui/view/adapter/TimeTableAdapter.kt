package com.goldenpiedevs.schedule.app.ui.view.adapter

import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.goldenpiedevs.schedule.app.R
import com.goldenpiedevs.schedule.app.core.dao.timetable.DayModel
import com.goldenpiedevs.schedule.app.core.ext.context
import com.goldenpiedevs.schedule.app.core.ext.currentWeek
import com.goldenpiedevs.schedule.app.core.ext.today
import com.goldenpiedevs.schedule.app.core.ext.todayName
import io.realm.OrderedRealmCollection
import io.realm.RealmRecyclerViewAdapter
import kotlinx.android.synthetic.main.timetable_list_item.view.*


class TimeTableAdapter(data: OrderedRealmCollection<DayModel>?)
    : RealmRecyclerViewAdapter<DayModel, TimeTableAdapter.ViewHolder>(data, true) {

    lateinit var listener: (Int) -> Unit

    constructor(data: OrderedRealmCollection<DayModel>, listener: (Int) -> Unit) : this(data) {
        this.listener = listener
    }

    private var primaryColor: Int = 0
    private var secondaryColor: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        primaryColor = ContextCompat.getColor(parent.context, R.color.primary_text)
        secondaryColor = ContextCompat.getColor(parent.context, R.color.secondary_text)

        return ViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.timetable_list_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val day = getItem(position)!!

        holder.list.layoutManager = LinearLayoutManager(holder.itemView.context)
        holder.dayName.text = day.dayName

        val itemDecorator = DividerItemDecoration(holder.context, DividerItemDecoration.VERTICAL)
        itemDecorator.setDrawable(ContextCompat.getDrawable(holder.context, R.drawable.divider)!!)

        holder.list.addItemDecoration(DividerItemDecoration(holder.context, DividerItemDecoration.VERTICAL))

        holder.list.adapter = LessonsAdapter(day.lessons) {
            listener(it)
        }

        if (day.lessons.first()!!.lessonWeek - 1 == currentWeek) {
            if (day.dayNumber >= today.dayOfWeek.value) {
                holder.dayDate.text = today.plusDays((day.dayNumber - today.dayOfWeek.value).toLong()).toString()
            } else if (day.dayNumber < today.dayOfWeek.value) {
                holder.dayDate.text = today.plusWeeks(2)
                        .plusDays((day.dayNumber - today.dayOfWeek.value).toLong()).toString()
            }
        } else {
            holder.dayDate.text = today.plusWeeks(1)
                    .plusDays((day.dayNumber - today.dayOfWeek.value).toLong()).toString()
        }

        if (day.lessons.first()!!.lessonWeek - 1 != currentWeek)
            return

        if (holder.dayName.text.toString().toLowerCase() == todayName) {
            holder.dateLayout.setBackgroundResource(R.color.primary_dark)

            if (holder.dayName.currentTextColor != Color.WHITE)
                holder.dayName.setTextColor(Color.WHITE)
            holder.dayDate.apply {
                if (currentTextColor != Color.WHITE)
                    setTextColor(Color.WHITE)
                if (alpha != 0.8f)
                    alpha = 0.8f
            }
        } else {
            holder.dateLayout.setBackgroundResource(android.R.color.transparent)

            if (holder.dayName.currentTextColor != primaryColor)
                holder.dayName.setTextColor(primaryColor)

            holder.dayDate.apply {
                if (currentTextColor != secondaryColor)
                    setTextColor(secondaryColor)
                if (alpha != 1f)
                    alpha = 1f
            }
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val dayName = view.dayName!!
        val dayDate = view.dayDate!!
        val list = view.list!!
        val dateLayout = view.dateLayout!!
    }
}