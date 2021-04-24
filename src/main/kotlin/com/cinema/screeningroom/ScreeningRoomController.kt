package com.cinema.screeningroom

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ScreeningRoomController(private val screeningRoomService: ScreeningRoomService) {
    @PostMapping("/screening-rooms/{roomId}/show-times/{showTimeId}")
    fun updateShowTime() {
        screeningRoomService.updateShowTime()
    }
}
