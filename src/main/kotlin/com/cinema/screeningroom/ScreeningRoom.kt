package com.cinema.screeningroom

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.OneToMany

@Entity
data class ScreeningRoom(
    @Id val roomName: String,
    val placesCount: Int,
    @OneToMany(mappedBy = "screeningRoom") val showTimes: List<ShowTime>
) {
    fun updateShowTime(showTime: ShowTime) {

    }
}
