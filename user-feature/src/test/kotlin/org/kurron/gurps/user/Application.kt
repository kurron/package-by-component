package org.kurron.gurps.user
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

// We need to have this in order to run the in-memory integration tests. In production, the application will provide the SpringBootApplication.
@SpringBootApplication
class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}