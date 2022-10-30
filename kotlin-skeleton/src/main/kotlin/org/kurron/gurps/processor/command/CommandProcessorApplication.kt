package org.kurron.gurps.processor.command

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.boot.runApplication
import org.springframework.context.event.EventListener
import org.springframework.core.env.Environment

@SpringBootApplication
class CommandProcessorApplication(private final val environment: Environment) {
	@EventListener( classes = [ApplicationReadyEvent::class])
	fun begin() = println("The message is " + environment.getProperty("message"))

/*
@ConditionalOnCloudPlatform(CloudPlatform.KUBERNETES)
ApplicationListener<ApplicationReadyEvent> readyEventApplicationListener() {
    return args -> System.out.println("the application is running on Kubernetes!");
}
 */
}

fun main(args: Array<String>) {
	runApplication<CommandProcessorApplication>(*args)
}
