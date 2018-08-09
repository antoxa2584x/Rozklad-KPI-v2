package com.goldenpiedevs.schedule.app.ui.map

import android.os.Bundle
import android.view.View
import com.davemorrissey.labs.subscaleview.ImageSource
import com.goldenpiedevs.schedule.app.R
import com.goldenpiedevs.schedule.app.ui.base.BaseFragment
import kotlinx.android.synthetic.main.map_activity_layout.*

class MapFragment : BaseFragment(), MapView {
    override fun getFragmentLayout(): Int = R.layout.map_activity_layout

    private lateinit var presenter: MapPresenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = MapImplementation()

        with(presenter) {
            attachView(this@MapFragment)
            loadMap()
        }
    }

    override fun showMap(imageSource: ImageSource) {
        subsamplingImageView.setImage(imageSource)
    }
}