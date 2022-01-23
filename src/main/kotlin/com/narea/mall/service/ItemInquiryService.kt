package com.narea.mall.service

import com.amazonaws.services.s3.model.InstructionFileId
import com.narea.mall.dto.*
import com.narea.mall.entity.InquiryFile
import com.narea.mall.entity.InquiryReply
import com.narea.mall.exception.BadRequestException
import com.narea.mall.exception.NotFoundException
import com.narea.mall.repository.InquiryFileRepository
import com.narea.mall.repository.InquiryReplyRepository
import com.narea.mall.repository.InquiryRepository
import org.springframework.data.crossstore.ChangeSetPersister
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile

@Service
@Transactional(readOnly = true)
class ItemInquiryService(
    private val inquiryRepository: InquiryRepository,
    private val inquiryFileRepository: InquiryFileRepository,
    private val inquiryReplyRepository: InquiryReplyRepository,
    private val userService: UserService,
    private val itemService: ItemService,
    private val s3Service: S3Service
) {
    fun getInquiry(inquiryId: Long) = inquiryRepository.findByIdOrNull(inquiryId) ?: throw NotFoundException("inquiryId: $inquiryId does not exist")
    /** TODO: 동적쿼리 추가 */
    fun getInquiriesResponse(itemId: Long, pageable: Pageable)
        = inquiryRepository.findAllByItemId(itemId, pageable).map { it.toResponse() }
    /** TODO: 동적쿼리 추가 */
    fun getUserInquiriesResponse(userId: Long, pageable: Pageable)
        = inquiryRepository.findAllByUserId(userId, pageable).map { it.toUserResponse() }
    @Transactional
    fun createInquiry(userId: Long, itemId: Long, inquiryCreateRequest: InquiryCreateRequest)
        = inquiryRepository.save(
        inquiryCreateRequest.toEntity().apply {
            user = userService.getUser(userId)
            item = itemService.getItem(itemId)
        }
    )
    @Transactional
    fun updateInquiry(inquiryId: Long, inquiryUpdateRequest: InquiryUpdateRequest)
        = inquiryRepository.save(
            getInquiry(inquiryId).apply {
                title = inquiryUpdateRequest.title ?: title
                content = inquiryUpdateRequest.content ?: content
            }
        )

    @Transactional
    fun deleteInquiry(inquiryId: Long)
        = inquiryRepository.delete(
            getInquiry(inquiryId).apply {
                files.map { file ->
                    s3Service.delete(file.name)
                }
            }
        )

    // 문의글 파일
    fun getInquiryFileDir(inquiryId: Long) = "inquiryFile/$inquiryId"
    fun getInquiryFile(fileId: Long) = inquiryFileRepository.findByIdOrNull(fileId) ?: throw NotFoundException("fileId:$fileId does not exist")
    @Transactional
    fun createInquiryFile(inquiryId: Long, file:MultipartFile)
        = file.let {
            s3Service.validateMultipartFile("inquiry", inquiryId, file)
            InquiryFile(
                inquiry = getInquiry(inquiryId)
            )
        }.also {
            inquiryFileRepository.save(it)
        }.toResponse()
    @Transactional
    fun createInquiryFiles(inquiryId: Long, files: List<MultipartFile>)
        = files.map {
            s3Service.validateMultipartFile("inquiryId", inquiryId, it)
            InquiryFile(
                inquiry = getInquiry(inquiryId),
                name = s3Service.create(it, getInquiryFileDir(inquiryId))
            )
        }.also {
            inquiryFileRepository.saveAll(it)
        }.map { it.toResponse() }
    fun updateInquiryFile(inquiryId: Long, fileId: Long, file: MultipartFile)
        = file.let {
            s3Service.validateMultipartFile("inquiry", inquiryId, file)
            getInquiryFile(fileId).apply {
                name = s3Service.create(it, getInquiryFileDir(inquiryId))
            }
        }.also {
            inquiryFileRepository.save(it)
        }.toResponse()
    @Transactional
    fun deleteInquiryFile(fileId: Long)
        = inquiryFileRepository.delete(
            getInquiryFile(fileId).apply {
                s3Service.delete(this.name)
            }
        )

    // 문의글 답글
    fun getInquiryReply(replyId: Long) = inquiryReplyRepository.findByIdOrNull(replyId) ?: throw NotFoundException("replyId:$replyId does not exist")
    @Transactional
    fun createInquiryReply(userId: Long, inquiryId: Long, content: String)
        = InquiryReply(
            content = content,
            inquiry = getInquiry(inquiryId),
            user = userService.getUser(userId)
        ).apply {
            inquiryReplyRepository.save(this)
        }.toResponse()
    @Transactional
    fun updateInquiryReply(userId: Long, inquiryId: Long, replyId: Long, content: String)
        = getInquiryReply(replyId)
        .apply {
            this.content = content
        }.also {
            inquiryReplyRepository.save(it)
        }.toResponse()
    @Transactional
    fun deleteInquiryReply(userId: Long, inquiryId: Long, replyId: Long)
        = inquiryReplyRepository.delete(
            getInquiryReply(replyId)
        )
}