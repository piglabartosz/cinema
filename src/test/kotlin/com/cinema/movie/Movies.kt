package com.cinema.movie

import java.util.*

object Movies {
    fun movie(
        id: UUID = UUID.fromString("d07e2a9b-f9c9-4e4b-94e5-ca9b0021a9ab"),
        imbdId: String = "tt0232500",
        name: String = "The Fast and the Furious",
        customerRatings: List<CustomerRating> = emptyList(),
        avgCustomerRating: Float? = null
    ) = Movie(id, imbdId, name, customerRatings, avgCustomerRating)
}
