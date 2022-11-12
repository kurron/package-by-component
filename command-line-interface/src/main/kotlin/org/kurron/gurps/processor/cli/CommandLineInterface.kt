package org.kurron.gurps.processor.cli

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CommandLineInterface

fun main(args: Array<String>) {
	runApplication<CommandLineInterface>(*args)
}
