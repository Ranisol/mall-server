package com.narea.mall.backend.api.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@Tag(name = "item inquiry", description = "아이템 문의글")
@RestController
@RequestMapping("/api/v1/items/{itemId}/inquiries")
class ItemInquiryController {
    @Operation(summary = "문의글 리스트 조회", description = "")
    @GetMapping
    fun getInquiries(
        @PathVariable itemId: Long
    ){}

    @Operation(summary = "문의글 상세조회", description = "")
    @GetMapping("/{inquiryId}")
    fun getInquiry(
        @PathVariable itemId: Long,
        @PathVariable inquiryId: Long
    ){}
}

@Tag(name = "item inquiry", description = "아이템 문의글")
@RestController
@RequestMapping("/api/v1/users/{userId}/items/{itemId}/Inquiries")
class UserItemInquiryController {
    @Operation(summary = "유저별 문의글 리스트 조회", description = "")
    @GetMapping
    fun getUserInquiries(
        @PathVariable userId: Long,
        @PathVariable itemId: Long
    ){}

    @Operation(summary = "유저별 문의글 상세조회", description = "")
    @GetMapping("/{inquiryId}")
    fun getUserInquiry(
        @PathVariable userId: Long,
        @PathVariable itemId: Long,
        @PathVariable inquiryId: Long
    ){}
    @Operation(summary = "아이템 문의글 생성", description = "")
    @PostMapping
    fun createUserInquiry(
        @PathVariable userId: Long,
        @PathVariable itemId: Long
    ){}

    @Operation(summary = "아이템 문의글 수정", description = "")
    @PutMapping("/{inquiryId}")
    fun updateUserInquiry(
        @PathVariable userId: Long,
        @PathVariable itemId: Long,
        @PathVariable inquiryId: Long
    ){}

    @Operation(summary = "아이템 문의글 삭제", description = "")
    @DeleteMapping("/{inquiryId}")
    fun deleteUserInquiry(
        @PathVariable userId: Long,
        @PathVariable itemId: Long,
        @PathVariable inquiryId: Long
    ){}

    /** 문의글 사진 */
    @Operation(summary = "아이템 문의글 사진 등록", description = "")
    @PostMapping(
        "/{inquiryId}/images",
        consumes = [MediaType.MULTIPART_FORM_DATA_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun createUserInquiryImage(
        @PathVariable userId: Long,
        @PathVariable itemId: Long,
        @PathVariable inquiryId: Long
    ){}

    @Operation(summary = "아이템 문의글 사진 수정", description = "")
    @PutMapping("/{inquiryId}/images/{fileId}")
    fun updateUserInquiryImage(
        @PathVariable userId: Long,
        @PathVariable itemId: Long,
        @PathVariable inquiryId: Long,
        @PathVariable fileId: Long
    ){}

    @Operation(summary = "아이템 문의글 사진 삭제", description = "")
    @DeleteMapping("/{inquiryId}/images/{fileId}")
    fun deleteUserInquiryImage(
        @PathVariable userId: Long,
        @PathVariable itemId: Long,
        @PathVariable inquiryId: Long,
        @PathVariable fileId: Long
    ){}

    /** 문의글 답글 */
    @Operation(summary = "아이템 문의글 답글 등록", description = "어드민 전용")
    @PostMapping("/{inquiryId}/reply")
    fun createUserInquiryReply(
        @PathVariable userId: Long,
        @PathVariable itemId: Long,
        @PathVariable inquiryId: Long
    ){}

    @Operation(summary = "아이템 문의글 답글 수정", description = "어드민 전용")
    @PutMapping("/{inquiryId}/reply/{replyId}")
    fun updateUserInquiryReply(
        @PathVariable userId: Long,
        @PathVariable itemId: Long,
        @PathVariable inquiryId: Long,
        @PathVariable replyId: Long
    ){}

    @Operation(summary = "아이템 문의글 답글 삭제", description = "어드민 전용")
    @DeleteMapping("/{inquiryId}/reply/{replyId}")
    fun deleteUserInquiryReply(
        @PathVariable userId: Long,
        @PathVariable itemId: Long,
        @PathVariable inquiryId: Long,
        @PathVariable replyId: Long
    ){}
}


