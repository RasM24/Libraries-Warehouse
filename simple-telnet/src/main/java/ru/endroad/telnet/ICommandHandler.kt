package ru.endroad.telnet

abstract class ICommandHandler {

	abstract fun parseCommand(command: List<String>): Response?

	abstract fun getHelp(): List<String>

	companion object {
		const val HELP = "help"
		const val QUIT = "quit"
	}
}