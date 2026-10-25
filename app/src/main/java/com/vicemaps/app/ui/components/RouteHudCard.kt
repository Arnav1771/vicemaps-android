package com.vicemaps.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vicemaps.app.ui.theme.*

@Composable
fun RouteHudCard(
    destinationName: String,
    distanceDisplay: String,
    durationDisplay: String,
    onCloseClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clip(RoundedCornerShape(14.dp))
            .border(1.dp, BorderCyan, RoundedCornerShape(14.dp)),
        color = BgHud
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "GPS WAYPOINT ACTIVE",
                    fontSize = 11.sp,
                    color = NeonCyan,
                    style = MaterialTheme.typography.labelSmall
                )
                IconButton(onClick = onCloseClick, modifier = Modifier.size(24.dp)) {
                    Icon(Icons.Default.Close, contentDescription = "Close", tint = TextMuted)
                }
            }

            Text(
                text = "Destination: $destinationName",
                fontSize = 15.sp,
                color = TextPrimary,
                style = MaterialTheme.typography.titleMedium
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(BgDeep)
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text("DISTANCE", fontSize = 10.sp, color = TextMuted)
                    Text(distanceDisplay, fontSize = 18.sp, color = NeonCyan)
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text("EST. DRIVE", fontSize = 10.sp, color = TextMuted)
                    Text(durationDisplay, fontSize = 18.sp, color = NeonMagenta)
                }
            }
        }
    }
}
