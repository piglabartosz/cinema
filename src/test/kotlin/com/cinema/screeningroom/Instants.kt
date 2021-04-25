package com.cinema.screeningroom

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

object Instants {
    fun instant(
        year: Int = 2020,
        month: Int = 1,
        dayOfMonth: Int = 1,
        hour: Int = 8,
        minute: Int = 16
    ): Instant =
        LocalDateTime.of(year, month, dayOfMonth, hour, minute).atZone(ZoneId.systemDefault()).toInstant()
}
