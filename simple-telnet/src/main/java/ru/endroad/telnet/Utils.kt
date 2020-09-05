package ru.endroad.telnet

import java.io.PrintWriter

fun PrintWriter.send(response: Response) = println(response.toString())

fun PrintWriter.printList(list: List<String>) = list.forEach { println(it) }