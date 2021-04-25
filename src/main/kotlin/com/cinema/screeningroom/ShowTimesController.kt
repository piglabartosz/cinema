package com.cinema.screeningroom

import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/show-times")
class ShowTimesController(private val showTimeUpdateService: ShowTimeUpdateService) {
    @PutMapping("/{showTimeId}")
    fun updateShowTime(
        @PathVariable showTimeId: UUID,
        @RequestBody showTimeUpdateDto: ShowTimeUpdateDto
    ): ShowTimeDto =
        showTimeUpdateService.updateShowTime(showTimeId, showTimeUpdateDto)
}
