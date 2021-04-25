package com.cinema.utils

import com.cinema.exception.DomainException
import org.assertj.core.api.Assertions.assertThatCode
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class MoneyTest {
    @Test
    fun `creates money when value is positive`() {
        assertThatCode { Money(BigDecimal.valueOf(1)) }
            .doesNotThrowAnyException()
    }

    @Test
    fun `throws exception when value is zero`() {
        assertThatCode { Money(BigDecimal.ZERO) }
            .isInstanceOf(DomainException::class.java)
            .hasMessage("Money must be positive.")
    }

    @Test
    fun `throws exception when value is 0,0`() {
        assertThatCode { Money(BigDecimal("0.0")) }
            .isInstanceOf(DomainException::class.java)
            .hasMessage("Money must be positive.")
    }

    @Test
    fun `throws exception when value is negative`() {
        assertThatCode { Money(BigDecimal(-1)) }
            .isInstanceOf(DomainException::class.java)
            .hasMessage("Money must be positive.")
    }
}
