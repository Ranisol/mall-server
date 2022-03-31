package com.narea.mall.repository

import com.narea.mall.entity.Category
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface CategoryRepository: JpaRepository<Category, Long> {
}