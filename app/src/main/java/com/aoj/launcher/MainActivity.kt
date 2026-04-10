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
import androidx.lifecycle.lifecycleScope
import com.aoj.launcher.data.AppRepository
import com.aoj.launcher.data.LauncherPrefs
import com.aoj.launcher.model.AppInfo
import com.aoj.launcher.ui.screens.HomeScreen
import com.aoj.launcher.ui.theme.AOJLauncherTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private lateinit var repository: AppRepository
    private lateinit var prefs: LauncherPrefs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        repository = AppRepository(this)
        prefs = LauncherPrefs(this)

        setContent {
            AOJLauncherTheme {
                var apps by remember { mutableStateOf<List<AppInfo>>(emptyList()) }
                var pinnedPackages by remember { mutableStateOf(prefs.getPinnedPackages()) }
                var hiddenPackages by remember { mutableStateOf(prefs.getHiddenPackages()) }
                var adminMode by remember { mutableStateOf(prefs.isAdminModeEnabled()) }
                var showWatermark by remember { mutableStateOf(prefs.isWatermarkEnabled()) }
                var showBackgroundImage by remember { mutableStateOf(prefs.isBackgroundImageEnabled()) }
                var showSettings by remember { mutableStateOf(false) }

                LaunchedEffect(pinnedPackages, hiddenPackages) {
                    apps = repository.loadLaunchableApps(
                        hiddenPackages = hiddenPackages,
                        pinnedPackages = pinnedPackages
                    )
                }

                HomeScreen(
                    apps = apps,
                    adminMode = adminMode,
                    showWatermark = showWatermark,
                    showBackgroundImage = showBackgroundImage,
                    showSettings = showSettings,
                    onRefresh = {
                        lifecycleScope.launch {
                            apps = repository.loadLaunchableApps(
                                hiddenPackages = hiddenPackages,
                                pinnedPackages = pinnedPackages
                            )
                        }
                    },
                    onLaunchApp = { app ->
                        launchExternalApp(app.packageName)
                    },
                    onToggleSettings = {
                        showSettings = !showSettings
                    },
                    onSetAdminMode = { enabled ->
                        adminMode = enabled
                        prefs.setAdminModeEnabled(enabled)
                    },
                    onSetWatermark = { enabled ->
                        showWatermark = enabled
                        prefs.setWatermarkEnabled(enabled)
                    },
                    onSetBackgroundImage = { enabled ->
                        showBackgroundImage = enabled
                        prefs.setBackgroundImageEnabled(enabled)
                    },
                    onTogglePinned = { packageName ->
                        pinnedPackages = if (pinnedPackages.contains(packageName)) {
                            pinnedPackages - packageName
                        } else {
                            pinnedPackages + packageName
                        }
                        prefs.setPinnedPackages(pinnedPackages)
                    },
                    onToggleHidden = { packageName ->
                        hiddenPackages = if (hiddenPackages.contains(packageName)) {
                            hiddenPackages - packageName
                        } else {
                            hiddenPackages + packageName
                        }
                        prefs.setHiddenPackages(hiddenPackages)
                    },
                    pinnedPackages = pinnedPackages,
                    hiddenPackages = hiddenPackages
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
