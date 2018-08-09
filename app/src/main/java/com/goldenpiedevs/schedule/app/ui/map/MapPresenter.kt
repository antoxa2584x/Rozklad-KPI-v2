package com.goldenpiedevs.schedule.app.ui.map

import com.goldenpiedevs.schedule.app.ui.base.BasePresenter

interface MapPresenter : BasePresenter<MapView> {
    fun loadMap()
}