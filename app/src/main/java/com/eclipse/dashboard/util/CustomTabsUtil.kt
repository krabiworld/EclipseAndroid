package com.eclipse.dashboard.util

import android.content.Context
import android.graphics.Color
import android.net.Uri
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent

private val colorScheme = CustomTabColorSchemeParams.Builder()
    .setToolbarColor(Color.parseColor("#7762D2"))
    .build()

private val customTabs = CustomTabsIntent.Builder()
    .setShowTitle(true)
    .setDefaultColorSchemeParams(colorScheme)
    .build()

fun launchUri(context: Context, uri: Uri) {
    customTabs.launchUrl(context, uri)
}

fun launchUri(context: Context, uriString: String) {
    launchUri(context, Uri.parse(uriString))
}
