package com.cinema.screeningroom

import com.cinema.AbstractIntegrationTest
import org.junit.jupiter.api.Test
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class ScreeningRoomControllerTest : AbstractIntegrationTest() {
    @Test
    fun test1() {
        mockMvc.perform(
            post("/screening-rooms/room-id/show-times/c4ef0c3f-9825-491e-832c-f4e26e3ee7e4")
        ).andExpect(status().isOk)
    }
}
