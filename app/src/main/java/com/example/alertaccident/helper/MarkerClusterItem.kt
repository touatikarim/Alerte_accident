package com.example.alertaccident.helper

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem

class MarkerClusterItem :ClusterItem {
    private var latLng:LatLng?=null
    private var title:String?=null

    constructor(latLng: LatLng,title:String) {
        this.latLng=latLng
        this.title=title
    }
    override fun getSnippet(): String {
            return ""
    }

    override fun getTitle(): String? {
        return title
    }

    override fun getPosition(): LatLng? {
        return latLng
    }
}