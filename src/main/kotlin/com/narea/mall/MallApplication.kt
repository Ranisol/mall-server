package com.narea.mall

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import


@Import(
	CoreScanConfig::class
)
@SpringBootApplication
class MallApplication
fun main(args: Array<String>) {
	runApplication<MallApplication>(*args)
}
