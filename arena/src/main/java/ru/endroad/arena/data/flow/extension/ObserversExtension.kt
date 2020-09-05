package ru.endroad.arena.data.flow.extension

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

inline fun <reified T> LiveData<T>.subcribe(lifecycleOwner: LifecycleOwner, crossinline handler: (T) -> Unit) {
	this.observe(lifecycleOwner, Observer<T> { value -> handler(value) })
}