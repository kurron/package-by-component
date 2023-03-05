package org.kurron.gurps.shared

import org.springframework.amqp.rabbit.listener.ConditionalRejectingErrorHandler
import org.springframework.classify.BinaryExceptionClassifier
import org.springframework.dao.NonTransientDataAccessException

class CustomExceptionStrategy: ConditionalRejectingErrorHandler.DefaultExceptionStrategy() {
    override fun isUserCauseFatal(cause: Throwable): Boolean {
        val types: MutableMap<Class<out Throwable>, Boolean> = mutableMapOf(NonTransientDataAccessException::class.java to true)
        val classifier = BinaryExceptionClassifier(types, false, true)
        return classifier.classify(cause)
    }
}