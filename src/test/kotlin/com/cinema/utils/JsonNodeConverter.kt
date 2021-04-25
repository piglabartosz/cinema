package com.cinema.utils

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

object JsonNodeConverter {
    private val mapper = jacksonObjectMapper()

    fun toJsonNode(payload: String): JsonNode = mapper.readTree(payload)
}
