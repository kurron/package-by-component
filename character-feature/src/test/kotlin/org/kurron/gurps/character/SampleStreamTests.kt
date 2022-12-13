package org.kurron.gurps.character

import java.util.*
import java.util.function.Function
import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cloud.stream.binder.test.InputDestination
import org.springframework.cloud.stream.binder.test.OutputDestination
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.messaging.support.MessageBuilder


@SpringBootTest
@Import(TestChannelBinderConfiguration::class)
class SampleStreamTests(@Autowired val input: InputDestination, @Autowired val output: OutputDestination) {

    @Test
    fun testEmptyConfiguration() {
        val message = MessageBuilder.withPayload("hello".toByteArray()).build()
        input.send(message)
        // How the hell are you supposed to know what the binding name is?
        val actual = output.receive(10L, "uppercase-out-0.destination").payload
        val expected = "HELLO".toByteArray()
        assertArrayEquals(actual,expected)
    }
}

// TestConfiguration doesn't get picked up
@Configuration
class TestBeansX {
    @Bean
    fun uppercase(): Function<String, String> {
        return Function<String, String> { v -> v.uppercase(Locale.getDefault()) }
    }
}