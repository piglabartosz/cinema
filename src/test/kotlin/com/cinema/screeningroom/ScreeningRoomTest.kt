package com.cinema.screeningroom

import com.cinema.screeningroom.Instants.instant
import com.cinema.screeningroom.ScreeningRooms.screeningRoom
import com.cinema.screeningroom.ShowTimes.showTime
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.TestFactory
import java.util.*

class ScreeningRoomTest {
    @TestFactory
    fun `returns false if conflict does not occur`(): Iterable<DynamicTest> {
        val screeningRoom = screeningRoom(
            showTimes = listOf(
                showTime(
                    id = UUID.fromString("11111111-55cd-46f8-870f-66b0aa0e2f20"),
                    period = ShowTimePeriod(
                        instant(dayOfMonth = 5, hour = 18, minute = 0),
                        instant(dayOfMonth = 5, hour = 21, minute = 0)
                    )
                ),
                showTime(
                    id = UUID.fromString("22222222-55cd-46f8-870f-66b0aa0e2f20"),
                    period = ShowTimePeriod(
                        instant(dayOfMonth = 6, hour = 12, minute = 0),
                        instant(dayOfMonth = 6, hour = 14, minute = 0)
                    )
                )
            )
        )

        return listOf(
            showTime(
                id = UUID.fromString("11111111-55cd-46f8-870f-66b0aa0e2f20"),
                period = ShowTimePeriod(
                    instant(dayOfMonth = 1, hour = 0, minute = 0),
                    instant(dayOfMonth = 6, hour = 11, minute = 59)
                )
            ),
            showTime(
                id = UUID.fromString("11111111-55cd-46f8-870f-66b0aa0e2f20"),
                period = ShowTimePeriod(
                    instant(dayOfMonth = 6, hour = 14, minute = 1),
                    instant(dayOfMonth = 10, hour = 0, minute = 0)
                )
            )
        ).map {
            dynamicTest("returns false when conflicts does not occur") {
                assertThat(screeningRoom.isConflictedWithShowTime(it)).isFalse
            }
        }
    }

    @TestFactory
    fun `returns true if conflict occurs`(): Iterable<DynamicTest> {
        val screeningRoom = screeningRoom(
            showTimes = listOf(
                showTime(
                    id = UUID.fromString("11111111-55cd-46f8-870f-66b0aa0e2f20"),
                    period = ShowTimePeriod(
                        instant(dayOfMonth = 5, hour = 18, minute = 0),
                        instant(dayOfMonth = 5, hour = 21, minute = 0)
                    )
                ),
                showTime(
                    id = UUID.fromString("22222222-55cd-46f8-870f-66b0aa0e2f20"),
                    period = ShowTimePeriod(
                        instant(dayOfMonth = 6, hour = 12, minute = 0),
                        instant(dayOfMonth = 6, hour = 14, minute = 0)
                    )
                ),
                showTime(
                    id = UUID.fromString("33333333-55cd-46f8-870f-66b0aa0e2f20"),
                    period = ShowTimePeriod(
                        instant(dayOfMonth = 6, hour = 16, minute = 0),
                        instant(dayOfMonth = 6, hour = 18, minute = 0)
                    )
                )
            )
        )

        return listOf(
            showTime(
                id = UUID.fromString("11111111-55cd-46f8-870f-66b0aa0e2f20"),
                period = ShowTimePeriod(
                    instant(dayOfMonth = 1, hour = 0, minute = 0),
                    instant(dayOfMonth = 6, hour = 23, minute = 59)
                )
            ),
            showTime(
                id = UUID.fromString("22222222-55cd-46f8-870f-66b0aa0e2f20"),
                period = ShowTimePeriod(
                    instant(dayOfMonth = 5, hour = 21, minute = 0),
                    instant(dayOfMonth = 6, hour = 14, minute = 0)
                )
            )
        ).map {
            dynamicTest("returns true when conflicts does not occur") {
                assertThat(screeningRoom.isConflictedWithShowTime(it)).isTrue
            }
        }
    }
}
