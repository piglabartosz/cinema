package com.cinema.screeningroom

import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class ScreeningRoomController(private val screeningRoomService: ScreeningRoomService) {
    @PostMapping("/screening-rooms/{roomId}/show-times/{showTimeId}")
    fun updateShowTime(@PathVariable roomId: String, @PathVariable showTimeId: UUID) {
        screeningRoomService.updateShowTime()
    }
}
