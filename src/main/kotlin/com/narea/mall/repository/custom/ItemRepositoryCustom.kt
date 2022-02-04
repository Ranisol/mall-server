package com.narea.mall.repository.custom

import com.narea.mall.dto.ItemResponse
import com.narea.mall.entity.*
import com.narea.mall.utils.EMPTY_STRING
import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import org.hibernate.criterion.Projection
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

data class ItemParams (
    var itemName: String = EMPTY_STRING,
)

@Repository
interface ItemRepositoryCustom {
    fun findByItemParams(params: ItemParams, pageable: Pageable): Page<ItemResponse>
}

class ItemRepositoryImpl (
    private val jpaQueryFactory: JPAQueryFactory,
): ItemRepositoryCustom {
    override fun findByItemParams(params: ItemParams, pageable: Pageable): Page<ItemResponse> {
        TODO("Not yet implemented")
    }

}