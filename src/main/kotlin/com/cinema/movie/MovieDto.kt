package com.cinema.movie

import com.fasterxml.jackson.databind.JsonNode
import java.util.*

data class MovieDto(
    val id: UUID,
    val imdbId: String,
    val name: String,
    val avgCustomerRating: Float?,
    val imdbDetails: JsonNode?
) {
    companion object {
        fun create(movie: Movie, imdbDetails: JsonNode?) =
            MovieDto(
                id = movie.id,
                imdbId = movie.imdbId,
                name = movie.name,
                avgCustomerRating = movie.avgCustomerRating,
                imdbDetails = imdbDetails
            )
    }
}
