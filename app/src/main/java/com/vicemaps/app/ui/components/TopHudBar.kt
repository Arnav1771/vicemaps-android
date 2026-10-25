package com.vicemaps.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vicemaps.app.ui.theme.*

@Composable
fun TopHudBar(
    searchQuery: String,
    onQueryChange: (String) -> Unit,
    onLocateMeClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Brand Chip
        Surface(
            modifier = Modifier.clip(RoundedCornerShape(8.dp)),
            color = BgHud,
            border = androidx.compose.foundation.BorderStroke(1.dp, BorderSubtle)
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = "VICEMAPS",
                    fontSize = 12.sp,
                    color = TextPrimary,
                    style = MaterialTheme.typography.labelMedium
                )
                Text(
                    text = "3D",
                    fontSize = 12.sp,
                    color = NeonCyan,
                    style = MaterialTheme.typography.labelMedium
                )
            }
        }

        // Worldwide Search Box
        TextField(
            value = searchQuery,
            onValueChange = onQueryChange,
            placeholder = {
                Text("Search any city or street worldwide…", fontSize = 13.sp, color = TextMuted)
            },
            leadingIcon = {
                Icon(Icons.Default.Search, contentDescription = "Search", tint = TextMuted)
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = BgHud,
                unfocusedContainerColor = BgHud,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedTextColor = TextPrimary,
                unfocusedTextColor = TextPrimary
            ),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .weight(1f)
                .height(48.dp)
                .border(1.dp, BorderSubtle, RoundedCornerShape(10.dp)),
            singleLine = true
        )

        // Real-Time GPS Locate Me Button
        IconButton(
            onClick = onLocateMeClick,
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(BgHud)
                .border(1.dp, BorderCyan, RoundedCornerShape(10.dp))
        ) {
            Icon(
                Icons.Default.MyLocation,
                contentDescription = "My GPS Location",
                tint = NeonCyan
            )
        }
    }
}
