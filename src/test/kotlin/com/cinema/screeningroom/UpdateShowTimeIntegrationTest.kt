package com.cinema.screeningroom

import com.cinema.AbstractIntegrationTest
import com.cinema.screeningroom.Instants.instant
import com.cinema.screeningroom.ScreeningRooms.screeningRoom
import com.cinema.screeningroom.ShowTimes.showTime
import org.assertj.core.api.Assertions.assertThat
import org.hamcrest.CoreMatchers.`is`
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.math.BigDecimal
import java.time.Instant
import java.util.*

class UpdateShowTimeIntegrationTest : AbstractIntegrationTest() {

    @Autowired
    private lateinit var screeningRoomRepository: ScreeningRoomRepository

    @Autowired
    private lateinit var showTimeRepository: ShowTimeRepository

    @BeforeEach
    fun resetDatabase() {
        showTimeRepository.deleteAll()
        screeningRoomRepository.deleteAll()
    }

    @Test
    fun `returns 200 if updated show time is valid`() {
        val screeningRoom = screeningRoomRepository.save(screeningRoom())
        val showTime = showTimeRepository.save(
            showTime(
                screeningRoom = screeningRoom,
                period = ShowTimePeriod(instant(hour = 8, minute = 0), instant(hour = 10, minute = 0))
            )
        )
        val showTimeUpdate = showTimeUpdate(
            screeningRoomId = screeningRoom.id,
            startTime = instant(hour = 6, minute = 0),
            endTime = instant(hour = 12, minute = 0),
            price = BigDecimal.valueOf(100),
            movieId = showTime.movieId
        )

        mockMvc.perform(updateShowTimeRequest(showTime.id, showTimeUpdate))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id", `is`(showTime.id.toString())))
            .andExpect(jsonPath("$.screeningRoomId", `is`(screeningRoom.id.toString())))
            .andExpect(jsonPath("$.startTime", `is`(instant(hour = 6, minute = 0).toString())))
            .andExpect(jsonPath("$.endTime", `is`(instant(hour = 12, minute = 0).toString())))
            .andExpect(jsonPath("$.price", `is`(100)))
            .andExpect(jsonPath("$.movieId", `is`(showTime.movieId.toString())))

        assertThat(showTimeRepository.count()).isEqualTo(1L)
        val updatedShowTimePeriod = showTimeRepository.findById(showTime.id).get().period
        assertThat(updatedShowTimePeriod).isEqualTo(
            ShowTimePeriod(
                instant(hour = 6, minute = 0),
                instant(hour = 12, minute = 0)
            )
        )
    }

    @Test
    fun `returns 400 if updated show time cannot be scheduled in screening room`() {
        val screeningRoom = screeningRoomRepository.save(screeningRoom())
        showTimeRepository.saveAll(
            listOf(
                showTime(
                    id = UUID.fromString("11111111-c70a-488c-82b5-1bb7f10c9b05"),
                    screeningRoom = screeningRoom,
                    period = ShowTimePeriod(instant(hour = 8, minute = 0), instant(hour = 10, minute = 0))
                ),
                showTime(
                    id = UUID.fromString("22222222-c70a-488c-82b5-1bb7f10c9b05"),
                    screeningRoom = screeningRoom,
                    period = ShowTimePeriod(instant(hour = 11, minute = 0), instant(hour = 12, minute = 0))
                )
            )
        )
        val showTimeUpdate = showTimeUpdate(
            screeningRoomId = screeningRoom.id,
            startTime = instant(hour = 8, minute = 0),
            endTime = instant(hour = 12, minute = 0),
        )

        mockMvc.perform(
            updateShowTimeRequest(
                UUID.fromString("11111111-c70a-488c-82b5-1bb7f10c9b05"),
                showTimeUpdate
            )
        )
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$", `is`("Show time with given id cannot be scheduled.")))
    }

    @Test
    fun `returns 400 if period of updated show time is invalid`() {
        val screeningRoom = screeningRoomRepository.save(screeningRoom())
        val showTime = showTimeRepository.save(showTime(screeningRoom = screeningRoom))
        val showTimeUpdate = showTimeUpdate(
            screeningRoomId = screeningRoom.id,
            startTime = instant(hour = 10),
            endTime = instant(hour = 8)
        )

        mockMvc.perform(updateShowTimeRequest(showTime.id, showTimeUpdate))
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$", `is`("Start time should be before end time.")))
    }

    @Test
    fun `returns 400 if price of updated show time is invalid`() {
        val screeningRoom = screeningRoomRepository.save(screeningRoom())
        val showTime = showTimeRepository.save(showTime(screeningRoom = screeningRoom))
        val showTimeUpdate = showTimeUpdate(
            screeningRoomId = screeningRoom.id,
            price = BigDecimal.ZERO
        )

        mockMvc.perform(updateShowTimeRequest(showTime.id, showTimeUpdate))
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$", `is`("Money must be positive.")))
    }

    @Test
    fun `returns 404 if show time does not exist`() {
        val screeningRoom = screeningRoomRepository.save(screeningRoom())
        val showTimeUpdate = showTimeUpdate(screeningRoomId = screeningRoom.id)

        mockMvc.perform(
            updateShowTimeRequest(
                UUID.fromString("eab65091-cb78-413b-abc8-cbc68ee1c6bc"),
                showTimeUpdate
            )
        )
            .andExpect(status().isNotFound)
            .andExpect(jsonPath("$", `is`("Show time with given id does not exist.")))
    }

    private fun updateShowTimeRequest(showTimeId: UUID, showTimeUpdateDto: ShowTimeUpdateDto) =
        put("/show-times/$showTimeId")
            .contentType(MediaType.APPLICATION_JSON)
            .content(convertDtoToJson(showTimeUpdateDto))

    private fun showTimeUpdate(
        screeningRoomId: UUID = screeningRoom().id,
        startTime: Instant = instant(hour = 8),
        endTime: Instant = instant(hour = 9),
        price: BigDecimal = BigDecimal.valueOf(100),
        movieId: UUID = UUID.fromString("7110ad92-b80c-4f26-b4be-ee1ec6256c35")
    ) = ShowTimeUpdateDto(screeningRoomId, startTime, endTime, price, movieId)
}
