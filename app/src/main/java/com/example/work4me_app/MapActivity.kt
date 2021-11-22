package com.example.work4me_app

import android.animation.ValueAnimator
import android.content.Context
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
import android.util.Log
import androidx.core.content.ContextCompat
import com.mapbox.api.directions.v5.DirectionsCriteria
import com.mapbox.api.directions.v5.models.DirectionsRoute
import com.mapbox.api.directions.v5.models.RouteOptions
import com.mapbox.maps.Style
import com.mapbox.maps.StyleManagerInterface
import com.mapbox.maps.plugin.LocationPuck2D
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

        mapView.getMapboxMap().loadStyleUri(Style.MAPBOX_STREETS) { style ->
            val routeLineOptions = MapboxRouteLineOptions.Builder(this).build()
            val routeLineApi = MapboxRouteLineApi(routeLineOptions)
            val routeLineView = MapboxRouteLineView(routeLineOptions)

            locationObserver = object : LocationObserver {

                override fun onNewRawLocation(rawLocation: Location) {

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


            val myOrigin: Point = Point.fromLngLat(3.534152801759124, -76.29335403442384)
            val myDestination: Point = Point.fromLngLat(3.522796439875773, -76.30057990550995)

            val navigationOptions: NavigationOptions.Builder =
                NavigationOptions.Builder(applicationContext)
                    .accessToken(getString(R.string.mapbox_access_token))

            mapboxNavigation = MapboxNavigationProvider.create(navigationOptions.build())

            mapboxNavigation.registerLocationObserver(locationObserver)



            mapboxNavigation.requestRoutes(
                RouteOptions.builder()
                    .coordinatesList(listOf(myOrigin, myDestination))
                    .profile(DirectionsCriteria.PROFILE_DRIVING)
                    .steps(true)
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
                            Log.d("RoutePoints", routes[0].distance().toString())
                            routeLineView.renderRouteDrawData(
                                style,
                                value
                            )
                        }
                    }

                }
            )


            mapView.location.apply {
                this.locationPuck = LocationPuck2D(
                    bearingImage = ContextCompat.getDrawable(
                        this@MapActivity,
                        R.drawable.ic_placeholder
                    )
                )
                setLocationProvider(navigationLocationProvider)
                enabled = true
            }
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
}
