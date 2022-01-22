package com.narea.mall.repository

import com.narea.mall.entity.Item
import com.narea.mall.entity.ItemFile
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository // jpa exception 사용
interface ItemRepository: JpaRepository<Item, Long> {

}

@Repository
interface ItemFileRepository: JpaRepository<ItemFile, Long>