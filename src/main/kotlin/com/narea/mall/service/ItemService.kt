package com.narea.mall.service

import com.narea.mall.dto.*
import com.narea.mall.entity.Item
import com.narea.mall.entity.ItemCategory
import com.narea.mall.entity.ItemFile
import com.narea.mall.exception.BadRequestException
import com.narea.mall.exception.NotFoundException
import com.narea.mall.repository.ItemCategoryRepository
import com.narea.mall.repository.ItemFileRepository
import com.narea.mall.repository.ItemRepository

import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile

@Service
@Transactional(readOnly = true)
class ItemService(
    private val itemRepository: ItemRepository,
    private val itemFileRepository: ItemFileRepository,
    private val itemCategoryRepository: ItemCategoryRepository,
    private val categoryService: CategoryService,
    private val s3Service: S3Service,
) {

    fun getItem(itemId:Long):Item = itemRepository.findByIdOrNull(itemId) ?: throw NotFoundException("$itemId not exist")
    fun getItemResponse(itemId: Long): ItemResponse = getItem(itemId).toResponse()
    /** TODO: 동적쿼리 추가 */
    fun getItemsResponse(pageable: Pageable) =
        itemRepository.findAll(pageable).map { entity ->
            entity.toResponse()
        }
    @Transactional
    fun createItem(itemCreateRequest: ItemCreateRequest): ItemResponse =
        itemCreateRequest.toEntity().apply {
            itemCategories = itemCreateRequest.categoryIds.map { id ->
                ItemCategory(
                    category = categoryService.getCategory(id),
                    item = this
                )
            }
        }.also { item ->
            itemRepository.save(item) // cascade all
        }.toResponse()
    @Transactional
    fun updateItem(itemId:Long, itemUpdateRequest: ItemUpdateRequest):ItemResponse =
        getItem(itemId).apply {
            name = itemUpdateRequest.name ?: name
            description = itemUpdateRequest.description ?: description
            price = itemUpdateRequest.price ?: price
            inventory = itemUpdateRequest.inventory ?: inventory
            itemCategories = itemUpdateRequest.categories?.map { categoryId ->
                ItemCategory(
                    item = this,
                    category = categoryService.getCategory(categoryId)
                )
            } ?: itemCategories
        }.also { item ->
            itemRepository.save(item)
        }.toResponse()
    @Transactional
    fun deleteItem(itemId: Long) =
        itemRepository.delete(
            getItem(itemId).apply {
                // 파일 먼저 삭제 후, 제거
                files.map { file ->
                    s3Service.delete(file.name)
                }
            }
        )

    // 이미지, 파일 관리
    @Transactional
    fun createItemImages(itemId: Long, files:List<MultipartFile>) {
        val item = getItem(itemId)
        files.map { file ->
            s3Service.validateMultipartFile("item", itemId, file)
            ItemFile(
                name = s3Service.create(file, getItemFileDir(itemId)),
                item = item
            )
        }.let { itemFileRepository.saveAll(it) }
    }
    @Transactional
    fun deleteItemImage(itemId: Long, fileId:Long) {
        val itemFile = itemFileRepository.findByIdOrNull(fileId) ?: throw BadRequestException("$fileId not exist")
        s3Service.delete(itemFile.name)
        itemFileRepository.deleteById(fileId)
    }
    @Transactional
    fun updateItemImage(itemId: Long, fileId: Long, file:MultipartFile) {
        s3Service.validateMultipartFile("item", fileId, file)
        itemFileRepository.getById(fileId).apply {
            s3Service.delete(name)
            name = s3Service.create(file, getItemFileDir(itemId))
        }
    }
    fun getItemFileDir(itemId:Long) = "itemFile/${itemId}"


}