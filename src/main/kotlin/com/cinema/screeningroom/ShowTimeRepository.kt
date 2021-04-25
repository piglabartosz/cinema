package com.cinema.screeningroom

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ShowTimeRepository : CrudRepository<ShowTime, UUID>
