package com.aoj.launcher.ui.screens

import android.content.Intent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.drawLine
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HomeScreen(
    onLaunchApp: (String) -> Unit
) {
    val context = LocalContext.current
    val pm = context.packageManager

    var apps by remember { mutableStateOf(listOf<Pair<String, String>>()) }

    LaunchedEffect(Unit) {
        val intent = Intent(Intent.ACTION_MAIN).apply {
            addCategory(Intent.CATEGORY_LAUNCHER)
        }

        val resolved = pm.queryIntentActivities(intent, 0)

        apps = resolved.map {
            val label = it.loadLabel(pm).toString()
            val pkg = it.activityInfo.packageName
            label to pkg
        }.sortedBy { it.first }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1A1A1A))
    ) {

        // Background grid lines (tactical feel)
        Canvas(modifier = Modifier.fillMaxSize()) {
            val step = 80f
            for (x in 0..size.width.toInt() step step.toInt()) {
                drawLine(
                    color = Color(0xFF2A2A2A),
                    start = androidx.compose.ui.geometry.Offset(x.toFloat(), 0f),
                    end = androidx.compose.ui.geometry.Offset(x.toFloat(), size.height),
                    strokeWidth = 1f
                )
            }
            for (y in 0..size.height.toInt() step step.toInt()) {
                drawLine(
                    color = Color(0xFF2A2A2A),
                    start = androidx.compose.ui.geometry.Offset(0f, y.toFloat()),
                    end = androidx.compose.ui.geometry.Offset(size.width, y.toFloat()),
                    strokeWidth = 1f
                )
            }
        }

        Column(modifier = Modifier.fillMaxSize()) {

            // Header (system feel)
            Text(
                text = "AOJ CONTROL SYSTEM",
                color = Color(0xFF00FF9C),
                fontSize = 18.sp,
                modifier = Modifier.padding(12.dp)
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier.fillMaxSize().padding(8.dp)
            ) {
                items(apps) { app ->

                    Box(
                        modifier = Modifier
                            .padding(6.dp)
                            .background(Color(0xFF111111))
                            .clickable { onLaunchApp(app.second) }
                            .padding(10.dp)
                    ) {

                        Column {

                            Text(
                                text = app.first,
                                color = Color.White,
                                fontSize = 14.sp
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            // fake "system data line"
                            Text(
                                text = "ID:${app.second.take(6)}",
                                color = Color(0xFF888888),
                                fontSize = 10.sp
                            )
                        }
                    }
                }
            }
        }
    }
}
