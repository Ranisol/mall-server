package com.narea.mall.controller

import com.narea.mall.auth.AUTHORIZATION_HEADER
import com.narea.mall.dto.ItemCreateRequest
import com.narea.mall.dto.ItemResponse
import com.narea.mall.dto.ItemUpdateRequest
import com.narea.mall.service.CategoryService
import com.narea.mall.service.ItemService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.MediaType
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.sql.RowId
import javax.validation.Valid

@Tag(name = "item", description="아이템 api")
@RestController
@RequestMapping("/api/v1/items")
class ItemController (
    private val itemService: ItemService,
) {
    @Operation(summary = "아이템 조회", description = "")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "아이템 정보"),
        ApiResponse(responseCode = "404", description = "아이템 찾을 수 없음"),
    )
    @GetMapping("/{itemId}")
    fun getItem(
        @PathVariable itemId:Long
    ): ItemResponse = itemService.getItemResponse(itemId)

    /** TODO: 카테고리별 검색 추가 예정 */
    @Operation(summary = "아이템 리스트 조회", description = "")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "아이템 정보들"),
        ApiResponse(responseCode = "404", description = "아이템 찾을 수 없음"),
    )
    @GetMapping
    fun getItems(
        @PageableDefault
        @Parameter(hidden = true)
        pageable: Pageable
    ) = itemService.getItemsResponse(pageable)
}

@Tag(name = "item user", description="아이템 api")
@RestController
@RequestMapping("/api/v1/users/{userId}/items")
@SecurityRequirement(name = AUTHORIZATION_HEADER)
class ItemManageController (
      private val itemService: ItemService
    ) {

    @Operation(summary = "아이템 생성", description = "어드민 전용")
    @ApiResponses()
    @PreAuthorize("@authService.hasAdminAuth(#userId)")
    @PostMapping
    fun createItem(
        @RequestBody @Valid
        itemCreateRequest: ItemCreateRequest,
        @PathVariable userId: String
    ) = itemService.createItem(itemCreateRequest)

    @Operation(summary = "아이템 수정", description = "어드민 전용")
    @ApiResponses()
    @PreAuthorize("@authService.hasAdminAuth(#userId)")
    @PutMapping("/{itemId}")
    fun updateItem(
        @PathVariable itemId: Long,
        @RequestBody @Valid itemUpdateRequest: ItemUpdateRequest,
        @PathVariable userId: String
    ) = itemService.updateItem(itemId, itemUpdateRequest)

    @Operation(summary = "아이템 삭제", description = "어드민 전용")
    @ApiResponses()
    @PreAuthorize("@authService.hasAdminAuth(#userId)")
    @DeleteMapping("/{itemId}")
    fun deleteItem(
        @PathVariable itemId: Long,
        @PathVariable userId: String
    ) = itemService.deleteItem(itemId)


    // 아이템 이미지
    @Operation(summary = "아이템 이미지 생성", description = "어드민 전용")
    @PreAuthorize("@authService.hasAuthByUserId(#userId)")
    @PostMapping("/{itemId}/images",
        consumes = [MediaType.MULTIPART_FORM_DATA_VALUE],
    )
    fun createItemImage(
        @PathVariable itemId: Long,
        @RequestBody @Valid multipartFiles: List<MultipartFile>,
        @PathVariable userId: String
    ) = itemService.createItemImages(itemId, multipartFiles)

    @Operation(summary = "아이템 이미지 수정", description = "어드민 전용")
    @PreAuthorize("@authService.hasAuthByUserId(#userId)")
    @PutMapping("/{itemId}/images/{fileId}",
        consumes = [MediaType.MULTIPART_FORM_DATA_VALUE],
    )
    fun updateItemImage(
        @PathVariable itemId: Long,
        @PathVariable fileId: Long,
        multipartFile: MultipartFile, @PathVariable userId: String
    ) = itemService.updateItemImage(itemId, fileId, multipartFile)

    @Operation(summary = "아이템 이미지 삭제", description = "어드민 전용")
    @PreAuthorize("@authService.hasAuthByUserId(#userId)")
    @DeleteMapping("/{itemId}/images/{fileId}")
    fun deleteItemImage(
        @PathVariable itemId: Long,
        @PathVariable fileId: Long,
        @PathVariable userId: String
    ) = itemService.deleteItemImage(itemId, fileId)

}