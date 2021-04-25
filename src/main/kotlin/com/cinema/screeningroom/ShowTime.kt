package com.cinema.screeningroom

import com.cinema.exception.DomainException
import com.cinema.utils.Money
import java.time.Instant
import java.util.*
import javax.persistence.*

@Entity
data class ShowTime(
    @Id val id: UUID,

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "screening_room_id", nullable = false)
    val screeningRoom: ScreeningRoom,

    @Embedded
    val period: ShowTimePeriod,

    @Embedded
    val price: Money,

    val movieId: UUID
)

@Embeddable
data class ShowTimePeriod(
    val startTime: Instant,
    val endTime: Instant
) {
    init {
        if (!startTime.isBefore(endTime)) {
            throw DomainException("Start time should be before end time.")
        }
    }

    fun conflictsWith(period: ShowTimePeriod): Boolean =
        containsTime(period.startTime) || containsTime(period.endTime) || startsLaterAndEndsEarlier(period)

    private fun containsTime(time: Instant): Boolean =
        !time.isBefore(startTime) && !time.isAfter(endTime)

    private fun startsLaterAndEndsEarlier(period: ShowTimePeriod): Boolean =
        period.startTime.isBefore(startTime) && period.endTime.isAfter(endTime)
}
