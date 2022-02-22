package com.mateuszcholyn.wallet.util

import android.app.Activity
import android.content.Context
import android.os.Environment
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import java.io.File


data class ThemeProperties(
        val colors: Colors,
        val shouldUseDarkTheme: Boolean,
)


@Suppress("unused")
@Composable
fun resolveTheme(ctx: Context, activity: Activity): ThemeProperties {
    if (Environment.MEDIA_MOUNTED != Environment.getExternalStorageState()) {
        return lightThemeProperties()
    }

    verifyStoragePermissions(activity)

    return runCatching {
        when {
            darkModeFileNotExists(ctx) -> lightThemeProperties()
            darkModeIsEnabled(ctx) -> darkThemeProperties()
            lightModeIsEnabled(ctx) -> lightThemeProperties()
            useSystemTheme(ctx) -> resolveSystemTheme()
            else -> lightThemeProperties()
        }
    }
            .getOrDefault(lightThemeProperties())

}

private fun lightThemeProperties(): ThemeProperties =
        ThemeProperties(
                colors = lightColors(),
                shouldUseDarkTheme = false,
        )

private fun darkThemeProperties(): ThemeProperties =
        ThemeProperties(
                colors = darkColors(),
                shouldUseDarkTheme = true,
        )

private fun darkModeFileNotExists(ctx: Context): Boolean {
    return try {
        !ctx
                .darkModeFilePath()
                .toFile()
                .exists()
    } catch (t: Throwable) {
        true
    }
}

private fun darkModeIsEnabled(ctx: Context): Boolean =
        ctx
                .darkModeFilePath()
                .toFile()
                .readText() == "DARK_MODE"


private fun lightModeIsEnabled(ctx: Context): Boolean =
        ctx
                .darkModeFilePath()
                .toFile()
                .readText() == "DARK_MODE"

private fun useSystemTheme(ctx: Context): Boolean =
        ctx
                .darkModeFilePath()
                .toFile()
                .readText() == "SYSTEM_THEME"


@Composable
private fun resolveSystemTheme(): ThemeProperties =
        when (isSystemInDarkTheme()) {
            true -> darkThemeProperties()
            false -> lightThemeProperties()
        }

private fun Context.darkModeFilePath(): String {
    val appPath = this.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)!!.toString()
    return "$appPath${File.separator}DARK_MODE.txt"
}


// LIGHT_MODE
// DARK_MODE
// SYSTEM_THEME