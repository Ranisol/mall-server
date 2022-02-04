package com.narea.mall.controller

import com.narea.mall.auth.AUTHORIZATION_HEADER
import com.narea.mall.dto.InquiryCreateRequest
import com.narea.mall.dto.InquiryUpdateRequest
import com.narea.mall.dto.ItemCreateRequest
import com.narea.mall.service.ItemInquiryService
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

// public
@Tag(name = "item inquiry", description = "아이템 문의글")
@RestController
@RequestMapping("/api/v1/items/{itemId}/inquiries")
class ItemInquiryController(
    private val inquiryService: ItemInquiryService
) {
    @Operation(summary = "문의글 리스트 조회", description = "")
    @GetMapping
    fun getInquiries(
        @PathVariable itemId: Long,
        @PageableDefault
        @Parameter(hidden = true)
        pageable: Pageable
    ) = inquiryService.getInquiriesResponse(itemId, pageable)
}

// private
@Tag(name = "item user inquiry", description = "아이템 문의글")
@RestController
@RequestMapping("/api/v1/users/{userId}/items/{itemId}/Inquiries")
@SecurityRequirement(name = AUTHORIZATION_HEADER)
class UserItemInquiryController(
    private val inquiryService: ItemInquiryService
) {
    @Operation(summary = "유저별 문의글 리스트 조회", description = "")
    @GetMapping
    fun getUserInquiries(
        @PathVariable userId: Long,
        @PathVariable itemId: Long,
        @PageableDefault
        @Parameter(hidden = true)
        pageable: Pageable
    ) = inquiryService.getUserInquiriesResponse(userId, pageable)

    @Operation(summary = "아이템 문의글 생성", description = "")
    @PostMapping
    fun createUserInquiry(
        @PathVariable userId: Long,
        @PathVariable itemId: Long,
        @RequestBody @Valid inquiryCreateRequest: InquiryCreateRequest
    ) = inquiryService.createInquiry(userId, itemId, inquiryCreateRequest)

    @Operation(summary = "아이템 문의글 수정", description = "")
    @PutMapping("/{inquiryId}")
    fun updateUserInquiry(
        @PathVariable userId: Long,
        @PathVariable itemId: Long,
        @PathVariable inquiryId: Long,
        @RequestBody @Valid inquiryUpdateRequest: InquiryUpdateRequest
    ) = inquiryService.updateInquiry(inquiryId, inquiryUpdateRequest)

    @Operation(summary = "아이템 문의글 삭제", description = "")
    @DeleteMapping("/{inquiryId}")
    fun deleteUserInquiry(
        @PathVariable userId: Long,
        @PathVariable itemId: Long,
        @PathVariable inquiryId: Long
    ) = inquiryService.deleteInquiry(inquiryId)

    /** 문의글 사진 */
    @Operation(summary = "아이템 문의글 사진 등록", description = "")
    @PostMapping(
        "/{inquiryId}/images",
        consumes = [MediaType.MULTIPART_FORM_DATA_VALUE]
    )
    fun createUserInquiryImage(
        @PathVariable userId: Long,
        @PathVariable itemId: Long,
        @PathVariable inquiryId: Long,
        multipartFile: MultipartFile
    ) = inquiryService.createInquiryFile(inquiryId, multipartFile)

    @Operation(summary = "아이템 문의글 사진 수정", description = "")
    @PutMapping(
        "/{inquiryId}/images/{fileId}",
        consumes = [MediaType.MULTIPART_FORM_DATA_VALUE]
    )
    fun updateUserInquiryImage(
        @PathVariable userId: Long,
        @PathVariable itemId: Long,
        @PathVariable inquiryId: Long,
        @PathVariable fileId: Long,
        multipartFile: MultipartFile
    ) = inquiryService.updateInquiryFile(inquiryId, fileId, multipartFile)

    @Operation(summary = "아이템 문의글 사진 삭제", description = "")
    @DeleteMapping("/{inquiryId}/images/{fileId}")
    fun deleteUserInquiryImage(
        @PathVariable userId: Long,
        @PathVariable itemId: Long,
        @PathVariable inquiryId: Long,
        @PathVariable fileId: Long
    ) = inquiryService.deleteInquiryFile(fileId)

    /** 문의글 답글 */
    @Operation(summary = "아이템 문의글 답글 등록", description = "어드민 전용")
    @PostMapping("/{inquiryId}/reply")
    fun createUserInquiryReply(
        @PathVariable userId: Long,
        @PathVariable itemId: Long,
        @PathVariable inquiryId: Long,
        @RequestBody content: String
    ) = inquiryService.createInquiryReply(userId, inquiryId, content)

    @Operation(summary = "아이템 문의글 답글 수정", description = "어드민 전용")
    @PutMapping("/{inquiryId}/reply/{replyId}")
    fun updateUserInquiryReply(
        @PathVariable userId: Long,
        @PathVariable itemId: Long,
        @PathVariable inquiryId: Long,
        @PathVariable replyId: Long,
        @RequestBody content: String
    ) = inquiryService.updateInquiryReply(userId, inquiryId, replyId, content)

    @Operation(summary = "아이템 문의글 답글 삭제", description = "어드민 전용")
    @DeleteMapping("/{inquiryId}/reply/{replyId}")
    fun deleteUserInquiryReply(
        @PathVariable userId: Long,
        @PathVariable itemId: Long,
        @PathVariable inquiryId: Long,
        @PathVariable replyId: Long
    ) = inquiryService.deleteInquiryReply(userId, inquiryId, replyId)
}

