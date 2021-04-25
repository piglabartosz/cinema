package com.cinema.screeningroom

import com.cinema.exception.DomainException
import com.cinema.screeningroom.Instants.instant
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatCode
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class ShowTimePeriodTest {
    @Nested
    inner class ObjectCreation {
        @Test
        fun `creates period when start time is before end time`() {
            assertThatCode {
                ShowTimePeriod(
                    startTime = instant(hour = 8, minute = 0),
                    endTime = instant(hour = 9, minute = 0)
                )
            }.doesNotThrowAnyException()
        }

        @Test
        fun `throws exception when start time is equal to end time`() {
            assertThatCode {
                ShowTimePeriod(
                    startTime = instant(hour = 8, minute = 0),
                    endTime = instant(hour = 8, minute = 0)
                )
            }
                .isInstanceOf(DomainException::class.java)
                .hasMessage("Start time should be before end time.")
        }

        @Test
        fun `throws exception period when start time is greater than end time`() {
            assertThatCode {
                ShowTimePeriod(
                    startTime = instant(hour = 9, minute = 0),
                    endTime = instant(hour = 8, minute = 0)
                )
            }
                .isInstanceOf(DomainException::class.java)
                .hasMessage("Start time should be before end time.")
        }
    }

    @Nested
    inner class ConflictsWith {
        private val initialPeriodFrom8To10 = ShowTimePeriod(
            startTime = instant(hour = 8, minute = 0),
            endTime = instant(hour = 10, minute = 0)
        )

        @Test
        fun `does not conflict when period starts after initial period end`() {
            val periodFrom11To12 = ShowTimePeriod(
                startTime = instant(hour = 11, minute = 0),
                endTime = instant(hour = 12, minute = 0)
            )

            assertThat(initialPeriodFrom8To10.conflictsWith(periodFrom11To12)).isFalse
        }

        @Test
        fun `does not conflict when period ends before initial period start`() {
            val periodFrom6To7 = ShowTimePeriod(
                startTime = instant(hour = 6, minute = 0),
                endTime = instant(hour = 7, minute = 0)
            )

            assertThat(initialPeriodFrom8To10.conflictsWith(periodFrom6To7)).isFalse
        }

        @Test
        fun `conflicts when start time is within initial period`() {
            val periodFrom9To11 = ShowTimePeriod(
                startTime = instant(hour = 9, minute = 0),
                endTime = instant(hour = 11, minute = 0)
            )

            assertThat(initialPeriodFrom8To10.conflictsWith(periodFrom9To11)).isTrue
        }

        @Test
        fun `conflicts when start time of period is equal to start time of initial period`() {
            val periodFrom8To11 = ShowTimePeriod(
                startTime = instant(hour = 8, minute = 0),
                endTime = instant(hour = 11, minute = 0)
            )

            assertThat(initialPeriodFrom8To10.conflictsWith(periodFrom8To11)).isTrue
        }

        @Test
        fun `conflicts when end time is within initial period`() {
            val periodFrom7To9 = ShowTimePeriod(
                startTime = instant(hour = 7, minute = 0),
                endTime = instant(hour = 9, minute = 0)
            )

            assertThat(initialPeriodFrom8To10.conflictsWith(periodFrom7To9)).isTrue
        }

        @Test
        fun `conflicts when end time of period is equal to end time of initial period`() {
            val periodFrom7To10 = ShowTimePeriod(
                startTime = instant(hour = 7, minute = 0),
                endTime = instant(hour = 10, minute = 0)
            )

            assertThat(initialPeriodFrom8To10.conflictsWith(periodFrom7To10)).isTrue
        }

        @Test
        fun `conflicts when both start time and end time is within initial period`() {
            val periodWithinInitialPeriod = ShowTimePeriod(
                startTime = instant(hour = 8, minute = 30),
                endTime = instant(hour = 9, minute = 30)
            )

            assertThat(initialPeriodFrom8To10.conflictsWith(periodWithinInitialPeriod)).isTrue
        }

        @Test
        fun `conflicts when period starts earlier than initial period and ends later than initial period`() {
            val allDayPeriod = ShowTimePeriod(
                startTime = instant(hour = 0, minute = 0),
                endTime = instant(hour = 23, minute = 59)
            )

            assertThat(initialPeriodFrom8To10.conflictsWith(allDayPeriod)).isTrue
        }
    }
}
