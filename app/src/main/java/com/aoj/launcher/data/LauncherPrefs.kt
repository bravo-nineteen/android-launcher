package com.aoj.launcher.data

import android.content.Context

class LauncherPrefs(context: Context) {

    private val prefs = context.getSharedPreferences("aoj_launcher_prefs", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_PINNED = "pinned_packages"
        private const val KEY_HIDDEN = "hidden_packages"
        private const val KEY_SHOW_WATERMARK = "show_watermark"
        private const val KEY_SHOW_BACKGROUND_IMAGE = "show_background_image"
        private const val KEY_ADMIN_MODE = "admin_mode"
    }

    fun getPinnedPackages(): Set<String> {
        return prefs.getStringSet(KEY_PINNED, emptySet()) ?: emptySet()
    }

    fun setPinnedPackages(packages: Set<String>) {
        prefs.edit().putStringSet(KEY_PINNED, packages).apply()
    }

    fun getHiddenPackages(): Set<String> {
        return prefs.getStringSet(KEY_HIDDEN, emptySet()) ?: emptySet()
    }

    fun setHiddenPackages(packages: Set<String>) {
        prefs.edit().putStringSet(KEY_HIDDEN, packages).apply()
    }

    fun isWatermarkEnabled(): Boolean {
        return prefs.getBoolean(KEY_SHOW_WATERMARK, true)
    }

    fun setWatermarkEnabled(enabled: Boolean) {
        prefs.edit().putBoolean(KEY_SHOW_WATERMARK, enabled).apply()
    }

    fun isBackgroundImageEnabled(): Boolean {
        return prefs.getBoolean(KEY_SHOW_BACKGROUND_IMAGE, true)
    }

    fun setBackgroundImageEnabled(enabled: Boolean) {
        prefs.edit().putBoolean(KEY_SHOW_BACKGROUND_IMAGE, enabled).apply()
    }

    fun isAdminModeEnabled(): Boolean {
        return prefs.getBoolean(KEY_ADMIN_MODE, false)
    }

    fun setAdminModeEnabled(enabled: Boolean) {
        prefs.edit().putBoolean(KEY_ADMIN_MODE, enabled).apply()
    }
}
