package ru.endroad.telnet

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.endroad.telnet.ICommandHandler.Companion.HELP
import ru.endroad.telnet.ICommandHandler.Companion.QUIT
import ru.endroad.telnet.Response.Companion.AUTH
import ru.endroad.telnet.Response.Companion.INVALIDATE
import ru.endroad.telnet.Response.Companion.OK
import java.io.*
import java.net.ServerSocket
import java.net.Socket

//TODO нужен грамотный рефакторинг всего модуля
//TODO Добавить sample для запуска и проверки модуля
class TelnetServer(
	private val login: String,
	private val password: String,
	private val handler: ICommandHandler,
	port: Int = 8081
) : CoroutineScope {

	override val coroutineContext = Dispatchers.IO

	private val serverSocket = ServerSocket(port)

	fun start() = launch {
		try {
			serverSocket.soTimeout

			while (true) {
				val socket = serverSocket.accept()
				launch { safety(socket, ::connectClient) }
			}
		} catch (e: IOException) {
			e.printStackTrace()
		}
	}

	private fun safety(socket: Socket, connect: (Socket) -> Unit) {
		try {
			connect(socket)
		} catch (e: IOException) {
			e.printStackTrace()
		} finally {
			socket.close()
		}
	}

	/**
	 * Работа с подключенным клиентом
	 */
	private fun connectClient(socket: Socket) {
		var line: String
		var command: List<String>
		var response: Response

		//input/output Stream
		val writer = PrintWriter(socket.getOutputStream(), true)
		val reader = BufferedReader(InputStreamReader(socket.getInputStream()))

		//аутентификация
		writer.println("enter login and password")

		line = reader.readLine() ?: ""
		command = line.split(" ", limit = 2)

		if (command[0] == login && command[1] == password)
			writer.send(Response(AUTH))
		else {
			writer.send(Response(INVALIDATE, "login or password incorrect"))
			socket.close()
		}

		//работа с клиентом
		while (socket.isConnected) {
			//чтение и перевод в нужный формат
			line = reader.readLine() ?: continue
			command = line.split(" ", limit = 2)

			if (command[0].isEmpty()) {
				writer.print("#")
				writer.flush()
				continue
			}

			if (command[0] == "author") {
				writer.send(Response(OK, "Dvolyatik Oleg"))
				continue
			}
			if (command[0] == HELP) {
				writer.printList(handler.getHelp())
				continue
			}
			if (command[0] == QUIT) socket.close()

			//выполнение запроса и ответ серверу
			response = handler.parseCommand(command) ?: continue
			writer.send(response)
		}
	}
}