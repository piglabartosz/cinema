package com.cinema.movie

import com.cinema.exception.DomainObjectNotFoundException
import com.fasterxml.jackson.databind.JsonNode
import org.springframework.stereotype.Service
import java.util.*

@Service
class MovieDetailsService(
    private val movieRepository: MovieRepository,
    private val omdbService: OmdbService
) {
    fun getMovieDetails(id: UUID): MovieDto {
        val movie = movieRepository
            .findById(id)
            .orElseThrow { DomainObjectNotFoundException("Movie with given id does not exist.") }

        val imdbDetailsResponse = omdbService.getMovieDetails(movie.imdbId)

        return MovieDto.create(movie, imdbDetailsResponse.body)
    }
}

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
