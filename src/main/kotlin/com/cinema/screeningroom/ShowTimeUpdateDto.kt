package com.cinema.screeningroom

import java.math.BigDecimal
import java.time.Instant
import java.util.*

data class ShowTimeUpdateDto(
    val screeningRoomId: UUID,
    val startTime: Instant,
    val endTime: Instant,
    val price: BigDecimal,
    val movieId: UUID
)
