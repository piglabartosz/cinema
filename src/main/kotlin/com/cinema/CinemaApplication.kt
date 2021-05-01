package com.cinema

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@SpringBootApplication
@EnableFeignClients
class CinemaApplication

fun main(args: Array<String>) {
    runApplication<CinemaApplication>(*args)
}
