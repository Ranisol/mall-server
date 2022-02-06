package com.narea.mall.repository

import com.narea.mall.dto.OrderParams
import com.narea.mall.entity.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageRequest
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import kotlin.test.assertEquals


@SpringBootTest
class OrderRepositoryTest(
    @Autowired private val orderRepository: OrderRepository,
    @Autowired private val userRepository: UserRepository
) {

    @Test
    @Transactional
    fun `findOrdersByParamsAndUserId(1) - OrderParam with orderStatus` () {

        // given
        val user = userRepository.save(User(email = "aamm10@naver.com"))
        orderRepository.saveAll(
            listOf(
                Orders(user = user, orderStatus = OrderStatus.READY),
                Orders(user = user, orderStatus = OrderStatus.ORDER), Orders(user = user, orderStatus = OrderStatus.ORDER),
                Orders(user = user, orderStatus = OrderStatus.CANCEL),
                Orders(user = user, orderStatus = OrderStatus.REFUND), Orders(user = user, orderStatus = OrderStatus.REFUND), Orders(user = user, orderStatus = OrderStatus.REFUND)
            )
        )
        val paramsReady = OrderParams(orderStatus = OrderStatus.READY)
        val paramsOrder = OrderParams(orderStatus = OrderStatus.ORDER)
        val paramsCancel = OrderParams(orderStatus = OrderStatus.CANCEL)
        val paramsRefund = OrderParams(orderStatus = OrderStatus.REFUND)
        val pageableOption1 = PageRequest.of(0, 10)

        // when
        val readyResult = orderRepository.findOrdersByParamsAndUserId(user.id, paramsReady, pageableOption1)
        val orderResult = orderRepository.findOrdersByParamsAndUserId(user.id, paramsOrder, pageableOption1)
        val cancelResult = orderRepository.findOrdersByParamsAndUserId(user.id, paramsCancel, pageableOption1)
        val refundResult = orderRepository.findOrdersByParamsAndUserId(user.id, paramsRefund, pageableOption1)

        // then
        assertAll(
            { assertEquals(1, readyResult.content.size) },
            { assertEquals(2, orderResult.content.size) },
            { assertEquals(1, cancelResult.content.size) },
            { assertEquals(3, refundResult.content.size) },
        )
    }

    @Test
    @Transactional
    fun `findOrdersByParamsAndUserId(2) - OrderParams with Date`() {
        // given
        val user = userRepository.save(User(email = "aamm10@naver.com"))
        orderRepository.saveAll(arrayListOf(
            Orders(user = user), Orders(user = user), Orders(user = user)
        ))

        // when
        val result = orderRepository.findOrdersByParamsAndUserId(
            user.id,
            OrderParams(
                startDate = LocalDate.now().apply { minusDays(1) },
                endDate = LocalDate.now().apply { plusDays(1) }
            ), PageRequest.of(0, 10)
        )

        // then
        assertEquals(3, result.content.size)
    }

    @Test
    @Transactional
    fun `findOrdersByParamsAndUserId(3) - OrderParas with DeliveryStatus`() {
        // given
        val user = userRepository.save(User(email = "aamm10@naver.com"))
        orderRepository.saveAll(arrayListOf(
            Orders(user = user).apply {
                delivery = Delivery(status = DeliveryStatus.PREPARED, order = this)
            },
            Orders(user = user).apply {
                delivery = Delivery(status = DeliveryStatus.COMPLETED, order = this)
            },
            Orders(user = user).apply {
                delivery = Delivery(status = DeliveryStatus.SHIPPING, order = this)
            },
            Orders(user = user).apply {
                delivery = Delivery(status = DeliveryStatus.SHIPPING, order = this)
            }
        ))

        // when
        val resultOfPrepared = orderRepository.findOrdersByParamsAndUserId(
            user.id,
            OrderParams(deliveryStatus = DeliveryStatus.PREPARED),
            PageRequest.of(0, 10)
        )
        val resultOfCompleted = orderRepository.findOrdersByParamsAndUserId(
            user.id,
            OrderParams(deliveryStatus = DeliveryStatus.COMPLETED),
            PageRequest.of(0, 10)
        )
        val resultOfShipping = orderRepository.findOrdersByParamsAndUserId(
            user.id,
            OrderParams(deliveryStatus = DeliveryStatus.SHIPPING),
            PageRequest.of(0, 10)
        )

        // then
        assertAll("check size",
            { assertEquals(1, resultOfPrepared.content.size) },
            { assertEquals(1, resultOfCompleted.content.size) },
            { assertEquals(2, resultOfShipping.content.size) },
         )
        assertAll("check status",
            { assertEquals(DeliveryStatus.PREPARED, resultOfPrepared.content[0].delivery?.status) },
            { assertEquals(DeliveryStatus.COMPLETED, resultOfCompleted.content[0].delivery?.status) },
            { assertEquals(DeliveryStatus.SHIPPING, resultOfShipping.content[0].delivery?.status) }
        )
    }

    @Test
    @Transactional
    fun `test one`() {
        // given
        val user = userRepository.save(User(email = "aamm10@naver.com"))
        orderRepository.saveAll(arrayListOf(
            Orders(user = user).apply {
                delivery = Delivery(status = DeliveryStatus.PREPARED, order = this)
            },
        ))
        val result1 = orderRepository.findOrdersByParamsAndUserId(
            user.id,
            OrderParams(),
            PageRequest.of(0, 1)
        )
        val result2 = orderRepository.findOrdersByParamsAndUserId(
            user.id,
            OrderParams(),
            PageRequest.of(0, 10)
        )
        println(result1.content[0] == result2.content[0]) // querydsl의 쿼리는 무조건 추가로 나간다. 하지만 동등함

    }

}