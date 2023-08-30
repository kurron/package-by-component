# 1. Use Spring AMQP

## Status
Rejected

## Context
In a message centric solution, routing of messages to the correct handler is both crucial and complex. Over time, those routing rules will change and we want a mechanism that allows for easy evolution of the system.

## Decision
Leveraging the [Spring AMQP](https://spring.io/projects/spring-amqp) framework to manage the routing of messages in the system does not make sense in a [Spring Modulith](https://spring.io/projects/spring-modulith) environment.

## Consequences
By taking using a framework that uses in-process messages we can avoid some of the complexity associated with using messages for modularity.