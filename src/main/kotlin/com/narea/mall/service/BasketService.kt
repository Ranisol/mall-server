package com.narea.mall.service

import com.narea.mall.dto.*
import com.narea.mall.entity.Basket
import com.narea.mall.entity.BasketItem
import com.narea.mall.exception.NotFoundException
import com.narea.mall.repository.BasketItemRepository
import com.narea.mall.repository.BasketRepository
import com.narea.mall.repository.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.lang.RuntimeException
import java.util.*


@Service
@Transactional(readOnly = true)
class BasketService (
    private val basketRepository: BasketRepository,
    private val basketItemRepository: BasketItemRepository,
    private val userRepository: UserRepository, // only for createBasket
    private val userService: UserService,
    private val itemService: ItemService
        ) {
    @Transactional
    fun createBasket(userId: Long) = userRepository.save( //
        userService.getUser(userId).apply {
            basket = Basket(
                user = this
            )
        }
    )
    fun getBasket(userId: Long): Basket =
        basketRepository.findByUserId(userId) ?: throw NotFoundException("userId:$userId basket does not exist, create basket first")
    fun getBasketItem(basketItemId: Long): BasketItem =
        basketItemRepository.findByIdOrNull(basketItemId) ?: throw NotFoundException("basketItemId:$basketItemId does not exist")
    fun getBasketResponse(userId: Long): BasketResponse = getBasket(userId).toResponse()
    @Transactional
    fun addBasketItem(userId: Long, basketAddItemRequest: BasketItemAddRequest): BasketResponse =
        getBasket(userId).apply {
            basketItems.add(
                BasketItem(
                    basket = this,
                    count = basketAddItemRequest.count,
                    item = itemService.getItem(basketAddItemRequest.itemId)
                )
            )
        }.also { basket ->
            basketRepository.save(basket)
        }.toResponse()

    @Transactional
    fun removeBasketItem(basketItemId: Long) =
        basketItemRepository.delete(
            getBasketItem(basketItemId)
        )
    @Transactional
    fun updateBasketItem(userId: Long, basketItemId: Long, count: Int) =
        getBasketItem(basketItemId).let { basketItem ->
            basketItem.count = count
            basketItemRepository.save(basketItem)
        }.toResponse()
}