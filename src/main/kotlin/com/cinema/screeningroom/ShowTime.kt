package com.cinema.screeningroom

import java.math.BigDecimal
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
    val period: Period,

    @Embedded
    val price: Money,

    val moveId: String
)

@Embeddable
data class Period(
    val startTime: Instant,
    val endTime: Instant
)

@Embeddable
class Money(
    val value: BigDecimal
)
