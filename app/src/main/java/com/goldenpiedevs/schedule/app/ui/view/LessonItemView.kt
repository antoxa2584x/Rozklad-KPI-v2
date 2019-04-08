package com.goldenpiedevs.schedule.app.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.content.res.AppCompatResources
import com.goldenpiedevs.schedule.app.R
import kotlinx.android.synthetic.main.lesson_item_view_layout.view.*

class LessonItemView constructor(
        context: Context,
        attrs: AttributeSet? = null) : LinearLayout(context, attrs) {

    var subText: String? = null
        set(value) {
            field = value
            subTitle.text = field

            if (!isInEditMode)
                value?.let {
                    when {
                        it.isNotEmpty() -> subTitle.visibility = View.VISIBLE
                        it.isEmpty() -> subTitle.visibility = View.GONE
                    }
                }
        }

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.lesson_item_view_layout, this)

        attrs?.let {
            with(context.obtainStyledAttributes(it, R.styleable.LessonItemView, 0, 0)) {
                title.text = getString(R.styleable.LessonItemView_liv_title)

                subText = getString(R.styleable.LessonItemView_liv_subtitle)

                icon.setImageDrawable(AppCompatResources.getDrawable(context, getResourceId(R.styleable.LessonItemView_liv_icon, -1)))
                recycle()
            }
        }
    }
}