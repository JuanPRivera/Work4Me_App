package com.example.work4me_app

import android.Manifest
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.QuerySnapshot
import com.mapbox.geojson.Point
import com.mapbox.maps.MapView
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.location.LocationManagerCompat.requestLocationUpdates
import com.mapbox.api.directions.v5.DirectionsCriteria
import com.mapbox.api.directions.v5.models.DirectionsRoute
import com.mapbox.api.directions.v5.models.RouteOptions
import com.mapbox.maps.Style
import com.mapbox.maps.StyleManagerInterface
import com.mapbox.maps.dsl.cameraOptions
import com.mapbox.maps.plugin.LocationPuck2D
import com.mapbox.maps.plugin.animation.camera
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.locationcomponent.location
import com.mapbox.navigation.base.extensions.applyDefaultNavigationOptions
import com.mapbox.navigation.base.options.NavigationOptions
import com.mapbox.navigation.base.route.RouterCallback
import com.mapbox.navigation.base.route.RouterFailure
import com.mapbox.navigation.base.route.RouterOrigin
import com.mapbox.navigation.core.MapboxNavigation
import com.mapbox.navigation.core.MapboxNavigationProvider
import com.mapbox.navigation.core.trip.session.LocationMatcherResult
import com.mapbox.navigation.core.trip.session.LocationObserver
import com.mapbox.navigation.ui.maps.location.NavigationLocationProvider
import com.mapbox.navigation.ui.maps.route.line.api.MapboxRouteLineApi
import com.mapbox.navigation.ui.maps.route.line.api.MapboxRouteLineView
import com.mapbox.navigation.ui.maps.route.line.model.MapboxRouteLineOptions
import com.mapbox.navigation.ui.maps.route.line.model.RouteLine
import com.mapbox.navigation.core.directions.session.RoutesObserver
import com.mapbox.navigation.core.directions.session.RoutesUpdatedResult
import com.mapbox.navigation.core.reroute.RerouteController
import java.lang.ref.WeakReference

class MapActivity : AppCompatActivity() {

    private lateinit var mapView : MapView

    private lateinit var mapboxNavigation : MapboxNavigation

    private val navigationLocationProvider = NavigationLocationProvider()

    private lateinit var locationObserver : LocationObserver




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        mapView = findViewById(R.id.mapView)

        if (ContextCompat.checkSelfPermission( this,android.Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED )
        {
            ActivityCompat.requestPermissions(
                this,
                arrayOf( android.Manifest.permission.ACCESS_COARSE_LOCATION),
                1
            )
        }else{
            Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
            generateRoute()
        }





    }

    override fun onStart() {
        super.onStart()
        mapView?.onStart()
    }

    override fun onStop() {
        super.onStop()
        mapboxNavigation.unregisterLocationObserver(locationObserver)
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

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            1 -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED) {
                    if ((ContextCompat.checkSelfPermission(this@MapActivity,
                            Manifest.permission.ACCESS_FINE_LOCATION) ===
                                PackageManager.PERMISSION_GRANTED)) {
                        Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
                        generateRoute()
                    }
                } else {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
                }
                return
            }
        }
    }

    @SuppressLint("MissingPermission")
    fun generateRoute() {
        FirebaseFirestore
            .getInstance()
            .collection("users")
            .whereEqualTo("uid", intent.getStringExtra("companyUid"))
            .get()
            .addOnSuccessListener { querySnapshot : QuerySnapshot ->
                if (querySnapshot.size() > 0){
                    val documentSnapshot : DocumentSnapshot = querySnapshot.documents[0]
                    val geoPoint : GeoPoint = documentSnapshot["ubication"] as GeoPoint

                    mapView.getMapboxMap().loadStyleUri(Style.MAPBOX_STREETS) { style ->
                        val routeLineOptions = MapboxRouteLineOptions.Builder(this).build()
                        val routeLineApi = MapboxRouteLineApi(routeLineOptions)
                        val routeLineView = MapboxRouteLineView(routeLineOptions)


                        locationObserver = object : LocationObserver {

                            override fun onNewRawLocation(rawLocation: Location) {

                                mapView.camera.flyTo(cameraOptions {
                                    center(Point.fromLngLat(geoPoint.latitude, geoPoint.longitude))
                                    zoom(15.5)
                                })

                                val myOrigin: Point = Point.fromLngLat(rawLocation.longitude, rawLocation.latitude)
                                val myDestination: Point = Point.fromLngLat(geoPoint.latitude, geoPoint.longitude)


                                mapboxNavigation.requestRoutes(
                                    RouteOptions.builder()
                                        .applyDefaultNavigationOptions()
                                        .coordinatesList(listOf(myOrigin, myDestination))
                                        .profile(DirectionsCriteria.PROFILE_DRIVING)
                                        .alternatives(false)
                                        .build(), object : RouterCallback {
                                        override fun onCanceled(
                                            routeOptions: RouteOptions,
                                            routerOrigin: RouterOrigin
                                        ) {

                                        }

                                        override fun onFailure(
                                            reasons: List<RouterFailure>,
                                            routeOptions: RouteOptions
                                        ) {

                                        }

                                        override fun onRoutesReady(
                                            routes: List<DirectionsRoute>,
                                            routerOrigin: RouterOrigin
                                        ) {

                                            val routeLines = routes.map { RouteLine(it, null) }

                                            routeLineApi.setRoutes(routeLines) { value ->
                                                routeLineView.renderRouteDrawData(
                                                    style,
                                                    value
                                                )
                                            }
                                        }

                                    }
                                )


                                mapView.location.apply {
                                    locationPuck = LocationPuck2D(
                                        bearingImage = ContextCompat.getDrawable(
                                            this@MapActivity,
                                            com.mapbox.maps.plugin.locationcomponent.R.drawable.mapbox_user_puck_icon
                                        )
                                    )
                                    setLocationProvider(navigationLocationProvider)
                                    enabled = true
                                }
                            }

                            override fun onNewLocationMatcherResult(locationMatcherResult: LocationMatcherResult) {
                                val transitionOptions: (ValueAnimator.() -> Unit) =
                                    {
                                        duration = 1000
                                    }

                                navigationLocationProvider.changePosition(
                                    location = locationMatcherResult.enhancedLocation,
                                    keyPoints = locationMatcherResult.keyPoints,
                                    latLngTransitionOptions = transitionOptions,
                                    bearingTransitionOptions = transitionOptions
                                )


                            }
                        }

                        val navigationOptions: NavigationOptions.Builder =
                            NavigationOptions.Builder(applicationContext)
                                .accessToken(getString(R.string.mapbox_access_token))

                        mapboxNavigation = MapboxNavigationProvider.create(navigationOptions.build())

                        mapboxNavigation.apply {
                            startTripSession()
                            registerLocationObserver(locationObserver)
                        }


                    }

                }
            }
    }
}
