package ru.endroad.telnet

class Response(private val code: Int, private val message: String = "") {

	override fun toString(): String {
		val status = if (code == OK) STATUS_OK else STATUS_ERROR

		return "Code: $code, Status: $status, Message: $message"
	}

	companion object {
		private const val STATUS_OK = "OK"
		private const val STATUS_ERROR = "ERROR"

		const val OK = 0
		const val ERROR = 20
		const val AUTH = 0
		const val INVALIDATE = 21

		const val ERROR_COMMAND = 30
		const val ERROR_COMMAND_COUNT = 31
		const val ERROR_COMMAND_NOT_FOUND = 32
		const val ERROR_COMMAND_NPE = 33

		const val ERROR_ARGUMENT = 40
		const val ERROR_ARGUMENT_COUNT = 41
		const val ERROR_ARGUMENT_NOT_FOUND = 42

		const val ERROR_FILE_NOT_FOUND = 50
	}
}