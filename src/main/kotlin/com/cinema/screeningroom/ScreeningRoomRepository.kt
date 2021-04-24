package com.cinema.screeningroom

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ScreeningRoomRepository : CrudRepository<ScreeningRoom, String>
