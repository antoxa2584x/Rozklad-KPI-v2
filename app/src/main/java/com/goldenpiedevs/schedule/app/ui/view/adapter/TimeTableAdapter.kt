package com.goldenpiedevs.schedule.app.ui.view.adapter

import android.content.Context
import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.goldenpiedevs.schedule.app.R
import com.goldenpiedevs.schedule.app.core.dao.timetable.DaoDayModel
import com.goldenpiedevs.schedule.app.core.dao.timetable.getDayDate
import com.goldenpiedevs.schedule.app.core.ext.context
import com.goldenpiedevs.schedule.app.core.ext.currentWeek
import com.goldenpiedevs.schedule.app.core.ext.todayName
import kotlinx.android.synthetic.main.timetable_list_item.view.*
import kotlinx.android.synthetic.main.timetable_week_name_layout.view.*


class TimeTableAdapter(val data: List<DaoDayModel>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val TITLE = 1
        const val DAY = 2
    }

    constructor(data: List<DaoDayModel>, context: Context, listener: (String) -> Unit) : this(data) {
        this.listener = listener
        primaryColor = ContextCompat.getColor(context, R.color.primary_text)
        secondaryColor = ContextCompat.getColor(context, R.color.secondary_text)

        itemDecorator = DividerItemDecoration(context, DividerItemDecoration.VERTICAL).apply {
            setDrawable(ContextCompat.getDrawable(context, R.drawable.divider)!!)
        }
    }

    override fun getItemCount(): Int = data.size

    lateinit var listener: (String) -> Unit
    private var primaryColor: Int = 0
    private var secondaryColor: Int = 0

    private lateinit var itemDecorator: DividerItemDecoration

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            TITLE -> return TitleViewHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.timetable_week_name_layout, parent, false))
            DAY -> return DayViewHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.timetable_list_item, parent, false))
        }
        @Suppress("UNREACHABLE_CODE")
        return null!!
    }

    override fun getItemViewType(position: Int): Int {
        return if (data[position].dayNumber.toInt() < 0) TITLE else DAY
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            TITLE -> {
                (holder as TitleViewHolder).apply {
                    title.text = context.getText(
                            if (position == 0) R.string.first_week
                            else R.string.second_week)
                }
            }

            DAY -> {
                val day = data[position]

                (holder as DayViewHolder).apply {
                    list.layoutManager = LinearLayoutManager(itemView.context)
                    dayName.text = day.dayName

                    list.addItemDecoration(itemDecorator)

                    list.adapter = LessonsAdapter(day.lessons.toList()) { listener(it) }

                    dayDate.text = day.lessons.first()!!.getDayDate()

                    //Many if statements for more performance of View's
                    if (dayName.text.toString().toLowerCase() == todayName &&
                            (day.weekNumber.toInt() - 1) == currentWeek) {
                        dateLayout.setBackgroundResource(R.color.primary_dark)

                        if (dayName.currentTextColor != Color.WHITE)
                            dayName.setTextColor(Color.WHITE)

                        dayDate.apply {
                            if (currentTextColor != Color.WHITE)
                                setTextColor(Color.WHITE)
                            if (alpha != 0.8f)
                                alpha = 0.8f
                        }
                    } else {
                        dateLayout.setBackgroundResource(android.R.color.white)

                        if (dayName.currentTextColor != primaryColor)
                            dayName.setTextColor(primaryColor)

                        dayDate.apply {
                            if (currentTextColor != secondaryColor)
                                setTextColor(secondaryColor)
                            if (alpha != 1f)
                                alpha = 1f
                        }
                    }
                }
            }
        }
    }

    class TitleViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title = view.title!!
    }

    class DayViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val dayName = view.dayName!!
        val dayDate = view.dayDate!!
        val list = view.list!!
        val dateLayout = view.dateLayout!!
    }
}