package com.cinema.utils

import com.cinema.exception.DomainException
import java.math.BigDecimal
import javax.persistence.Embeddable

@Embeddable
data class Money(val value: BigDecimal) {
    init {
        if (value.compareTo(BigDecimal.ZERO) < 1) {
            throw DomainException("Money must be positive.")
        }
    }
}
