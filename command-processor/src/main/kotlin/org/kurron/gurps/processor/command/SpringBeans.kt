package org.kurron.gurps.processor.command

import org.kurron.gurps.shared.Command
import org.kurron.gurps.shared.Event
import org.kurron.gurps.shared.SharedConstants
import org.kurron.gurps.shared.SharedConstants.Companion.MESSAGE_ROUTING_LABEL
import org.springframework.amqp.core.*
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.amqp.support.converter.MessageConverter
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.integration.amqp.inbound.AmqpInboundChannelAdapter
import org.springframework.integration.amqp.outbound.AmqpOutboundEndpoint
import org.springframework.integration.annotation.Router
import org.springframework.integration.annotation.ServiceActivator
import org.springframework.integration.channel.DirectChannel
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.handler.annotation.Header
import org.springframework.messaging.handler.annotation.Headers
import java.time.Instant


@Configuration
class SpringBeans {
    @Bean
    fun defaultOutputChannel(): MessageChannel = DirectChannel()

    @Bean
    fun inboundCommands(): MessageChannel = DirectChannel()

    @Bean
    fun campaignCommands(): MessageChannel = DirectChannel()

    @Bean
    fun characterCommands(): MessageChannel = DirectChannel()

    @Bean
    fun administrationCommands(): MessageChannel = DirectChannel()

    @Bean
    fun outboundEvents(): MessageChannel = DirectChannel()

    @Bean
    fun outboundDocuments(): MessageChannel = DirectChannel()

    @Bean
    fun commandQueue(): Queue = QueueBuilder.nonDurable(SharedConstants.COMMAND_QUEUE_NAME).build()

    @Bean
    fun commandExchange(): Exchange = ExchangeBuilder.directExchange(SharedConstants.COMMAND_EXCHANGE_NAME).durable(false).build()

    @Bean
    fun commandBinding(commandQueue: Queue, commandExchange: Exchange): Binding = BindingBuilder.bind(commandQueue).to(commandExchange).with(SharedConstants.COMMAND_ROUTING_KEY).noargs()

    @Bean
    fun commandContainer(connectionFactory: ConnectionFactory, commandQueue: Queue): SimpleMessageListenerContainer {
        val container = SimpleMessageListenerContainer(connectionFactory)
        container.setQueues(commandQueue)
        container.setConcurrentConsumers(2)
        return container
    }

    @Bean
    fun messageConverter(): MessageConverter = Jackson2JsonMessageConverter()

    @Bean
    fun commandConsumer(listenerContainer: SimpleMessageListenerContainer, inboundCommands: MessageChannel): AmqpInboundChannelAdapter {
        val adapter = AmqpInboundChannelAdapter(listenerContainer)
        adapter.outputChannel = inboundCommands
        return adapter
    }

    @ServiceActivator(inputChannel = "inboundCommands")
    @Bean
    fun labelRouter(campaignCommands: MessageChannel, characterCommands: MessageChannel, administrationCommands: MessageChannel): LabelRouter {
        return LabelRouter(campaignCommands, characterCommands, administrationCommands)
    }

    class LabelRouter(private val campaignCommands: MessageChannel, private val characterCommands: MessageChannel, private val administrationCommands: MessageChannel) {
        @Router(inputChannel = "inboundCommands", defaultOutputChannel = "defaultOutputChannel")
        fun route(@Header(name = MESSAGE_ROUTING_LABEL) label: String?): MessageChannel? {
            val prefix = label?.lowercase()?.split('.')?.take(2)?.joinToString(".")
            return when(prefix) {
                "command.campaign" -> campaignCommands
                "command.character" -> characterCommands
                "command.administration" -> administrationCommands
                else -> null
            }
        }
    }
    class CommandProcessor() {
        @ServiceActivator(inputChannel = "campaignCommands", outputChannel = "outboundEvents", requiresReply = "true")
        fun handleCampaignMessage(command: Command, @Headers headers: Map<String,Any>) : Event {
            // Spring transforms the AMQP message into a Command
            // process the command by writing to MongoDB
            // create an event and return it, where it will be picked up by the AMQP outbound adapter
            println("handleCampaignMessage: $command")
            return Event(command.label)
        }
        @ServiceActivator(inputChannel = "administrationCommands", outputChannel = "outboundEvents", requiresReply = "true")
        fun handleAdministrationCommandsMessage(command: Command, @Headers headers: Map<String,Any>) : Event {
            // Spring transforms the AMQP message into a Command
            // process the command by writing to MongoDB
            // create an event and return it, where it will be picked up by the AMQP outbound adapter
            println("handleAdministrationCommandsMessage: $command")
            return Event(command.label)
        }

        @ServiceActivator(inputChannel = "characterCommands", outputChannel = "outboundEvents", requiresReply = "true")
        fun handleCharacterMessage(command: Command, @Headers headers: Map<String,Any>) : Event {
            // Spring transforms the AMQP message into a Command
            // process the command by writing to MongoDB
            // create an event and return it, where it will be picked up by the AMQP outbound adapter
            println("handleCharacterMessage: $command")
            return Event(command.label)
        }

        @ServiceActivator(inputChannel = "defaultOutputChannel")
        fun handleOrphanedMessage(@Headers headers: Map<String,Any>) {
            println("handleOrphanedMessage: $headers")
        }
    }

    @Bean
    fun commandProcessor(): CommandProcessor = CommandProcessor()

    @Bean
    fun eventQueue(): Queue = QueueBuilder.nonDurable(SharedConstants.EVENT_QUEUE_NAME).build()

    @Bean
    fun eventExchange(): Exchange = ExchangeBuilder.fanoutExchange(SharedConstants.EVENT_EXCHANGE_NAME).durable(false).build()

    @Bean
    fun eventBinding(eventQueue: Queue, eventExchange: Exchange): Binding = BindingBuilder.bind(eventQueue).to(eventExchange).with(SharedConstants.EVENT_ROUTING_KEY).noargs()


    // This works because AmqpOutboundEndpoint is a MessageHandler
    @Bean
    @ServiceActivator(inputChannel = "outboundEvents")
    fun eventsOutbound(rabbitMQ: AmqpTemplate): AmqpOutboundEndpoint {
        val outbound = AmqpOutboundEndpoint(rabbitMQ)
        outbound.setExchangeName(SharedConstants.EVENT_EXCHANGE_NAME)
        outbound.setRoutingKey(SharedConstants.EVENT_ROUTING_KEY)
        return outbound
    }

    @Bean
    fun fireOffMessage(template: AmqpTemplate): CommandLineRunner = CommandLineRunner { template.convertAndSend(SharedConstants.COMMAND_ROUTING_KEY, Command(Instant.now().toString())) }
}