package com.narea.mall.controller

import com.narea.mall.auth.AUTHORIZATION_HEADER
import com.narea.mall.service.CategoryService
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@Tag(name = "category")
@RestController
@RequestMapping("/api/v1/category")
class CategoryController(
    private val categoryService: CategoryService
) {
    @GetMapping
    fun getCategories() = categoryService.getCategories()
}

@Tag(name = "manage category")
@RestController
@RequestMapping("/api/v1/users/{userId}/category")
@SecurityRequirement(name = AUTHORIZATION_HEADER)
class ManageCategoryController(
    private val categoryService: CategoryService
) {

    @PreAuthorize("@authService.hasAdminAuth(#userId)")
    @PostMapping
    fun createCategory(
        @RequestBody name: String,
        @PathVariable userId: String
    ) = categoryService.createCategory(name)

    @PreAuthorize("@authService.hasAdminAuth(#userId)")
    @PutMapping("/{categoryId}")
    fun updateCategory(
        @PathVariable categoryId: Long,
        @RequestBody name:String,
        @PathVariable userId: String
    ) = categoryService.updateCategory(categoryId, name)

    @PreAuthorize("@authService.hasAdminAuth(#userId)")
    @DeleteMapping("/{categoryId}")
    fun deleteCategory(
        @PathVariable categoryId: Long,
        @PathVariable userId: String,
    ) = categoryService.deleteCategory(categoryId)
}
