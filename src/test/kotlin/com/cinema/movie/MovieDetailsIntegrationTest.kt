package com.cinema.movie

import com.cinema.AbstractIntegrationTest
import com.cinema.movie.Movies.movie
import com.cinema.utils.JsonNodeConverter.toJsonNode
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.nullValue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import org.mockito.Mockito.`when`
import org.mockito.Mockito.anyString
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.util.*

@TestInstance(PER_CLASS)
class MovieDetailsIntegrationTest : AbstractIntegrationTest() {

    @Autowired
    private lateinit var movieRepository: MovieRepository

    @MockBean
    private lateinit var omdbClient: OmdbClient

    @Test
    fun `returns 200 with movie details from IMDb if OMDb API works`() {
        val movie = movieRepository.save(
            movie(
                id = UUID.fromString("d07e2a9b-f9c9-4e4b-94e5-ca9b0021a9ab"),
                imbdId = "tt0232500",
                name = "The Fast and the Furious",
                avgCustomerRating = 3f
            )
        )
        `when`(omdbClient.getMovieDetails(anyString(), anyString())).thenReturn(
            ResponseEntity(toJsonNode("""{"Response":"True","Awards":"11 wins"}"""), HttpStatus.OK)
        )

        mockMvc.perform(getMovieDetailsRequest(movie.id))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id", `is`(movie.id.toString())))
            .andExpect(jsonPath("$.imdbId", `is`("tt0232500")))
            .andExpect(jsonPath("$.name", `is`("The Fast and the Furious")))
            .andExpect(jsonPath("$.avgCustomerRating", `is`(3.0)))
            .andExpect(jsonPath("$.imdbDetails.Response", `is`("True")))
            .andExpect(jsonPath("$.imdbDetails.Awards", `is`("11 wins")))
    }

    @Test
    fun `returns 200 without movie details from IMDb if OMDb API does not work`() {
        val movie = movieRepository.save(
            movie(
                id = UUID.fromString("d07e2a9b-f9c9-4e4b-94e5-ca9b0021a9ab"),
                imbdId = "tt0232500",
                name = "The Fast and the Furious",
                avgCustomerRating = 3f
            )
        )
        `when`(omdbClient.getMovieDetails(anyString(), anyString())).thenReturn(ResponseEntity.notFound().build())

        mockMvc.perform(getMovieDetailsRequest(movie.id))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id", `is`(movie.id.toString())))
            .andExpect(jsonPath("$.imdbId", `is`("tt0232500")))
            .andExpect(jsonPath("$.name", `is`("The Fast and the Furious")))
            .andExpect(jsonPath("$.avgCustomerRating", `is`(3.0)))
            .andExpect(jsonPath("$.imdbDetails", nullValue()))
    }

    @Test
    fun `returns 404 if movie does not exist`() {
        mockMvc.perform(getMovieDetailsRequest(UUID.fromString("35fb9c74-a3eb-4520-852e-5428e2938ceb")))
            .andExpect(status().isNotFound)
            .andExpect(jsonPath("$", `is`("Movie with given id does not exist.")))
    }

    private fun getMovieDetailsRequest(movieId: UUID) =
        get("/movies/$movieId")
}
