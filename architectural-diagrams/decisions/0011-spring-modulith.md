# 1. Use The Spring Modulith Framework

## Status
Accepted

## Context
One of the goals of this project is to exercise the notion of a "modular monolith", seeing how it behaves in a working system. 

## Decision
[Spring Modulish](https://spring.io/projects/spring-modulith) is a new framework that provides guide rails and opinions on how JVM applications can have loosely coupled components and retain the benefits of a monolith. Let's see how much of the heavy lifting it can do for us.

## Consequences
By using a framework, we cut down on the work needed to keep components decoupled. Using Java 9 module system looks like a ton of work and using a message broker, like RabbitMQ, introduces probably just as much work. Since we're already using Spring for other parts of the solution, it makes sense to use it for modularity concerns as well.