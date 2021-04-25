package com.cinema.screeningroom

import com.cinema.screeningroom.Instants.instant
import com.cinema.utils.Money
import java.math.BigDecimal
import java.util.*

object ShowTimes {
    fun showTime(
        id: UUID = UUID.fromString("39422574-11bc-445d-ba2b-8dc8424a3c80"),
        screeningRoom: ScreeningRoom = ScreeningRoom(
            id = UUID.fromString("98fd6d8e-55cd-46f8-870f-66b0aa0e2f20"),
            name = "Los Angeles Room",
            placesCount = 100,
            showTimes = emptyList()
        ),
        period: ShowTimePeriod = ShowTimePeriod(instant(hour = 6), instant(hour = 8))
    ) =
        ShowTime(
            id = id,
            screeningRoom = screeningRoom,
            period = period,
            price = Money(BigDecimal.valueOf(100)),
            movieId = UUID.fromString("5a4d60ef-5dcc-4839-b5df-5e9ecda6b7b7")
        )
}
