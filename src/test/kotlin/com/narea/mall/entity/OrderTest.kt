package com.narea.mall.entity

import com.narea.mall.exception.BadRequestException
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class OrderTest {
    companion object {
        private val item1 = Item(
            price = 1_000,
            inventory = 100
        )
        private val item2 = Item(
            price = 2_000,
            inventory = 200
        )
        private val orderItem1 = OrdersItem(
            item = item1,
            count = 10
        )
        private val orderItem2 = OrdersItem(
            item = item2,
            count = 5
        )

    }

    @Test fun `test getTotalPrice`(){
        val order = Orders(
            orderItems = arrayListOf(orderItem1, orderItem2)
        )
        assertEquals(20_000, order.getTotalPrice())
    }

    @Test fun `test order`(){
        val order = Orders(
            orderItems = arrayListOf(orderItem1, orderItem2)
        )
        order.order()
        assertEquals(OrderStatus.ORDER, order.orderStatus)
        assertNotNull(order.delivery)
        assertAll(
            "Consume inventory",
            { assertEquals(90, item1.inventory) },
            { assertEquals(195, item2.inventory) }
        )
    }

    @Test fun `Can only be ordered when orderStatus is READY (1)`() {
        val order = Orders(
            orderItems = arrayListOf(orderItem1, orderItem2)
        )
        assertThrows<BadRequestException> {
            // given
            order.order()
            // when
            order.order()
        }
    }
    @Test fun `Can only be ordered when orderStatus is READY (2)`() {
        val order = Orders(
            orderItems = arrayListOf(orderItem1, orderItem2)
        )
        assertThrows<BadRequestException> {
            // given
            order.order()
            order.cancel()
            // when
            order.order()
        }
    }
    @Test fun `Can only be ordered when orderStatus is READY (3)`() {
        val order = Orders(
            orderItems = arrayListOf(orderItem1, orderItem2)
        )
        assertThrows<BadRequestException> {
            // given
            order.order()
            order.cancel()
            order.refund()
            // when
            order.order()
        }
    }


    @Test fun `test cancel`(){
        val order = Orders(
            orderItems = arrayListOf(orderItem1, orderItem2)
        )
        order.order()
        order.cancel()
        assertEquals(OrderStatus.CANCEL, order.orderStatus)
        assertAll(
            "Restore inventory",
            { assertEquals(100, item1.inventory) },
            { assertEquals(200, item2.inventory) }
        )
    }

    @Test fun `Can only be cancelled when orderStatus is ORDER (1)`() {
        val order = Orders(
            orderItems = arrayListOf(orderItem1, orderItem2)
        )
        assertThrows<BadRequestException> {
            // when
            order.cancel()
        }
    }
    @Test fun `Can only be cancelled when orderStatus is ORDER (2)`() {
        val order = Orders(
            orderItems = arrayListOf(orderItem1, orderItem2)
        )
        assertThrows<BadRequestException> {
            // when
            order.order()
            order.cancel()
            // then
            order.cancel()
        }
    }
    @Test fun `Can only be cancelled when orderStatus is ORDER (3)`() {
        val order = Orders(
            orderItems = arrayListOf(orderItem1, orderItem2)
        )
        assertThrows<BadRequestException> {
            // given
            order.order()
            order.cancel()
            order.refund()
            // when
            order.cancel()
        }
    }

    @Test fun `refund`(){
        val order = Orders(
            orderItems = arrayListOf(orderItem1, orderItem2)
        )
        order.order()
        order.cancel()
        order.refund()
        assertEquals(OrderStatus.REFUND, order.orderStatus)
    }
}