package com.goldenpiedevs.schedule.app.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.goldenpiedevs.schedule.app.R
import kotlinx.android.synthetic.main.lesson_item_view_layout.view.*

class LessonItemView constructor(
        context: Context,
        attrs: AttributeSet? = null) : LinearLayout(context, attrs) {

    var subText: String? = null
        set(value) {
            subTitle.text = value
        }

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.lesson_item_view_layout, this)

        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.LessonItemView, 0, 0)

            title.text = typedArray.getString(R.styleable.LessonItemView_liv_title)

            subText = typedArray.getString(R.styleable.LessonItemView_liv_subtitle)
            subTitle.text = subText

            icon.setImageDrawable(typedArray.getDrawable(R.styleable.LessonItemView_liv_icon))
            typedArray.recycle()
        }
    }
}