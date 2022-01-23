package com.narea.mall.service
import com.narea.mall.entity.Category
import com.narea.mall.exception.BadRequestException
import com.narea.mall.repository.CategoryRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class CategoryService(
    private val categoryRepository: CategoryRepository
) {
    fun getCategories():List<Category> = categoryRepository.findAll()
    fun getCategory(categoryId: Long) = categoryRepository.findByIdOrNull(categoryId) ?: throw BadRequestException("categoryId:$categoryId does not exist")
    fun createCategory(name:String) = categoryRepository.save(Category(name = name))
    fun updateCategory(categoryId:Long, categoryName: String)
        = getCategory(categoryId).apply { name = categoryName }.also { categoryRepository.save(it) }
    fun deleteCategory(categoryId: Long) = categoryRepository.delete(
        getCategory(categoryId)
    )
}

