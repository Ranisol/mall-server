package com.narea.mall.backend.api.controller

import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "basket", description = "장바구니 api")
@RestController
@RequestMapping("/api/v1/users/{userId}/baskets")
class BasketController {
}