package com.cinema.screeningroom

import com.cinema.exception.DomainException
import com.cinema.exception.DomainObjectNotFoundException
import com.cinema.utils.Money
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.time.Instant
import java.util.*

@Service
class ShowTimeUpdateService(
    private val screeningRoomRepository: ScreeningRoomRepository,
    private val showTimeRepository: ShowTimeRepository
) {
    @Transactional
    fun updateShowTime(showTimeId: UUID, showTimeUpdate: ShowTimeUpdateDto): ShowTimeDto {
        val screeningRoom = screeningRoomRepository
            .findById(showTimeUpdate.screeningRoomId)
            .orElseThrow { DomainObjectNotFoundException("Screening room with given id does not exist.") }

        val oldShowTime = showTimeRepository
            .findById(showTimeId)
            .orElseThrow { DomainObjectNotFoundException("Show time with given id does not exist.") }

        val updatedShowTime = ShowTime(
            id = oldShowTime.id,
            screeningRoom = screeningRoom,
            period = ShowTimePeriod(showTimeUpdate.startTime, showTimeUpdate.endTime),
            price = Money(showTimeUpdate.price),
            movieId = showTimeUpdate.movieId
        )

        if (screeningRoom.isConflictedWithShowTime(updatedShowTime)) {
            throw DomainException("Show time with given id cannot be scheduled.")
        }

        showTimeRepository.save(updatedShowTime)

        return ShowTimeDto.createFromDomainObject(updatedShowTime)
    }
}

data class ShowTimeUpdateDto(
    val screeningRoomId: UUID,
    val startTime: Instant,
    val endTime: Instant,
    val price: BigDecimal,
    val movieId: UUID
)
