package org.kurron.gurps

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.modulith.core.ApplicationModules
import org.springframework.modulith.docs.Documenter

@SpringBootTest
class ApplicationTests {

	@Test
	fun contextLoads() {
	}

	@Test
	fun writeDocumentationSnippets() {

		val modules = ApplicationModules.of(Application::class.java).verify()

		Documenter(modules).writeModulesAsPlantUml().writeIndividualModulesAsPlantUml().writeModuleCanvases();
	}
}
