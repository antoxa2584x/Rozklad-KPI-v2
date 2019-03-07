package com.goldenpiedevs.schedule.app.ui.timetable.adapter

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
import com.goldenpiedevs.schedule.app.core.ext.todayNumberInWeek
import kotlinx.android.synthetic.main.timetable_list_item.view.*
import kotlinx.android.synthetic.main.timetable_week_name_layout.view.*


class TimeTableAdapter(var data: MutableList<DaoDayModel>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val TITLE = 1
        const val DAY = 2
    }

    lateinit var listener: (String) -> Unit

    private var primaryColor: Int = 0
    private var secondaryColor: Int = 0
    private var viewPool = RecyclerView.RecycledViewPool()

    private lateinit var itemDecorator: DividerItemDecoration

    constructor(data: MutableList<DaoDayModel>, context: Context, listener: (String) -> Unit) : this(data) {
        this.listener = listener

        with(context) {
            primaryColor = ContextCompat.getColor(this, R.color.primary_text)
            secondaryColor = ContextCompat.getColor(this, R.color.secondary_text)
        }

        itemDecorator = DividerItemDecoration(context, DividerItemDecoration.VERTICAL).apply {
            setDrawable(ContextCompat.getDrawable(context, R.drawable.divider)!!)
        }
    }

    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TITLE -> TitleViewHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.timetable_week_name_layout, parent, false))
            else -> DayViewHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.timetable_list_item, parent, false), viewPool, itemDecorator, listener)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (data[position].dayNumber.toInt() == -1) TITLE else DAY
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            TITLE -> {
                (holder as TitleViewHolder).apply {
                    title.text = context.getText(
                            if (position == 0) R.string.first_week else R.string.second_week)
                }
            }

            DAY -> {
                val day = data[position]

                (holder as DayViewHolder).apply {
                    dayName.text = day.dayName

                    (list.adapter as LessonsAdapter).data = day.lessons

                    dayDate.text = day.lessons.first()?.getDayDate()

                    //Many if statements for more performance of View's
                    if (day.dayNumber == todayNumberInWeek &&
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

    fun clear() {
        data.clear()
        notifyDataSetChanged()
    }

    fun update(data: List<DaoDayModel>) {
        this.data.apply {
            clear()
            addAll(data)
        }

        notifyDataSetChanged()
    }

    internal class TitleViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title = view.title!!
    }

    internal class DayViewHolder(view: View, viewPool: RecyclerView.RecycledViewPool?,
                                 itemDecorator: RecyclerView.ItemDecoration,
                                 listener: (String) -> Unit) : RecyclerView.ViewHolder(view) {

        init {
            view.list!!.apply {
                setRecycledViewPool(viewPool)
                addItemDecoration(itemDecorator)

                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                        .apply {
                            isItemPrefetchEnabled = true
                            initialPrefetchItemCount = 6
                        }

                adapter = LessonsAdapter { listener(it) }.apply {
                    if (!hasObservers()) {
                        setHasStableIds(true)
                    }
                }

                setItemViewCacheSize(6)
                isDrawingCacheEnabled = true
                drawingCacheQuality = View.DRAWING_CACHE_QUALITY_LOW
            }
        }

        val dayName = view.dayName!!
        val dayDate = view.dayDate!!
        val list = view.list!!
        val dateLayout = view.dateLayout!!
    }
}