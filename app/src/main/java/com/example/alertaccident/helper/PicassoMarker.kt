package com.example.alertaccident.helper

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.Marker
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import java.lang.IllegalArgumentException

class PicassoMarker :Target {
    var mMarker: Marker? = null

//   fun PicassoMarker(marker: Marker) {
//        mMarker = marker
//    }
    constructor(marker: Marker){
    mMarker=marker
}

    override fun equals(o: Any?): Boolean {
        if (o is PicassoMarker) {
            val marker = o.mMarker
            return mMarker!!.equals(marker)
        } else {
            return false
        }
    }

    override fun hashCode(): Int {
        return mMarker.hashCode()
    }

    override fun onPrepareLoad(placeHolderDrawable: Drawable?) {

    }

    override fun onBitmapFailed(errorDrawable: Drawable?) {
    }

    override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
       try{
           mMarker!!.setIcon(BitmapDescriptorFactory.fromBitmap(bitmap))
       }
       catch (exception:IllegalArgumentException){

       }
    }
}