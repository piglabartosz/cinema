package com.cinema.exception

class DomainObjectNotFoundException(override val message: String) : RuntimeException(message)
