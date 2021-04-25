package com.cinema.movie

import com.cinema.utils.JsonNodeConverter.toJsonNode
import com.fasterxml.jackson.databind.JsonNode
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import org.mockito.ArgumentMatchers.eq
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestTemplate

@TestInstance(PER_CLASS)
class OmbdServiceTest {
    private lateinit var restTemplate: RestTemplate
    private lateinit var omdbService: OmdbService

    @BeforeAll
    fun setUpService() {
        restTemplate = mock(RestTemplate::class.java)
        omdbService = OmdbService(restTemplate, "http://www.omdbapi.com", "api-key")
    }

    @Test
    fun `returns 200 if response body from IMDb has Response field set to 'True'`() {
        `when`(
            restTemplate.getForEntity(
                eq("http://www.omdbapi.com?apikey=api-key&i=imdbid"),
                eq(JsonNode::class.java)
            )
        ).thenReturn(
            ResponseEntity(toJsonNode("""{"Response":"True","other-property":"value"}"""), HttpStatus.OK)
        )

        val response: ResponseEntity<JsonNode> = omdbService.getMovieDetails("imdbid")

        assertThat(response).isEqualTo(
            ResponseEntity(toJsonNode("""{"Response":"True","other-property":"value"}"""), HttpStatus.OK)
        )
    }

    @Test
    fun `returns 404 if response body from IMDb has Response field set to 'False'`() {
        `when`(
            restTemplate.getForEntity(
                eq("http://www.omdbapi.com?apikey=api-key&i=imdbid"),
                eq(JsonNode::class.java)
            )
        ).thenReturn(
            ResponseEntity(toJsonNode("""{"Response":"False","other-property":"value"}"""), HttpStatus.OK)
        )

        val response: ResponseEntity<JsonNode> = omdbService.getMovieDetails("imdbid")

        assertThat(response).isEqualTo(ResponseEntity.notFound().build<JsonNode>())
    }

    @Test
    fun `returns 404 if response body from IMDb does not contain Response field`() {
        `when`(
            restTemplate.getForEntity(
                eq("http://www.omdbapi.com?apikey=api-key&i=imdbid"),
                eq(JsonNode::class.java)
            )
        ).thenReturn(ResponseEntity(toJsonNode("""{"only-one-property":"value"}"""), HttpStatus.OK))

        val response: ResponseEntity<JsonNode> = omdbService.getMovieDetails("imdbid")

        assertThat(response).isEqualTo(ResponseEntity.notFound().build<JsonNode>())
    }

    @Test
    fun `returns 404 if response body from IMDb does not exist`() {
        `when`(
            restTemplate.getForEntity(
                eq("http://www.omdbapi.com?apikey=api-key&i=imdbid"),
                eq(JsonNode::class.java)
            )
        ).thenReturn(ResponseEntity(null, HttpStatus.OK))

        val response: ResponseEntity<JsonNode> = omdbService.getMovieDetails("imdbid")

        assertThat(response).isEqualTo(ResponseEntity.notFound().build<JsonNode>())
    }

    @Test
    fun `returns 404 if response has other status code than 200`() {
        `when`(
            restTemplate.getForEntity(
                eq("http://www.omdbapi.com?apikey=api-key&i=imdbid"),
                eq(JsonNode::class.java)
            )
        ).thenReturn(
            ResponseEntity(toJsonNode("""{"Response":"True","other-property":"value"}"""), HttpStatus.BAD_REQUEST)
        )

        val response: ResponseEntity<JsonNode> = omdbService.getMovieDetails("imdbid")

        assertThat(response).isEqualTo(ResponseEntity.notFound().build<JsonNode>())
    }
}
