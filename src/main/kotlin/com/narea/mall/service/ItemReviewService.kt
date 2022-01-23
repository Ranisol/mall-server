package com.narea.mall.service

import com.narea.mall.dto.*
import com.narea.mall.entity.ReviewFile
import com.narea.mall.exception.BadRequestException
import com.narea.mall.exception.NotFoundException
import com.narea.mall.repository.*
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile

@Transactional(readOnly = true)
@Service
class ItemReviewService(
    private val userService: UserService,
    private val itemService: ItemService,
    private val s3Service: S3Service,
    private val reviewRepository: ReviewRepository,
    private val reviewFileRepository: ReviewFileRepository,
) {
    fun getReview(reviewId: Long)
            = reviewRepository.findByIdOrNull(reviewId) ?: throw NotFoundException("reviewId:$reviewId does not exist")

    // 아이템에 대한 리뷰 응답 (reply 와 file은 아이템에 딸려 나옴)
    fun getItemReviewResponse(reviewId: Long)
            = getReview(reviewId).toResponse()
    /** TODO: 동적쿼리 추가 */
    fun getItemReviewsResponse(itemId: Long, pageable: Pageable)
            = reviewRepository.findAllByItemId(itemId, pageable).map { it.toResponse() }
    /** TODO: 동적쿼리 추가 */
    fun getUserItemReviewsResponse(userId: Long, pageable: Pageable)
        = reviewRepository.findAllByUserId(userId, pageable).map { it.toUserResponse() }

    // 리뷰 수정
    @Transactional
    fun createReview(userId: Long, itemId:Long, createRequest: ReviewCreateRequest)
        = createRequest.toEntity().apply {
        user = userService.getUser(userId)
        item = itemService.getItem(itemId)
    }.also { reviewRepository.save(it) }.toResponse()
    @Transactional
    fun updateReview(reviewId:Long, updateRequest: ReviewUpdateRequest)
        = getReview(reviewId).apply {
            title = updateRequest.title ?: title
            content = updateRequest.content ?: content
            hearts = updateRequest.hearts ?: hearts
        }.also { reviewRepository.save(it) }.toResponse()
    @Transactional
    fun deleteReview(reviewId: Long)
        = reviewRepository.delete(
            getReview(reviewId).apply {
                files.map { file ->
                    s3Service.delete(file.name)
                }
            }
        )
    @Transactional
    fun hiddenReview(reviewId: Long)
        = getReview(reviewId).apply {
            hidden = true
       }.also { reviewRepository.save(it) }.toResponse()
    @Transactional
    fun unHiddenReview(reviewId: Long)
        = getReview(reviewId).apply {
            hidden = false
        }.also { reviewRepository.save(it) }.toResponse()


    fun getReviewFile(fileId: Long)
        = reviewFileRepository.findByIdOrNull(fileId) ?: throw BadRequestException("fileId:$fileId does not exist")

    // 리뷰 이미지
    @Transactional
    fun createReviewFile(reviewId: Long, file:MultipartFile): ReviewFileResponse{
        s3Service.validateMultipartFile("review", reviewId, file)
        return ReviewFile(
            name =  s3Service.create(file, getReviewFileDir(reviewId)),
            review = getReview(reviewId)
        ).toResponse()
    }

    @Transactional
    fun updateReviewFile(reviewId: Long, fileId:Long, file:MultipartFile): ReviewFileResponse {
        s3Service.validateMultipartFile("reviewFile", fileId, file)
        return getReviewFile(fileId).apply {
                s3Service.delete(this.name)
                this.name = s3Service.create(file, getReviewFileDir(reviewId))
            }.toResponse()
    }

    @Transactional
    fun deleteReviewFile(fileId: Long)
        = reviewFileRepository.delete(
            getReviewFile(fileId).apply {
                s3Service.delete(this.name)
            }
        )
    fun getReviewFileDir(reviewId: Long)
        = "reviewFile/$reviewId"

}