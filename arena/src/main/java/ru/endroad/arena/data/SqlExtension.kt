package ru.endroad.arena.data

import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

fun SupportSQLiteDatabase.runTransaction(transaction: SupportSQLiteDatabase.() -> Unit) {
	try {
		beginTransaction()
		this.transaction()
		setTransactionSuccessful()
	} finally {
		endTransaction()
	}
}

fun <T : RoomDatabase> RoomDatabase.Builder<T>.preload(preloader: SupportSQLiteDatabase.() -> Unit): RoomDatabase.Builder<T> {
	this.addCallback(object : RoomDatabase.Callback() {
		override fun onCreate(db: SupportSQLiteDatabase) {
			db.preloader()
		}
	})

	return this
}