package com.cinema.movie

import java.util.*
import javax.persistence.*

@Entity
data class Movie(
    @Id val id: UUID,
    val imdbId: String,
    val name: String,
    @OneToMany(mappedBy = "movie") val customerRatings: List<CustomerRating>,
    /**
     * TODO Create value object with business rules during adding endpoint for movie rating
     */
    val avgCustomerRating: Float?
)

/**
 * TODO Add customer during adding endpoint for movie rating
 */
@Entity
data class CustomerRating(
    @Id val id: UUID,

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id", nullable = false)
    val movie: Movie,

    /**
     * TODO Create value object with business rules during adding endpoint for movie rating
     */
    val rating: Float
)
