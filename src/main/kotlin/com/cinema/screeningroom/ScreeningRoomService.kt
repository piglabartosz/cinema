package com.cinema.screeningroom

import org.springframework.stereotype.Service

@Service
class ScreeningRoomService(
    private val screeningRoomRepository: ScreeningRoomRepository,
    private val showTimeRepository: ShowTimeRepository
) {
    fun updateShowTime() {
        throw NotImplementedError()
    }
}
