package com.narea.mall.repository

import com.narea.mall.entity.Item
import com.narea.mall.entity.ItemCategory
import com.narea.mall.entity.ItemFile
import com.narea.mall.repository.custom.ItemRepositoryCustom
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository



@Repository // jpa exception 사용
interface ItemRepository: JpaRepository<Item, Long>, ItemRepositoryCustom



@Repository
interface ItemFileRepository: JpaRepository<ItemFile, Long>

@Repository
interface ItemCategoryRepository: JpaRepository<ItemCategory, Long>