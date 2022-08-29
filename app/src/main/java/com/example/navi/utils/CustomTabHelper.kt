package com.example.navi.utils

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.net.Uri
import android.text.TextUtils
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.browser.customtabs.CustomTabsService.ACTION_CUSTOM_TABS_CONNECTION
import androidx.core.content.ContextCompat
import com.example.navi.R

object CustomTabHelper {
    private const val CHROME_PACKAGE = "com.android.chrome"

    fun launchUrl(context: Context, url: String?): Boolean {
        val launched: Boolean = try {
            launchNativeApp(context, url)
        } catch (e: Exception) {
            false
        }
        if (!launched) {
            openCustomTab(context, url)
        }
        return false
    }

    private fun openCustomTab(context: Context, url: String?): Boolean {
        val packageName = getPackageNameToUse(context)

        //If we cant find a package name, it means theres no browser that supports
        //Chrome Custom Tabs installed. So, we fallback to the external browser
        if (packageName == null) {
            return try {
                val result = openExternalLink(context, url)
                result
            } catch (e: Exception) {
                false
            }
        } else {
            try {
                val builder = CustomTabsIntent.Builder()
                builder.setToolbarColor(ContextCompat.getColor(context, R.color.black))
                val customTabsIntent: CustomTabsIntent = builder.build()
                customTabsIntent.intent.setPackage(packageName)
                customTabsIntent.launchUrl(context, Uri.parse(url))
            } catch (e: Exception) {
                return openExternalLink(context, url)
            }
        }
        return true
    }

    private fun launchNativeApp(context: Context, url: String?): Boolean {
        val pm = context.packageManager

        // Get all Apps that resolve a generic url
        val browserActivityIntent = Intent().apply {
            action = Intent.ACTION_VIEW
            addCategory(Intent.CATEGORY_BROWSABLE)
            data = Uri.fromParts("http", "", null)
        }
        val genericResolvedList: List<String> = extractPackageNames(pm.queryIntentActivities(browserActivityIntent, 0))

        // Get all apps that resolve the specific Url
        val specializedActivityIntent = Intent().apply {
            action = Intent.ACTION_VIEW
            addCategory(Intent.CATEGORY_BROWSABLE)
            data = Uri.parse(url)
        }
        val resolvedSpecializedList = extractPackageNames(pm.queryIntentActivities(specializedActivityIntent, 0))

        // Keep only the Urls that resolve the specific, but not the generic
        // urls.
        resolvedSpecializedList.removeAll(genericResolvedList)

        // If the list is empty, no native app handlers were found.
        if (resolvedSpecializedList.isEmpty()) {
            return false
        }

        // if found native handlers. Launch the Intent.
        specializedActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(specializedActivityIntent)
        return true
    }

    private fun openExternalLink(context: Context, url: String?): Boolean {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        val resolveInfo =
            context.packageManager.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY)
        context.startActivity(intent)
        if (resolveInfo?.activityInfo != null) {
            context.startActivity(intent)
            return true
        } else {
            Toast.makeText(context, "No Apps available to open link", Toast.LENGTH_SHORT).show()
        }
        return false
    }

    private fun getPackageNameToUse(context: Context): String? {
        var sPackageNameToUse: String? = null
        val pm = context.packageManager
        // Get default VIEW intent handler.
        val activityIntent = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.example.com"))
        val defaultViewHandlerInfo = pm.resolveActivity(activityIntent, 0)
        var defaultViewHandlerPackageName: String? = null
        if (defaultViewHandlerInfo != null) {
            defaultViewHandlerPackageName = defaultViewHandlerInfo.activityInfo.packageName
        }

        // Get all apps that can handle VIEW intents.
        val resolvedActivityList: List<String> =
            extractPackageNames(pm.queryIntentActivities(activityIntent, 0))
        val packagesSupportingCustomTabs: MutableList<String?> = ArrayList()
        for (packageName in resolvedActivityList) {
            val serviceIntent = Intent()
            serviceIntent.action = ACTION_CUSTOM_TABS_CONNECTION
            serviceIntent.setPackage(packageName)
            if (pm.resolveService(serviceIntent, 0) != null) {
                packagesSupportingCustomTabs.add(packageName)
            }
        }

        // Now packagesSupportingCustomTabs contains all apps that can handle both VIEW intents
        // and service calls.
        if (packagesSupportingCustomTabs.isEmpty()) {
            sPackageNameToUse = null
        } else if (packagesSupportingCustomTabs.size == 1) {
            sPackageNameToUse = packagesSupportingCustomTabs[0]
        } else if (!TextUtils.isEmpty(defaultViewHandlerPackageName)
            && packagesSupportingCustomTabs.contains(defaultViewHandlerPackageName)
        ) {
            sPackageNameToUse = defaultViewHandlerPackageName
        } else if (packagesSupportingCustomTabs.contains(CHROME_PACKAGE)) {
            sPackageNameToUse = CHROME_PACKAGE
        }
        return sPackageNameToUse
    }

    private fun extractPackageNames(activityList: List<ResolveInfo>): MutableList<String> {
        val packages: MutableList<String> = ArrayList()
        for (info in activityList) {
            packages.add(info.activityInfo.packageName)
        }
        return packages
    }
}