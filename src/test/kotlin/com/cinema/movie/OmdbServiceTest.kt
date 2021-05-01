package com.cinema.movie

import com.cinema.utils.JsonNodeConverter.toJsonNode
import com.fasterxml.jackson.databind.JsonNode
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

@TestInstance(PER_CLASS)
class OmdbServiceTest {
    @Test
    fun `returns 200 if response body from IMDb has Response field set to 'True'`() {
        val service = OmdbService(
            omdbClient = { _, _ ->
                ResponseEntity(
                    toJsonNode("""{"Response":"True","other-property":"value"}"""),
                    HttpStatus.OK
                )
            },
            apiKey = "api-key"
        )

        val response = service.getMovieDetails("imdbid")

        assertThat(response).isEqualTo(
            ResponseEntity(toJsonNode("""{"Response":"True","other-property":"value"}"""), HttpStatus.OK)
        )
    }

    @Test
    fun `returns 404 if response body from IMDb has Response field set to 'False'`() {
        val service = OmdbService(
            omdbClient = { _, _ ->
                ResponseEntity(
                    toJsonNode("""{"Response":"False","other-property":"value"}"""),
                    HttpStatus.OK
                )
            },
            apiKey = "api-key"
        )

        val response = service.getMovieDetails("imdbid")

        assertThat(response).isEqualTo(ResponseEntity.notFound().build<JsonNode>())
    }

    @Test
    fun `returns 404 if response body from IMDb does not contain Response field`() {
        val service = OmdbService(
            omdbClient = { _, _ ->
                ResponseEntity(
                    toJsonNode("""{"only-one-property":"value"}"""),
                    HttpStatus.OK
                )
            },
            apiKey = "api-key"
        )

        val response = service.getMovieDetails("imdbid")

        assertThat(response).isEqualTo(ResponseEntity.notFound().build<JsonNode>())
    }

    @Test
    fun `returns 404 if response body from IMDb does not exist`() {
        val service = OmdbService(
            omdbClient = { _, _ -> ResponseEntity(null, HttpStatus.OK) },
            apiKey = "api-key"
        )

        val response = service.getMovieDetails("imdbid")

        assertThat(response).isEqualTo(ResponseEntity.notFound().build<JsonNode>())
    }

    @Test
    fun `returns 404 if response has other status code than 200`() {
        val service = OmdbService(
            omdbClient = { _, _ ->
                ResponseEntity(
                    toJsonNode("""{"Response":"True","other-property":"value"}"""),
                    HttpStatus.NOT_FOUND
                )
            },
            apiKey = "api-key"
        )

        val response = service.getMovieDetails("imdbid")

        assertThat(response).isEqualTo(ResponseEntity.notFound().build<JsonNode>())
    }
}
