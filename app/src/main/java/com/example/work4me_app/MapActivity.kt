package com.example.work4me_app

import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.QuerySnapshot
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.Style
import com.mapbox.maps.plugin.animation.flyTo
import com.mapbox.maps.plugin.annotation.*
import com.mapbox.maps.plugin.annotation.generated.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.appcompat.content.res.AppCompatResources


class MapActivity : AppCompatActivity() {

    private lateinit var mapView : MapView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        mapView = findViewById(R.id.mapView)

        FirebaseFirestore
            .getInstance()
            .collection("users")
            .whereEqualTo("uid", "3S2fXe7gwXTfMRnwcRiIEDz1c1h2")
            .get()
            .addOnSuccessListener { documents : QuerySnapshot ->
                if(!documents.isEmpty){
                    val document : DocumentSnapshot = documents.elementAt(0)

                    val geoPoint : GeoPoint = document["ubication"] as GeoPoint

                    mapView.getMapboxMap().loadStyleUri(Style.MAPBOX_STREETS)
                    val cameraOptions : CameraOptions.Builder = CameraOptions.Builder()
                    cameraOptions.center(Point.fromLngLat(geoPoint.latitude, geoPoint.longitude))
                    cameraOptions.zoom(15.0)
                    mapView.getMapboxMap().flyTo(cameraOptions = cameraOptions.build())

                    val annotationPlugin : AnnotationPlugin = mapView.annotations
                    val circleManager : PointAnnotationManager = annotationPlugin.createPointAnnotationManager(mapView)
                    val circleOptions: PointAnnotationOptions = PointAnnotationOptions()
                        .withPoint(Point.fromLngLat(geoPoint.latitude, geoPoint.longitude))
                        .withIconImage(convertDrawableToBitmap(AppCompatResources.getDrawable(this, R.drawable.ic_placeholder))!!)
                        .withIconSize(.3)
                    circleManager.create(circleOptions)
                }
            }

    }

    override fun onStart() {
        super.onStart()
        mapView?.onStart()
    }

    override fun onStop() {
        super.onStop()
        mapView?.onStop()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView?.onLowMemory()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView?.onDestroy()
    }

    private fun convertDrawableToBitmap(sourceDrawable: Drawable?): Bitmap? {
        if (sourceDrawable == null) {
            return null
        }
        return if (sourceDrawable is BitmapDrawable) {
            sourceDrawable.bitmap
        } else {
// copying drawable object to not manipulate on the same reference
            val constantState = sourceDrawable.constantState ?: return null
            val drawable = constantState.newDrawable().mutate()
            val bitmap: Bitmap = Bitmap.createBitmap(
                drawable.intrinsicWidth, drawable.intrinsicHeight,
                Bitmap.Config.ARGB_8888
            )
            val canvas = Canvas(bitmap)
            drawable.setBounds(0, 0, canvas.width, canvas.height)
            drawable.draw(canvas)
            bitmap
        }
    }
}