package com.aoj.launcher
                    showWatermark = showWatermark,
                    showBackgroundImage = showBackgroundImage,
                    showSettings = showSettings,
                    onRefresh = {
                        val currentPinned = pinnedPackages
                        val currentHidden = hiddenPackages
                        apps = emptyList()
                        android.os.Handler(mainLooper).post {
                            // Trigger normal refresh path on UI thread.
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
