package org.kurron.gurps;

import io.cucumber.java.AfterAll;
import io.cucumber.java.BeforeAll;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.testcontainers.containers.PulsarContainer;
import org.testcontainers.utility.DockerImageName;

public class Containers {
    private static final DockerImageName pulsarImage = DockerImageName.parse("apachepulsar/pulsar:2.10.2");
    private static final PulsarContainer pulsar = new PulsarContainer(pulsarImage);

    @BeforeAll
    public static void startContainers() {
        //pulsar.start();
    }

    @AfterAll
    public static void stopContainers() {
        //pulsar.close();
    }

    public static void registerContainerCoordinates(DynamicPropertyRegistry registry) {
        registerRabbitCoordinates(registry);
    }

    private static void registerRabbitCoordinates(DynamicPropertyRegistry registry) {
        registry.add("spring.rabbitmq.host", () -> "localhost");
        registry.add("spring.rabbitmq.port", () -> 5672);
        registry.add("spring.rabbitmq.username", () -> "rabbitmq");
        registry.add("spring.rabbitmq.password", () -> "rabbitmq");
        registry.add("spring.rabbitmq.virtual-host", () -> "gurps");
    }
}
