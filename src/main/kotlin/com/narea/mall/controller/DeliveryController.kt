package com.narea.mall.controller

import com.narea.mall.auth.AUTHORIZATION_HEADER
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "delivery")
@RestController
@RequestMapping("/api/v1/users/{userId}/orders/{orderId}/delivery")
@SecurityRequirement(name = AUTHORIZATION_HEADER)
class DeliveryController {
}