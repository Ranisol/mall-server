package com.narea.mall.controller

import com.narea.mall.service.CategoryService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*

@Tag(name = "category")
@RestController
@RequestMapping("/api/v1/category")
class CategoryController(
    private val categoryService: CategoryService
) {
    @GetMapping
    fun getCategories() = categoryService.getCategories()

    @PostMapping
    fun createCategory(
        @RequestBody name: String
    ) = categoryService.createCategory(name)

    @PutMapping("/{categoryId}")
    fun updateCategory(
        @PathVariable categoryId: Long,
        @RequestBody name:String
    ) = categoryService.updateCategory(categoryId, name)

    @DeleteMapping("/{categoryId}")
    fun deleteCategory(
        @PathVariable categoryId: Long,
    ) = categoryService.deleteCategory(categoryId)
}