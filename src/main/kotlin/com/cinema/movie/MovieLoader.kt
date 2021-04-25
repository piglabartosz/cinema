package com.cinema.movie

import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component
import java.util.*

@Component
class MovieLoader(private val movieRepository: MovieRepository) : ApplicationRunner {
    override fun run(args: ApplicationArguments) {
        movieRepository.save(
            Movie(
                id = UUID.fromString("93e0bbf7-c79d-4011-beec-2a5bb063c040"),
                imdbId = "tt0232500",
                name = "The Fast and the Furious",
                customerRatings = emptyList(),
                avgCustomerRating = null,
            )
        )
    }
}
