package com.narea.mall.controller

import com.narea.mall.auth.AUTHORIZATION_HEADER
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@Tag(name = "delivery")
@RestController
@RequestMapping("/api/v1/users/{userId}/orders/{orderId}/delivery")
@SecurityRequirement(name = AUTHORIZATION_HEADER)
class DeliveryController {
    @Operation(summary = "운송장 번호 등록")
    @PreAuthorize("@authService.hasAdminAuth(#userId)")
    @PutMapping
    fun addWaybill(
        @PathVariable orderId: String,
        @PathVariable userId: String
    ) {
        // TODO
    }
}