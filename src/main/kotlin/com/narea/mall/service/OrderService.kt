package com.narea.mall.service

import com.narea.mall.dto.OrderCreateRequest
import com.narea.mall.dto.OrderParams
import com.narea.mall.dto.toResponse
import com.narea.mall.entity.*
import com.narea.mall.exception.BadRequestException
import com.narea.mall.exception.NotFoundException
import com.narea.mall.repository.OrderRepository
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class OrderService(
    private val orderRepository: OrderRepository,
    private val userService: UserService,
    private val itemService: ItemService,
    private val basketService: BasketService
) {

    fun getOrder(orderId: Long) = orderRepository.findByIdOrNull(orderId) ?: throw NotFoundException("orderId: $orderId does not exist")

    fun getOrderResponse(orderId: Long) = getOrder(orderId).toResponse()

    fun getOrdersResponse(userId: Long, orderParams: OrderParams, pageable: Pageable) =
        orderRepository.findOrdersByParamsAndUserId(userId, orderParams ,pageable).map { it.toResponse() }


    // 장바구니 통해서 결제
    @Transactional
    fun createOrder(userId: Long) =
        basketService.getBasket(userId).let { basket ->
            val orders = Orders()
            val ordersItems =  basket.basketItems.map { basketItem ->
                OrdersItem(
                    count = basketItem.count,
                    item = basketItem.item,
                    orders = orders,
                )
            }
            orders.apply {
                user = userService.getUser(userId)
                orderItems = ordersItems
            }
        }.let { order ->
            orderRepository.save(order)
        }.toResponse()

    // 바로 결제
    @Transactional
    fun createOrder(userId: Long, orderCreateRequest: OrderCreateRequest) =
        orderCreateRequest.let { request ->
            val orders = Orders()
            val orderItem = OrdersItem(
                count = request.count,
                item = itemService.getItem(request.itemId)
            )
            orders.apply {
                user = userService.getUser(userId)
                orderItems = arrayListOf(orderItem)
            }
        }.let { order ->
            order.order()
            basketService.clearBasket(userId)
            orderRepository.save(order)
        }.toResponse()

    @Transactional
    fun deleteOrder(orderId: Long) {
        val orders = getOrder(orderId)
        if(orders.orderStatus != OrderStatus.READY) throw BadRequestException("이미 결제 완료된 주문에 대해서는 삭제할 수 없습니다.")
        orderRepository.delete(orders)
    }

    // 결제 생성 후 => 실제 결제가 이루어지는 부분. 결제가 완료되면 order 엔티티의 order 호출
    @Transactional
    fun orderComplete(userId: Long, orderId: Long) =
        getOrder(orderId).let { order ->
            order.order()
            orderRepository.save(order)
            order.toResponse()
        }


    @Transactional
    fun cancelOrder(orderId: Long) =
        getOrder(orderId).let { order ->
            order.cancel()
            orderRepository.save(order)
            order.toResponse()
        }

    // 환불 추가 절차 필요
    @Transactional
    fun refundOrder(orderId: Long) =
        getOrder(orderId).let{ order ->
            order.refund()
            orderRepository.save(order)
            order.toResponse()
        }

}