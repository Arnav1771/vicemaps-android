package com.vicemaps.app.routing

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject

data class RouteResult(
    val distanceMeters: Double,
    val durationSeconds: Double,
    val distanceDisplay: String,
    val durationDisplay: String,
    val coordinates: List<Pair<Double, Double>> // Lng, Lat
)

class OsrmClient {

    private val httpClient = OkHttpClient()

    suspend fun calculateRoute(
        startLng: Double,
        startLat: Double,
        destLng: Double,
        destLat: Double
    ): RouteResult? = withContext(Dispatchers.IO) {
        val url = "https://router.project-osrm.org/route/v1/driving/$startLng,$startLat;$destLng,$destLat?overview=full&geometries=geojson"
        val request = Request.Builder().url(url).build()

        try {
            httpClient.newCall(request).execute().use { response ->
                if (!response.isSuccessful) return@withContext null
                val body = response.body?.string() ?: return@withContext null
                val json = JSONObject(body)

                val routes = json.optJSONArray("routes") ?: return@withContext null
                if (routes.length() == 0) return@withContext null

                val routeObj = routes.getJSONObject(0)
                val dist = routeObj.optDouble("distance", 0.0)
                val dur = routeObj.optDouble("duration", 0.0)

                val geom = routeObj.optJSONObject("geometry")
                val coordsArray = geom?.optJSONArray("coordinates")
                val coordsList = mutableListOf<Pair<Double, Double>>()

                if (coordsArray != null) {
                    for (i in 0 until coordsArray.length()) {
                        val pt = coordsArray.getJSONArray(i)
                        coordsList.add(Pair(pt.getDouble(0), pt.getDouble(1)))
                    }
                }

                val miles = (dist / 1609.34)
                val mins = (dur / 60.0).toInt()

                RouteResult(
                    distanceMeters = dist,
                    durationSeconds = dur,
                    distanceDisplay = "%.1f MI".format(miles),
                    durationDisplay = "$mins MIN",
                    coordinates = coordsList
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
