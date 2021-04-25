package com.cinema.screeningroom

import java.util.*

object ScreeningRooms {
    fun screeningRoom(
        id: UUID = UUID.fromString("c5212c59-cfeb-432c-b5e6-301950cb148f"),
        name: String = "Los Angeles Room",
        placesCount: Int = 100,
        showTimes: List<ShowTime> = emptyList()
    ) =
        ScreeningRoom(id, name, placesCount, showTimes)
}
