package ru.endroad.libraries.camp.fragment

import android.content.Context.INPUT_METHOD_SERVICE
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.camp_appbar_fragment.*
import ru.endroad.libraries.camp.R

//TODO проверить и настроить такие кейсы:
// 1. detach этого фрагмента, нужно ли отвязывать toolbar
// 2. на некоторых экранах не нужен toolbar - придумать как лучше его скрывать
abstract class CampAppBarFragment : CampFragment() {

	override val layout = R.layout.camp_appbar_fragment

	override fun setupViewComponents() {
		(activity as AppCompatActivity).run {
			setSupportActionBar(toolbar)
			toolbar.setNavigationOnClickListener { activity?.onBackPressed() }
			supportFragmentManager.addOnBackStackChangedListener {
				hideSoftKeyboard()
				supportActionBar?.setHomeEnabled()
			}
			supportActionBar?.setHomeEnabled()
		}
	}

	private fun ActionBar.setHomeEnabled() {
		this.setDisplayHomeAsUpEnabled(requireFragmentManager().backStackEntryCount != 0)
	}

	protected fun AppCompatActivity.hideSoftKeyboard() {
		val inputMethodManager = (getSystemService(INPUT_METHOD_SERVICE) as? InputMethodManager) ?: return
		currentFocus?.run { inputMethodManager.hideSoftInputFromWindow(windowToken, 0) }
	}
}