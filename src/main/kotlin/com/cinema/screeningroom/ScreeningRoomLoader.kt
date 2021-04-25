package com.cinema.screeningroom

import com.cinema.utils.Money
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
                id = UUID.fromString("461d5f7e-6c56-4f1d-9164-a31002951b6b"),
                name = "Los Angeles Screening Room",
                placesCount = 100,
                showTimes = emptyList()
            )
        )

        showTimeRepository.save(
            ShowTime(
                id = UUID.fromString("c4ef0c3f-9825-491e-832c-f4e26e3ee7e4"),
                screeningRoom = screeningRoom,
                period = ShowTimePeriod(
                    startTime = LocalDateTime.of(2020, 1, 1, 8, 0).atZone(ZoneId.systemDefault()).toInstant(),
                    endTime = LocalDateTime.of(2020, 1, 1, 10, 0).atZone(ZoneId.systemDefault()).toInstant()
                ),
                price = Money(BigDecimal.valueOf(100)),
                movieId = UUID.fromString("5a4d60ef-5dcc-4839-b5df-5e9ecda6b7b7")
            )
        )
    }
}
