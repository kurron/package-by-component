package org.kurron.gurps.user

import com.fasterxml.jackson.databind.ObjectMapper
import org.kurron.gurps.shared.SharedConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.hateoas.config.EnableHypermediaSupport
import org.springframework.util.ErrorHandler


@Configuration
@EnableHypermediaSupport(type = [EnableHypermediaSupport.HypermediaType.HAL, EnableHypermediaSupport.HypermediaType.HTTP_PROBLEM_DETAILS])
class UserConfiguration {

}