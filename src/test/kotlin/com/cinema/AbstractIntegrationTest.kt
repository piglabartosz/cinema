package com.cinema

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.test.web.servlet.MockMvc
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Testcontainers

@AutoConfigureMockMvc
@SpringBootTest
@Testcontainers
class AbstractIntegrationTest {

    @Autowired
    protected lateinit var mockMvc: MockMvc

    private val objectMapper = ObjectMapper()
        .registerModule(JavaTimeModule())

    companion object {
        private val container = PostgreSQLContainer<Nothing>("postgres:13").apply {
            withDatabaseName("test-db")
            withUsername("test")
            withPassword("test")
            withReuse(true)
        }

        init {
            container.start()
        }

        @JvmStatic
        @DynamicPropertySource
        fun properties(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url", container::getJdbcUrl)
            registry.add("spring.datasource.password", container::getPassword)
            registry.add("spring.datasource.username", container::getUsername)
            registry.add("spring.datasource.url", container::getJdbcUrl)
            registry.add("spring.jpa.hibernate.ddl-auto") { "create-drop" }
        }
    }

    fun convertDtoToJson(dto: Any): String =
        objectMapper.writeValueAsString(dto)
}
