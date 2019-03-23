package com.goldenpiedevs.schedule.app.ui.map

import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.goldenpiedevs.schedule.app.R
import com.goldenpiedevs.schedule.app.ui.base.BaseFragment
import kotlinx.android.synthetic.main.map_fragment_layout.*

class MapFragment : BaseFragment(), MapView {
    override fun getFragmentLayout(): Int = R.layout.map_fragment_layout

    private lateinit var presenter: MapPresenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = MapImplementation()

        with(presenter) {
            attachView(this@MapFragment)
            loadMap()
        }
    }

    override fun showMap() {
        Glide.with(this)
                .load(R.drawable.map)
                .into(map_photo_view)
    }
}