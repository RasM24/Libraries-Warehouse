package ru.endroad.libraries.vkontakte
//TODO Добавить sample для запуска и проверки модуля

import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import com.vk.sdk.VKScope
import com.vk.sdk.VKSdk
import com.vk.sdk.VKServiceActivity
import com.vk.sdk.VKServiceActivity.VKServiceType
import java.util.*

/**
 * Сделал работу за разработчиков VK SDK
 *
 * Данная функция позволяет запускать vk login из androidx фрагмента
 */
private const val KEY_TYPE = "arg1"
private const val KEY_SCOPE_LIST = "arg2"
private const val KEY_SDK_CUSTOM_INITIALIZE = "arg4"

fun Fragment.startVkLoginActivity(vararg scope: String) =
	startActivityForResult(
		requireContext().vkServiceIntent.apply { putStringArrayListExtra(KEY_SCOPE_LIST, preparingScopeList(*scope)) },
		VKServiceType.Authorization.outerCode
	)

private val Context.vkServiceIntent
	get() = Intent(this, VKServiceActivity::class.java).apply {
		putExtra(KEY_TYPE, VKServiceType.Authorization.name)
		putExtra(KEY_SDK_CUSTOM_INITIALIZE, VKSdk.isCustomInitialize())
	}

private fun preparingScopeList(vararg scope: String): ArrayList<String> =
	arrayListOf(*scope)
		.apply { if (!contains(VKScope.OFFLINE)) add(VKScope.OFFLINE) }