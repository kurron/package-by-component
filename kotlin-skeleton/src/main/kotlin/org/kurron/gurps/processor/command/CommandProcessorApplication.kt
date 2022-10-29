package org.kurron.gurps.processor.command

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CommandProcessorApplication

fun main(args: Array<String>) {
	runApplication<CommandProcessorApplication>(*args)
}
