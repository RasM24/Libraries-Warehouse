package ru.endroad.navigation.routing

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import ru.endroad.navigation.*

fun FragmentRouting.changeRoot(fragment: Fragment, @IdRes container: Int = R.id.fragment) =
	fragmentManager.changeRoot(fragment, container)

fun FragmentRouting.changeRoot(fragment: Fragment, animation: FragmentAnimation, @IdRes container: Int = R.id.fragment) =
	fragmentManager.changeRoot(fragment, animation, container)

fun FragmentRouting.replace(fragment: Fragment, @IdRes container: Int = R.id.fragment) =
	fragmentManager.changeRoot(fragment, container)

fun FragmentRouting.replace(fragment: Fragment, animation: FragmentAnimation, @IdRes container: Int = R.id.fragment) =
	fragmentManager.changeRoot(fragment, animation, container)

fun FragmentRouting.forwardTo(fragment: Fragment, @IdRes container: Int = R.id.fragment) =
	fragmentManager.forwardTo(fragment, container)

fun FragmentRouting.forwardTo(fragment: Fragment, animation: FragmentAnimation, @IdRes container: Int = R.id.fragment) =
	fragmentManager.forwardTo(fragment, animation, container)

fun FragmentRouting.back() =
	fragmentManager.back()

fun FragmentRouting.backTo(fragment: Class<out Fragment>) =
	fragmentManager.backTo(fragment)

fun FragmentRouting.backToRoot() =
	fragmentManager.backToRoot()