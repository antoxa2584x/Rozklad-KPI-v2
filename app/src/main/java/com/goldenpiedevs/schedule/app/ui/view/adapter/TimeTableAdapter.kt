package com.goldenpiedevs.schedule.app.ui.view.adapter

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.goldenpiedevs.schedule.app.R
import io.realm.OrderedRealmCollection
import io.realm.RealmRecyclerViewAdapter
import kotlinx.android.synthetic.main.timetable_list_item.view.*

class TimeTableAdapter(data: OrderedRealmCollection<DaoDayModel>?)
    : RealmRecyclerViewAdapter<DaoDayModel, TimeTableAdapter.ViewHolder>(data, true) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.timetable_list_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, p1: Int) {
        holder.list.layoutManager = LinearLayoutManager(holder.itemView.context)
        holder.dayName.text = getItem(p1)!!.dayModel!!.dayName
        holder.list.adapter = LessonsAdapter(getItem(p1)!!.dayModel!!.lessons!!)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val dayName = view.dayName
        val list = view.list
    }
}