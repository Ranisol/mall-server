package com.narea.mall.controller
import com.narea.mall.auth.AUTHORIZATION_HEADER
import com.narea.mall.dto.BasketItemAddRequest
import com.narea.mall.service.BasketService
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@Tag(name = "basket", description = "장바구니 api")
@RestController
@RequestMapping("/api/v1/users/{userId}/baskets")
@SecurityRequirement(name = AUTHORIZATION_HEADER)
class BasketController(
    private val basketService: BasketService
) {
    @PreAuthorize("@authService.hasAuthByUserId(#userId)")
    @GetMapping
    fun getBasket(
        @PathVariable userId: Long
    ) = basketService.getBasketResponse(userId)

    @PreAuthorize("@authService.hasAuthByUserId(#userId)")
    @PutMapping
    fun addItemToBasket(
        @PathVariable userId: Long,
        @RequestBody @Valid basketItemAddRequest: BasketItemAddRequest
    ) = basketService.addBasketItem(userId, basketItemAddRequest)

    @PreAuthorize("@authService.hasAuthByUserId(#userId)")
    @PutMapping("/{basketItemId}")
    fun updateItemToBasket(
        @PathVariable userId: Long,
        @PathVariable basketItemId: Long,
        @RequestBody count: Int
    ) = basketService.updateBasketItem(userId, basketItemId, count)

    @PreAuthorize("@authService.hasAuthByUserId(#userId)")
    @DeleteMapping("/{basketItemId}")
    fun deleteItemToBasket(
        @PathVariable userId: Long,
        @PathVariable basketItemId: Long
    ) = basketService.removeBasketItem(basketItemId)


}