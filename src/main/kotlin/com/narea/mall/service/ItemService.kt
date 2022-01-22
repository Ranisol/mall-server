package com.narea.mall.service

import com.narea.mall.entity.Item
import com.narea.mall.entity.ItemFile
import com.narea.mall.exception.BadRequestException
import com.narea.mall.exception.NotFoundException
import com.narea.mall.repository.ItemFileRepository
import com.narea.mall.repository.ItemRepository
import com.narea.mall.request.ItemCreateRequest
import com.narea.mall.request.ItemUpdateRequest
import com.narea.mall.response.ItemResponse
import com.narea.mall.toEntity
import com.narea.mall.toResponse

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
    private val s3Service: S3Service,
) {

    fun getItem(itemId:Long):Item = itemRepository.findByIdOrNull(itemId) ?: throw NotFoundException("$itemId not exist")
    fun getItemResponse(itemId: Long):ItemResponse = getItem(itemId).toResponse()
    fun getItems(pageable: Pageable) = itemRepository.findAll(pageable).map { it.toResponse() }
    @Transactional
    fun createItem(itemCreateRequest: ItemCreateRequest) = itemRepository.save(itemCreateRequest.toEntity()).toResponse()
    @Transactional
    fun updateItem(itemId:Long, itemUpdateRequest: ItemUpdateRequest):ItemResponse {
        val item = itemRepository.findByIdOrNull(itemId) ?: throw NotFoundException("itemId:$itemId does not exist")
        item.apply {
            name = itemUpdateRequest.name ?: name
            description = itemUpdateRequest.description ?: description
            price = itemUpdateRequest.price ?: price
            inventory = itemUpdateRequest.inventory ?: inventory
        }
        return item.toResponse()
    }
    @Transactional
    fun deleteItem(itemId: Long) = itemRepository.deleteById(itemId)

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

    // 카테고리 관리

}