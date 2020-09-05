package ru.endroad.libraries.mvi.core.viewmodel

import androidx.lifecycle.LiveData

interface PresenterMvi<State, Event> {

	val state: LiveData<State>
	fun reduce(event: Event)
}