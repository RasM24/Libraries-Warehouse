package ru.endroad.services.ftp

import org.apache.ftpserver.ConnectionConfig
import org.apache.ftpserver.FtpServer
import org.apache.ftpserver.FtpServerFactory
import org.apache.ftpserver.ftplet.FtpException
import org.apache.ftpserver.listener.Listener
import org.apache.ftpserver.usermanager.impl.BaseUser

//TODO нужен грамотный рефакторинг всего модуля
//TODO Добавить sample для запуска и проверки модуля
class FtpServer(
	port: Int = 2122,
	login: String,
	password: String?,
	home: String
) {

	private val connectionConfig: ConnectionConfig by ftpFactory()
	private val configListener: Listener by ftpFactory(port = port)

	private val userManager = createWithUser(login, password, home)
	private var server: FtpServer? = null

	fun start() {
		try {
			server?.safeStop()
			server = createServer()
			server?.start()
		} catch (ex: FtpException) {

		}
	}

	private fun createServer(): FtpServer =
		FtpServerFactory().apply {
			addListener("default", configListener)
			userManager = this@FtpServer.userManager
			connectionConfig = this@FtpServer.connectionConfig
		}.createServer()

	private fun createWithUser(login: String, password: String?, homeDir: String): UserManager {
		val user = BaseUser().apply {
			name = login
			password?.let { this.password = it }
			homeDirectory = homeDir
			enabled = true
		}
		return UserManager().apply { setUser(user) }
	}

	private fun FtpServer.safeStop() {
		if (!isStopped)
			this.stop()
	}
}