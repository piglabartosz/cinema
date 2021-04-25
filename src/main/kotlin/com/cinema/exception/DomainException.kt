package com.cinema.exception

class DomainException(override val message: String) : RuntimeException(message)
