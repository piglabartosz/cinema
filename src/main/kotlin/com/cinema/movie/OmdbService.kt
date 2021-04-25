package com.cinema.movie

import com.fasterxml.jackson.databind.JsonNode
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder

@Service
class OmdbService(
    val restTemplate: RestTemplate,
    @Value("\${omdb.url}") private val url: String,
    @Value("\${omdb.key}") private val apiKey: String
) {
    fun getMovieDetails(imdbId: String): ResponseEntity<JsonNode> {
        val omdbApiResponse: ResponseEntity<JsonNode> = restTemplate.getForEntity(
            UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("apikey", apiKey)
                .queryParam("i", imdbId)
                .toUriString(),
            JsonNode::class.java
        )

        return if (omdbApiResponse.statusCode == HttpStatus.OK && responseContainsMovieDetails(omdbApiResponse))
            ResponseEntity.ok(omdbApiResponse.body)
        else
            ResponseEntity.notFound().build()
    }

    private fun responseContainsMovieDetails(response: ResponseEntity<JsonNode>) =
        response.body != null
                && response.body!!.has("Response")
                && response.body!!.get("Response").textValue() == "True"
}
