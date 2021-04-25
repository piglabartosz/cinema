package com.cinema.movie

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/movies")
class MovieController(private val movieDetailsService: MovieDetailsService) {
    @GetMapping("/{movieId}")
    fun getMovieDetails(@PathVariable movieId: UUID): MovieDto {
        return movieDetailsService.getMovieDetails(movieId)
    }
}
