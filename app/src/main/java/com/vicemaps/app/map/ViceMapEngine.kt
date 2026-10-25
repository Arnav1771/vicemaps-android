package com.vicemaps.app.map

import android.content.Context
import org.maplibre.android.camera.CameraPosition
import org.maplibre.android.camera.CameraUpdateFactory
import org.maplibre.android.geometry.LatLng
import org.maplibre.android.maps.MapLibreMap
import org.maplibre.android.maps.MapView
import org.maplibre.android.maps.Style
import org.maplibre.android.style.layers.FillExtrusionLayer
import org.maplibre.android.style.layers.PropertyFactory.*
import org.maplibre.android.style.expressions.Expression.*

class ViceMapEngine(
    private val context: Context,
    private val mapView: MapView
) {
    var mapInstance: MapLibreMap? = null
        private set

    // OpenFreeMap Zero-API-Key vector tiles style
    private val styleUrl = "https://tiles.openfreemap.org/styles/dark"

    fun initialize(onReady: (MapLibreMap) -> Unit) {
        mapView.getMapAsync { map ->
            mapInstance = map
            map.setStyle(Style.Builder().fromUri(styleUrl)) { style ->
                setup3DBuildingExtrusions(style)
                // Default Vice City (Miami) downtown camera
                val initialPos = CameraPosition.Builder()
                    .target(LatLng(25.7725, -80.1880))
                    .zoom(15.8)
                    .tilt(65.0)
                    .bearing(38.0)
                    .build()
                map.moveCamera(CameraUpdateFactory.newCameraPosition(initialPos))
                onReady(map)
            }
        }
    }

    private fun setup3DBuildingExtrusions(style: Style) {
        // Native MapLibre 3D Skyscraper Extrusion Layer
        val buildingExtrusion = FillExtrusionLayer("3d-buildings-native", "openmaptiles").apply {
            sourceLayer = "building"
            minZoom = 14f
            setProperties(
                fillExtrusionColor(
                    interpolate(
                        linear(),
                        coalesce(get("render_height"), get("height"), literal(15)),
                        stop(0, rgb(13, 21, 39)),
                        stop(30, rgb(30, 41, 59)),
                        stop(80, rgb(255, 0, 127)),   // GTA 6 Neon magenta
                        stop(160, rgb(0, 240, 255))   // Electric cyan for towers
                    )
                ),
                fillExtrusionHeight(coalesce(get("render_height"), get("height"), literal(20))),
                fillExtrusionBase(coalesce(get("render_min_height"), get("min_height"), literal(0))),
                fillExtrusionOpacity(0.90f)
            )
        }

        // Add 3D layer to native style
        try {
            style.addLayer(buildingExtrusion)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun flyTo(lat: Double, lng: Double, zoom: Double = 16.5, tilt: Double = 65.0, bearing: Double = 38.0) {
        val pos = CameraPosition.Builder()
            .target(LatLng(lat, lng))
            .zoom(zoom)
            .tilt(tilt)
            .bearing(bearing)
            .build()
        mapInstance?.animateCamera(CameraUpdateFactory.newCameraPosition(pos), 2500)
    }
}
