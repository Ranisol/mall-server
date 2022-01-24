package com.narea.mall.controller
import com.narea.mall.auth.AUTHORIZATION_HEADER
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@Tag(name = "basket", description = "장바구니 api")
@RestController
@RequestMapping("/api/v1/users/{userId}/baskets")
@SecurityRequirement(name = AUTHORIZATION_HEADER)
class BasketController {
    @GetMapping
    fun createBasket(
        @PathVariable userId: Long,
        @RequestBody @Valid basketRe
    ) {}

    fun addItemToBasket() {}

    fun deleteItemToBasket() {}

    fun deleteBasket() {}
}