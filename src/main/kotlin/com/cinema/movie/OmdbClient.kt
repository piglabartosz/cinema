package com.cinema.movie

import com.fasterxml.jackson.databind.JsonNode
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(name = "omdb", url = "http://www.omdbapi.com")
fun interface OmdbClient {
    @GetMapping
    fun getMovieDetails(
        @RequestParam apiKey: String,
        @RequestParam("i") imdbId: String
    ): ResponseEntity<JsonNode>
}
