package com.cinema.screeningroom

import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component
import java.math.BigDecimal
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

@Component
class ScreeningRoomLoader(
    private val screeningRoomRepository: ScreeningRoomRepository,
    private val showTimeRepository: ShowTimeRepository
) : ApplicationRunner {
    override fun run(args: ApplicationArguments) {
        val screeningRoom = screeningRoomRepository.save(
            ScreeningRoom(
                roomName = "Los Angeles Screening Room",
                placesCount = 100,
                showTimes = emptyList()
            )
        )

        showTimeRepository.save(
            ShowTime(
                id = UUID.fromString("c4ef0c3f-9825-491e-832c-f4e26e3ee7e4"),
                screeningRoom = screeningRoom,
                period = Period(
                    startTime = LocalDateTime.of(2020, 1, 1, 8, 0).atZone(ZoneId.systemDefault()).toInstant(),
                    endTime = LocalDateTime.of(2020, 1, 1, 10, 0).atZone(ZoneId.systemDefault()).toInstant()
                ),
                price = Money(BigDecimal.valueOf(100)),
                moveId = "movie-id"
            )
        )
    }
}
