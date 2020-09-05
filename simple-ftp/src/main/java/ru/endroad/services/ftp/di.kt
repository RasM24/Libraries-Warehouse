package ru.endroad.services.ftp

import org.apache.ftpserver.ConnectionConfig
import org.apache.ftpserver.ConnectionConfigFactory
import org.apache.ftpserver.listener.Listener
import org.apache.ftpserver.listener.ListenerFactory
import kotlin.reflect.KProperty

internal inline fun <reified T : Any> ftpFactory(port: Int? = null): Creator<T> {
	return when (T::class) {
		ConnectionConfig::class -> Creator(simpleConfigConnection as T)
		Listener::class         -> Creator(getSimpleListenerFactory(port) as T)
		else                    -> throw ClassNotFoundException()
	}
}

internal class Creator<out T>(private val value: T) {
	@Suppress("UNCHECKED_CAST")
	operator fun getValue(thisRef: Any?, property: KProperty<*>): T = value
}

private val simpleConfigConnection = ConnectionConfigFactory().apply {
	isAnonymousLoginEnabled = true
	maxAnonymousLogins = 15
	maxLoginFailures = 5
	loginFailureDelay = 30
	maxThreads = 10
	maxLogins = 10
}.createConnectionConfig()

private fun getSimpleListenerFactory(listenerPort: Int?): Listener =
	ListenerFactory().apply {
		port = listenerPort ?: throw NullPointerException("Port must not be null")
		idleTimeout = 60
	}.createListener()
