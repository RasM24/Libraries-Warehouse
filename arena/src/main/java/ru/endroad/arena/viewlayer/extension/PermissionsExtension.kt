package ru.endroad.arena.viewlayer.extension

import android.Manifest.permission.*
import android.content.Context
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.checkSelfPermission
import androidx.core.content.PermissionChecker.*

//TODO скинул сюда примеры кода. Разобраться
/**
 * return true, если есть хоть одно разрешение
 */

fun Context.isLocationPermission(): Boolean {
	return checkSelfPermission(this, ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
		|| checkSelfPermission(this, ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
}

private const val REQUEST_CODE_PERMISSIONS = 101

private val REQUIRED_PERMISSIONS = arrayOf(ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION)

internal val AppCompatActivity.allPermissionsGranted: Boolean
	get() {
		REQUIRED_PERMISSIONS.forEach { if (!it.permissionGranted(this)) return false }
		return true
	}

internal fun AppCompatActivity.requestPermissions() {
	ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)
}

private fun String.permissionGranted(context: Context): Boolean =
	checkSelfPermission(context, this) == PERMISSION_GRANTED

///**
// * Проверка, включена ли передача данных/Wifi
// */
//fun isConnectedToNetwork(context: Context): Boolean {
//	val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//	val activeNetwork = connectivityManager.activeNetworkInfo
//
//	return activeNetwork != null && activeNetwork.isConnected
//}