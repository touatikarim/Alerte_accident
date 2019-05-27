package com.example.alertaccident.helper

import android.content.Context
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.view.DefaultClusterRenderer
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.Cluster
import com.google.maps.android.clustering.ClusterManager
import com.squareup.picasso.Picasso


class CustomClusterRenderer(context: Context,map:GoogleMap,clusterManager: ClusterManager<MarkerClusterItem>):DefaultClusterRenderer<MarkerClusterItem>(context, map, clusterManager) {

    override fun onBeforeClusterItemRendered(item: MarkerClusterItem?, markerOptions: MarkerOptions?) {
        val markerDescriptor=BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)
        markerOptions!!.icon(markerDescriptor).snippet(item!!.title)

    }

    override fun shouldRenderAsCluster(cluster: Cluster<MarkerClusterItem>?): Boolean {
        return cluster!!.size > 2
    }
}