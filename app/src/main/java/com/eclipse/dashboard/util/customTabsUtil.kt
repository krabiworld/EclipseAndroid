package com.eclipse.dashboard.util

import android.content.Context
import android.net.Uri
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import com.eclipse.dashboard.R

private val colorSchemeBuilder = CustomTabColorSchemeParams.Builder()

private val customTabsBuilder = CustomTabsIntent.Builder()
    .setShowTitle(true)
    .setUrlBarHidingEnabled(false)
    .setShareState(CustomTabsIntent.SHARE_STATE_OFF)

fun launchUri(context: Context?, uri: Uri) {
    colorSchemeBuilder.setToolbarColor(context!!.getColor(R.color.eclipse_primary))
    val colorScheme = colorSchemeBuilder.build()

    customTabsBuilder.setDefaultColorSchemeParams(colorScheme)
    val customTabs = customTabsBuilder.build()

    customTabs.launchUrl(context, uri)
}

fun launchUri(context: Context?, uri: String) {
    launchUri(context, Uri.parse(uri))
}
