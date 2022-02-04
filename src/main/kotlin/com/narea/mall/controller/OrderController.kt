package com.narea.mall.controller

import com.narea.mall.dto.OrderCreateRequest
import com.narea.mall.repository.custom.OrderParams
import com.narea.mall.service.OrderService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.query.Param
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.*

@Tag(name = "order", description = "주문 api")
@RestController
@RequestMapping("/api/v1/users/{userId}/orders")
class OrderController(
    private val orderService: OrderService
) {
    @Operation(summary = "주문 조회", description = "")
    @GetMapping("/{orderId}")
    fun getOrder(
        @PathVariable userId: Long,
        @PathVariable orderId: Long
    ) = orderService.getOrder(orderId)

    @Operation(summary = "주문 목록 조회", description = "")
    @GetMapping
    fun getOrders(
        @PathVariable userId: Long,
        @PageableDefault
        @Parameter(hidden = true)
        pageable: Pageable,
        @Parameter
        orderParams: OrderParams
    ) = orderService.getOrdersResponse(userId, orderParams ,pageable)

    @Operation(summary = "장바구니 통해서 주문 생성", description = "")
    @PostMapping("/basket")
    fun createOrderByBasket(
        @PathVariable userId: Long
    ) = orderService.createOrder(userId)

    @Operation(summary = "상품 통해서 직접 주문 생성", description = "")
    @PostMapping
    fun createOrder(
        @PathVariable userId: Long,
        @RequestBody orderCreateRequest: OrderCreateRequest
    ) = orderService.createOrder(userId, orderCreateRequest)

   @Operation(summary = "주문 목록 삭제", description = "")
   @DeleteMapping("/{orderId}")
   fun deleteOrder(
       @PathVariable userId: Long,
       @PathVariable orderId: Long
   ) = orderService.deleteOrder(orderId)

    @Operation(summary = "주문 완료", description = "")
    @PutMapping("/{orderId}/complete")
    fun orderComplete(
        @PathVariable userId: Long,
        @PathVariable orderId: Long
    ) = orderService.orderComplete(userId, orderId)

    @Operation(summary = "주문 취소 요청", description = "")
    @PutMapping("/{orderId}/cancel")
    fun cancelOrder(
        @PathVariable userId: Long,
        @PathVariable orderId: Long
    ) = orderService.cancelOrder(orderId)

    @Operation(summary = "주문 환불 완료", description = "")
    @PutMapping("/{orderId}/refund")
    fun refundOrder(
        @PathVariable userId: Long,
        @PathVariable orderId: Long
    ) = orderService.refundOrder(orderId)
}