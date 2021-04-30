package com.cinema.movie

import com.cinema.exception.DomainObjectNotFoundException
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
