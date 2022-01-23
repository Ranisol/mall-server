package com.narea.mall.controller


import com.narea.mall.auth.AUTHORIZATION_HEADER
import com.narea.mall.dto.ReviewCreateRequest
import com.narea.mall.dto.ReviewUpdateRequest
import com.narea.mall.service.ItemReviewService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import javax.validation.Valid

@Tag(name = "item review", description = "아이템 리뷰")
@RestController
@RequestMapping("/api/v1/items/{itemId}/reviews")
class ItemReviewController(
    private val reviewService: ItemReviewService
) {
    @Operation(summary = "아이템 리뷰 조회")
    @GetMapping("/{reviewId}")
    fun getReview(
        @PathVariable itemId:Long,
        @PathVariable reviewId:Long
    ) = reviewService.getItemReviewResponse(reviewId)

    /** TODO: 동적 쿼리 추가 예정 */
    @Operation(summary = "아이템 리뷰 목록 조회")
    @GetMapping
    fun getReviews(
        @PathVariable itemId:Long,
        @PageableDefault
        @Parameter(hidden = true)
        pageable: Pageable
    ) = reviewService.getItemReviewsResponse(itemId, pageable)
}


@Tag(name = "item review user", description = "아이템 리뷰")
@RestController
@RequestMapping("/api/v1/users/{userId}/items/{itemId}/reviews")
@SecurityRequirement(name = AUTHORIZATION_HEADER)
class UserItemReviewController(
    private val itemReviewService: ItemReviewService
) {

    @Operation(summary = "아이템 리뷰 생성")
    @PostMapping
    fun createUserReview(
        @PathVariable userId:Long,
        @PathVariable itemId: Long,
        @RequestBody @Valid createRequest: ReviewCreateRequest
    ) = itemReviewService.createReview(userId, itemId, createRequest)

    /** TODO: 동적 쿼리 추가 예정 */
    @Operation(summary = "유저별 아이템 리뷰 목록 조회")
    @GetMapping
    fun getUserReviews(
        @PathVariable userId: Long,
        @PathVariable itemId: Long,
        @PageableDefault
        @Parameter(hidden = true)
        pageable: Pageable
    ) = itemReviewService.getUserItemReviewResponse(userId, pageable)

    @Operation(summary = "아이템 리뷰 수정")
    @PutMapping("/{reviewId}")
    fun updateUserReview(
        @PathVariable userId: Long,
        @PathVariable itemId: Long,
        @PathVariable reviewId: Long,
        @RequestBody @Valid updateRequest: ReviewUpdateRequest
    ) = itemReviewService.updateReview(reviewId, updateRequest)

    @Operation(summary = "아이템 리뷰 제거")
    @DeleteMapping("/{reviewId}")
    fun deleteUserReview(
        @PathVariable userId: Long,
        @PathVariable itemId: Long,
        @PathVariable reviewId: Long
    ) = itemReviewService.deleteReview(reviewId)

    // 리뷰 이미지
    @Operation(summary = "아이템 리뷰 이미지 생성")
    @PostMapping("/{reviewId}/images",
        consumes = [MediaType.MULTIPART_FORM_DATA_VALUE]
        )
    fun createUserReviewImage(
        @PathVariable userId: Long,
        @PathVariable itemId: Long,
        @PathVariable reviewId: Long,
        multipartFile: MultipartFile

    ) = itemReviewService.createReviewFile(reviewId, multipartFile)

    @Operation(summary = "아이템 리뷰 이미지 수정")
    @PutMapping(
        "/{reviewId}/images/{fileId}",
        consumes = [MediaType.MULTIPART_FORM_DATA_VALUE]
    )
    fun updateUserReviewImage(
        @PathVariable userId: Long,
        @PathVariable itemId: Long,
        @PathVariable reviewId: Long,
        @PathVariable fileId: Long,
        multipartFile: MultipartFile
    ) = itemReviewService.updateReviewFile(reviewId, fileId, multipartFile)

    @Operation(summary = "아이템 리뷰 이미지 삭제")
    @DeleteMapping("/{reviewId}/images/{fileId}")
    fun deleteUserReviewImage(
        @PathVariable userId: Long,
        @PathVariable itemId: Long,
        @PathVariable reviewId: Long,
        @PathVariable fileId: Long
    ) = itemReviewService.deleteReviewFile(fileId)

}