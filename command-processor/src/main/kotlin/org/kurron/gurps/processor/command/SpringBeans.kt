package org.kurron.gurps.processor.command

import org.kurron.gurps.processor.command.administration.AdministrationCommandProcessor
import org.kurron.gurps.processor.command.campaign.CampaignCommandProcessor
import org.kurron.gurps.processor.command.character.CharacterCommandProcessor
import org.kurron.gurps.shared.*
import org.kurron.gurps.shared.SharedConstants.Companion.MESSAGE_ROUTING_LABEL
import org.springframework.amqp.core.*
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.amqp.support.converter.MessageConverter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.integration.amqp.inbound.AmqpInboundGateway
import org.springframework.integration.amqp.outbound.AmqpOutboundEndpoint
import org.springframework.integration.annotation.Router
import org.springframework.integration.annotation.ServiceActivator
import org.springframework.integration.annotation.Splitter
import org.springframework.integration.channel.DirectChannel
import org.springframework.integration.router.PayloadTypeRouter
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.handler.annotation.Header
import org.springframework.messaging.handler.annotation.Headers


@Configuration
class SpringBeans {
    @Bean
    fun defaultOutputChannel(): MessageChannel = DirectChannel()
    @Bean
    fun amqpReplyChannel(): MessageChannel = DirectChannel()

    @Bean
    fun amqpRequestChannel(): MessageChannel = DirectChannel()

    @Bean
    fun toSplitMessagesChannel(): MessageChannel = DirectChannel()
    @Bean
    fun routeSplitMessagesChannel(): MessageChannel = DirectChannel()

    @Bean
    fun outboundCommands(): MessageChannel = DirectChannel()


    @Bean
    fun outboundEvents(): MessageChannel = DirectChannel()

    @Bean
    fun outboundDocuments(): MessageChannel = DirectChannel()

    @Bean
    fun messageConverter(): MessageConverter = Jackson2JsonMessageConverter()

    // any returned values from the services are routed to the AMQP caller via his reply-to exchange.
    @Bean
    fun commandGateway(listenerContainer: SimpleMessageListenerContainer,
                       amqpRequestChannel: MessageChannel,
                       amqpReplyChannel: MessageChannel,
                       messageConverter: MessageConverter): AmqpInboundGateway {
        val adapter = AmqpInboundGateway(listenerContainer)
        adapter.setRequestChannel(amqpRequestChannel)
        adapter.setReplyChannel(amqpReplyChannel)
        adapter.setMessageConverter(messageConverter)
        return adapter
    }

    @Bean
    fun inboundLabelRouter(campaignCommands: MessageChannel, characterCommands: MessageChannel, administrationCommands: MessageChannel): InboundLabelRouter {
        return InboundLabelRouter(campaignCommands, characterCommands, administrationCommands)
    }

    class InboundLabelRouter(private val campaignCommands: MessageChannel,
                             private val characterCommands: MessageChannel,
                             private val administrationCommands: MessageChannel) {
        @Router(inputChannel = "amqpRequestChannel", defaultOutputChannel = "defaultOutputChannel")
        fun route(@Header(name = MESSAGE_ROUTING_LABEL) label: String?): MessageChannel? {
            return when (label?.lowercase()?.split('.')?.take(2)?.joinToString(".")) {
                "command.campaign" -> campaignCommands
                "command.character" -> characterCommands
                "command.administration" -> administrationCommands
                else -> null
            }
        }
    }

    @ServiceActivator(inputChannel = "routeSplitMessagesChannel")
    @Bean
    fun payloadTypeRouter() : PayloadTypeRouter {
        val router = PayloadTypeRouter()
        router.setDefaultOutputChannelName("defaultOutputChannel")
        router.setChannelMapping(Campaign::class.qualifiedName, "amqpReplyChannel")
        router.setChannelMapping(Character::class.qualifiedName, "amqpReplyChannel")
        router.setChannelMapping(Administration::class.qualifiedName, "amqpReplyChannel")
        router.setChannelMapping(Event::class.qualifiedName, "outboundEvents")
        return router
    }

    @ServiceActivator(inputChannel = "defaultOutputChannel")
    fun handleOrphanedMessage(@Headers headers: Map<String,Any>) {
        println("handleOrphanedMessage: $headers")
    }
    @Bean
    @ServiceActivator(inputChannel = "outboundEvents")
    fun eventsOutbound(rabbitMQ: AmqpTemplate): AmqpOutboundEndpoint {
        // This works because AmqpOutboundEndpoint is a MessageHandler
        val outbound = AmqpOutboundEndpoint(rabbitMQ)
        outbound.setExchangeName(SharedConstants.EVENT_EXCHANGE_NAME)
        outbound.setRoutingKey(SharedConstants.EVENT_ROUTING_KEY)
        return outbound
    }

}