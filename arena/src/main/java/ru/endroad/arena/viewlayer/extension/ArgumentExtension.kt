package ru.endroad.arena.viewlayer.extension

import android.os.Bundle
import android.os.Parcelable
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import java.io.Serializable

fun Fragment.withArgument(argument: Bundle.() -> Unit): Fragment {
	arguments = Bundle().apply { argument() }
	return this
}

fun DialogFragment.withArgument(argument: Bundle.() -> Unit): DialogFragment {
	arguments = Bundle().apply { argument() }
	return this
}

inline fun <reified T : Serializable> Fragment.argument(key: String): Lazy<T> =
	lazy(LazyThreadSafetyMode.NONE) {
		arguments?.getSerializable(key) as? T
			?: throw ClassCastException("Instance of class ${T::class.java} not found in arguments")
	}

inline fun <reified T : Serializable> Fragment.argument(key: String, default: T): Lazy<T> =
	lazy(LazyThreadSafetyMode.NONE) { arguments?.getSerializable(key) as? T ?: default }

inline fun <reified T : Serializable> Fragment.argumentOptional(key: String): Lazy<T?> =
	lazy(LazyThreadSafetyMode.NONE) { arguments?.getSerializable(key) as? T? }

inline fun <reified T : Parcelable> AppCompatActivity.intent(key: String): Lazy<T> =
	lazy(LazyThreadSafetyMode.NONE) {
		intent.getParcelableExtra(key) as? T
			?: throw ClassCastException("Instance of class ${T::class.java} not found in arguments")
	}