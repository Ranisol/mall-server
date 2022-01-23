package com.narea.mall.controller

import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "order")
@RestController
@RequestMapping("/api/v1/users/{userId}/orders")
class OrderController {
}