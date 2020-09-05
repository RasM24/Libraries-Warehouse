package ru.endroad.libraries.mvi.core.view

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import ru.endroad.libraries.mvi.core.viewmodel.PresenterMvi

interface MviView<State, Event> {

	val presenter: PresenterMvi<State, Event>

	fun bindRenderState(lifecycleOwner: LifecycleOwner) {
		presenter.state.observe(lifecycleOwner, Observer<State> { state -> render(state) })
	}

	fun render(state: State)
}

