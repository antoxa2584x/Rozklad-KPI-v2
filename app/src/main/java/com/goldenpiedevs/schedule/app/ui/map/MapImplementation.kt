package com.goldenpiedevs.schedule.app.ui.map

import com.goldenpiedevs.schedule.app.ui.base.BasePresenterImpl

class MapImplementation : BasePresenterImpl<MapView>(), MapPresenter {

    override fun loadMap() {
        view.showMap()
    }
}