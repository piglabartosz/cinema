package com.cinema.screeningroom

import java.util.*
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.OneToMany

@Entity
data class ScreeningRoom(
    @Id val id: UUID,
    val name: String,
    val placesCount: Int,
    @OneToMany(mappedBy = "screeningRoom") val showTimes: List<ShowTime>
) {
    fun isConflictedWithShowTime(showTime: ShowTime): Boolean =
        showTimes.any { it.id != showTime.id && it.period.conflictsWith(showTime.period) }
}
