package com.vicemaps.app

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.vicemaps.app.location.DeviceLocationManager
import com.vicemaps.app.map.ViceMapEngine
import com.vicemaps.app.routing.OsrmClient
import com.vicemaps.app.routing.RouteResult
import com.vicemaps.app.ui.components.RouteHudCard
import com.vicemaps.app.ui.components.TopHudBar
import com.vicemaps.app.ui.theme.BgDeep
import com.vicemaps.app.ui.theme.ViceMapsTheme
import kotlinx.coroutines.launch
import org.maplibre.android.MapLibre
import org.maplibre.android.maps.MapView

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Initialize MapLibre Native SDK
        MapLibre.getInstance(this)

        setContent {
            ViceMapsTheme {
                ViceMapsMainScreen()
            }
        }
    }
}

@Composable
fun ViceMapsMainScreen() {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var searchQuery by remember { mutableStateOf("") }
    var activeRoute by remember { mutableStateOf<RouteResult?>(null) }
    var destinationName by remember { mutableStateOf("") }

    val osrmClient = remember { OsrmClient() }
    val locationManager = remember { DeviceLocationManager(context) }
    var viceMapEngine by remember { mutableStateOf<ViceMapEngine?>(null) }

    // Runtime Permission Launcher for Real-Time Device GPS
    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val granted = permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                      permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true
        if (granted) {
            scope.launch {
                locationManager.getLocationUpdates().collect { loc ->
                    viceMapEngine?.flyTo(loc.latitude, loc.longitude, 16.5, 68.0, 38.0)
                }
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize().background(BgDeep)) {
        // Native MapView embedded in Jetpack Compose
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { ctx ->
                MapView(ctx).apply {
                    val engine = ViceMapEngine(ctx, this)
                    engine.initialize {
                        viceMapEngine = engine
                    }
                }
            }
        )

        // Top HUD Bar with Search and Real-Time GPS Locate
        TopHudBar(
            searchQuery = searchQuery,
            onQueryChange = { query ->
                searchQuery = query
                // Worldwide geocoder trigger
            },
            onLocateMeClick = {
                val hasFine = ContextCompat.checkSelfPermission(
                    context, Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
                if (hasFine) {
                    scope.launch {
                        locationManager.getLocationUpdates().collect { loc ->
                            viceMapEngine?.flyTo(loc.latitude, loc.longitude, 16.5, 68.0, 38.0)
                        }
                    }
                } else {
                    locationPermissionLauncher.launch(
                        arrayOf(
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        )
                    )
                }
            },
            modifier = Modifier.align(Alignment.TopCenter)
        )

        // Route HUD Card (when active navigation is on)
        activeRoute?.let { route ->
            RouteHudCard(
                destinationName = destinationName,
                distanceDisplay = route.distanceDisplay,
                durationDisplay = route.durationDisplay,
                onCloseClick = { activeRoute = null },
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }
    }
}
