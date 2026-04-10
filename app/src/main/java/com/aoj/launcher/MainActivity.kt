package com.aoj.launcher

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.aoj.launcher.data.AppRepository
import com.aoj.launcher.model.AppInfo
import com.aoj.launcher.ui.screens.HomeScreen
import com.aoj.launcher.ui.theme.AOJLauncherTheme

class MainActivity : ComponentActivity() {

    private lateinit var repository: AppRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        repository = AppRepository(this)

        setContent {
            AOJLauncherTheme {
                var apps by remember { mutableStateOf<List<AppInfo>>(emptyList()) }

                LaunchedEffect(Unit) {
                    apps = repository.loadLaunchableApps()
                }

                HomeScreen(
                    apps = apps,
                    onRefresh = {
                        apps = repository.loadLaunchableApps()
                    },
                    onLaunchApp = { app ->
                        launchExternalApp(app.packageName)
                    }
                )
            }
        }
    }

    private fun launchExternalApp(packageName: String) {
        val launchIntent: Intent? = packageManager.getLaunchIntentForPackage(packageName)
        launchIntent?.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        if (launchIntent != null) {
            startActivity(launchIntent)
        }
    }
}
