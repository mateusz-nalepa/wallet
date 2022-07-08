package com.mateuszcholyn.wallet.util.darkmode

import android.content.Context
import android.os.Environment
import androidx.compose.material.Colors
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import com.mateuszcholyn.wallet.util.createNewIfNotExists
import com.mateuszcholyn.wallet.util.mediaIsNotMounted
import com.mateuszcholyn.wallet.util.toFile
import java.io.File


fun enableGivenTheme(
        ctx: Context,
        resolver: Resolver,
) {
    if (mediaIsNotMounted()) {
        return
    }

    try {
        ctx
                .darkModeFilePath()
                .toFile()
                .delete()
        if (Resolver.SYSTEM == resolver) {
            // we don't want to create file in this case
            return
        }

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
fun resolveTheme(ctx: Context, isSystemInDarkTheme: Boolean): ThemeProperties {
    if (mediaIsNotMounted()) {
        return lightThemeProperties(Resolver.LIGHT)
    }

    return runCatching {
        when {
            darkModeIsEnabled(ctx) -> darkThemeProperties(Resolver.DARK)
            lightModeIsEnabled(ctx) -> lightThemeProperties(Resolver.LIGHT)
            else -> resolveSystemTheme(isSystemInDarkTheme)
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


private fun darkModeFileExists(ctx: Context): Boolean {
    return !darkModeFileNotExists(ctx)
}

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
        darkModeFileExists(ctx) &&
                ctx
                        .darkModeFilePath()
                        .toFile()
                        .readText() == Resolver.DARK.name


private fun lightModeIsEnabled(ctx: Context): Boolean =
        darkModeFileExists(ctx) &&
                ctx
                        .darkModeFilePath()
                        .toFile()
                        .readText() == Resolver.LIGHT.name

private fun resolveSystemTheme(isSystemInDarkTheme: Boolean): ThemeProperties =
        when (isSystemInDarkTheme) {
            true -> darkThemeProperties(Resolver.SYSTEM)
            false -> lightThemeProperties(Resolver.SYSTEM)
        }

private fun Context.darkModeFilePath(): String {
    val appPath = this.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)!!.toString()
    return "$appPath${File.separator}DARK_MODE.txt"
}
