package com.mateuszcholyn.wallet.util

import android.app.Activity
import android.content.Context
import android.os.Environment
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import com.mateuszcholyn.wallet.config.ApplicationContext
import java.io.File


fun enableGivenTheme(resolver: Resolver) {
    if (Environment.MEDIA_MOUNTED != Environment.getExternalStorageState()) {
        return
    }

    verifyStoragePermissions(ApplicationContext.appActivity)

    val ctx = ApplicationContext.appContext

    try {
        ctx
                .darkModeFilePath()
                .toFile()
                .delete()
        ctx
                .darkModeFilePath()
                .toFile()
                .createNewIfNotExists()
                .writeText(resolver.name)

    } catch (t: Throwable) {
        t.printStackTrace()
    }
}


enum class Resolver {
    SYSTEM,
    DARK,
    LIGHT,
}

data class ThemeProperties(
        val colors: Colors,
        val shouldUseDarkTheme: Boolean,
        val resolver: Resolver,
)


@Suppress("unused")
@Composable
fun resolveTheme(ctx: Context, activity: Activity): ThemeProperties {
    if (Environment.MEDIA_MOUNTED != Environment.getExternalStorageState()) {
        return lightThemeProperties(Resolver.SYSTEM)
    }

    verifyStoragePermissions(activity)

    return runCatching {
        when {
            darkModeFileNotExists(ctx) -> lightThemeProperties(Resolver.SYSTEM)
            darkModeIsEnabled(ctx) -> darkThemeProperties(Resolver.LIGHT)
            lightModeIsEnabled(ctx) -> lightThemeProperties(Resolver.LIGHT)
            useSystemTheme(ctx) -> resolveSystemTheme()
            else -> lightThemeProperties(Resolver.SYSTEM)
        }
    }
            .getOrDefault(lightThemeProperties(Resolver.SYSTEM))
}

private fun lightThemeProperties(resolver: Resolver): ThemeProperties =
        ThemeProperties(
                colors = lightColors(),
                shouldUseDarkTheme = false,
                resolver = resolver,
        )

private fun darkThemeProperties(resolver: Resolver): ThemeProperties =
        ThemeProperties(
                colors = darkColors(),
                shouldUseDarkTheme = true,
                resolver = resolver,
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
                .readText() == Resolver.DARK.name


private fun lightModeIsEnabled(ctx: Context): Boolean =
        ctx
                .darkModeFilePath()
                .toFile()
                .readText() == Resolver.LIGHT.name

private fun useSystemTheme(ctx: Context): Boolean =
        ctx
                .darkModeFilePath()
                .toFile()
                .readText() == Resolver.SYSTEM.name


@Composable
private fun resolveSystemTheme(): ThemeProperties =
        when (isSystemInDarkTheme()) {
            true -> darkThemeProperties(Resolver.SYSTEM)
            false -> lightThemeProperties(Resolver.SYSTEM)
        }

private fun Context.darkModeFilePath(): String {
    val appPath = this.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)!!.toString()
    return "$appPath${File.separator}DARK_MODE.txt"
}
