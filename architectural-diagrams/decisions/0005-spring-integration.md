# 1. Use Spring Integration

## Status
Accepted

## Context
The solution is centered around messaging and its accepted patterns, as described in [Enterprise Integration Patterns](https://www.enterpriseintegrationpatterns.com/). Implementing those patterns should not be a concern of the project, allowing the team to focus on business value.

## Decision
We will let [Spring Integration](https://spring.io/projects/spring-integration) be our implementation of those patterns.

## Consequences
We're already using Spring Boot so using Spring Integration is a natural fit. Spring Integration has shown value in past projects and we so no reason why it should not here. Admittedly, this is the first Kotlin project to use Spring Integration, but we do not anticipate any issues in that regard.