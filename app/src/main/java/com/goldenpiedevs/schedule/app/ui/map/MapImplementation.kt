package com.goldenpiedevs.schedule.app.ui.map

import com.davemorrissey.labs.subscaleview.ImageSource
import com.goldenpiedevs.schedule.app.R
import com.goldenpiedevs.schedule.app.ui.base.BasePresenterImpl

class MapImplementation : BasePresenterImpl<MapView>(), MapPresenter {

    override fun loadMap() {
        view.showMap(ImageSource.resource(R.drawable.map))
    }
}