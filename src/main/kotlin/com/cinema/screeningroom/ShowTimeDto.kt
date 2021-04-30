package com.cinema.screeningroom

import java.math.BigDecimal
import java.time.Instant
import java.util.*

data class ShowTimeDto(
    val id: UUID,
    val screeningRoomId: UUID,
    val startTime: Instant,
    val endTime: Instant,
    val price: BigDecimal,
    val movieId: UUID
) {
    companion object {
        fun createFromDomainObject(showTime: ShowTime) =
            ShowTimeDto(
                id = showTime.id,
                screeningRoomId = showTime.screeningRoom.id,
                startTime = showTime.period.startTime,
                endTime = showTime.period.endTime,
                price = showTime.price.value,
                movieId = showTime.movieId
            )
    }
}
