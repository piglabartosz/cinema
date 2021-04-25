package com.cinema.movie

import org.springframework.data.repository.CrudRepository
import java.util.*

interface MovieRepository: CrudRepository<Movie, UUID>
