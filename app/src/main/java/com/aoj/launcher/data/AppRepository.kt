package com.aoj.launcher.data

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import com.aoj.launcher.model.AppInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AppRepository(
    private val context: Context
) {

    suspend fun loadLaunchableApps(
        hiddenPackages: Set<String> = emptySet(),
        pinnedPackages: Set<String> = emptySet()
    ): List<AppInfo> = withContext(Dispatchers.IO) {
        val packageManager = context.packageManager

        val intent = Intent(Intent.ACTION_MAIN).apply {
            addCategory(Intent.CATEGORY_LAUNCHER)
        }

        val resolveInfos = packageManager.queryIntentActivities(
            intent,
            PackageManager.MATCH_ALL
        )

        val apps = resolveInfos
            .mapNotNull { resolveInfo ->
                val activityInfo = resolveInfo.activityInfo ?: return@mapNotNull null
                val label = resolveInfo.loadLabel(packageManager)?.toString()?.trim().orEmpty()
                val packageName = activityInfo.packageName.orEmpty()
                val icon = try {
                    resolveInfo.loadIcon(packageManager)
                } catch (_: Exception) {
                    null
                }

                if (
                    label.isBlank() ||
                    packageName == context.packageName ||
                    hiddenPackages.contains(packageName)
                ) {
                    null
                } else {
                    AppInfo(
                        label = label,
                        packageName = packageName,
                        icon = icon
                    )
                }
            }
            .distinctBy { it.packageName }

        val pinned = apps
            .filter { pinnedPackages.contains(it.packageName) }
            .sortedBy { it.label.lowercase() }

        val normal = apps
            .filterNot { pinnedPackages.contains(it.packageName) }
            .sortedBy { it.label.lowercase() }

        pinned + normal
    }
}
