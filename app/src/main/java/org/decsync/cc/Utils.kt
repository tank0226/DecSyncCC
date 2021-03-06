package org.decsync.cc

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.util.Log
import android.widget.Toast
import at.bitfire.ical4android.TaskProvider

object Utils {
    private const val TAG = "Utils"

    val TASK_PROVIDERS = listOf(
            TaskProvider.ProviderName.OpenTasks,
            TaskProvider.ProviderName.TasksOrg
    )
    val TASK_PROVIDER_NAMES = listOf(
            R.string.tasks_app_opentasks,
            R.string.tasks_app_tasks_org
    )

    fun appInstalled(activity: Activity, packageName: String): Boolean {
        return try {
            activity.packageManager.getPackageInfo(packageName, 0)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }

    fun installApp(activity: Activity, packageName: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse("market://details?id=$packageName")
        if (intent.resolveActivity(activity.packageManager) == null) {
            Toast.makeText(activity, R.string.no_app_store, Toast.LENGTH_SHORT).show()
            return
        }
        activity.startActivity(intent)
    }

    fun parseColor(decsyncColor: String): Int? {
        return try {
            Color.parseColor(decsyncColor)
        } catch (e: IllegalArgumentException) {
            Log.w(TAG, "Unknown color $decsyncColor", e)
            null
        }
    }

    fun colorToString(color: Int): String {
        return String.format("#%06X", color and 0xFFFFFF)
    }
}