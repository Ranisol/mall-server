package com.narea.mall.backend.api.controller


import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*

@Tag(name = "item review", description = "아이템 리뷰")
@RestController
@RequestMapping("/api/v1/items/{itemId}/reviews")
class ItemReviewController {
    @Operation(summary = "아이템 리뷰 조회")
    @GetMapping("/{reviewId}")
    fun getComment(
        @PathVariable itemId:Long,
        @PathVariable reviewId:Long
    ){}

    @Operation(summary = "아이템 리뷰 목록 조회")
    @GetMapping
    fun getComments(
        @PathVariable itemId: Long
    ){}
}


@Tag(name = "item review", description = "아이템 리뷰")
@RestController
@RequestMapping("/api/v1/users/{userId}/items/{itemId}/reviews")
class UserItemReviewController {
    @Operation(summary = "유저별 아이템 리뷰 조회")
    @GetMapping("/{reviewId}")
    fun getUserComment(
        @PathVariable userId: Long,
        @PathVariable itemId:Long,
        @PathVariable reviewId:Long
    ){}

    @Operation(summary = "유저별 아이템 리뷰 목록 조회")
    @GetMapping
    fun getUserComments(
        @PathVariable userId: Long,
        @PathVariable itemId: Long
    ){}

    @Operation(summary = "아이템 리뷰 생성")
    @PostMapping
    fun createUserComment(
        @PathVariable userId:Long,
        @PathVariable itemId: Long
    ){}

    @Operation(summary = "아이템 리뷰 수정")
    @PutMapping("/{reviewId}")
    fun updateUserComment(
        @PathVariable userId: Long,
        @PathVariable itemId: Long,
        @PathVariable reviewId: Long
    ){}

    @Operation(summary = "아이템 리뷰 제거")
    @DeleteMapping("/{reviewId}")
    fun deleteUserComment(
        @PathVariable userId: Long,
        @PathVariable itemId: Long,
        @PathVariable reviewId: Long
    ){}

    // 리뷰 이미지
    @Operation(summary = "아이템 리뷰 이미지 생성")
    @PostMapping("/{reviewId}/images")
    fun createUserCommentImage(
        @PathVariable userId: Long,
        @PathVariable itemId: Long,
        @PathVariable reviewId: Long
    ){}

    @Operation(summary = "아이템 리뷰 이미지 수정")
    @PutMapping("/{reviewId}/images/{fileId}")
    fun updateUserCommentImage(
        @PathVariable userId: Long,
        @PathVariable itemId: Long,
        @PathVariable reviewId: Long,
        @PathVariable fileId: Long
    ){}

    @Operation(summary = "아이템 리뷰 이미지 삭제")
    @DeleteMapping("/{reviewId}/images/{fileId}")
    fun deleteUserCommentImage(
        @PathVariable userId: Long,
        @PathVariable itemId: Long,
        @PathVariable reviewId: Long,
        @PathVariable fileId: Long
    ){}
}