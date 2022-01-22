package com.narea.mall.backend.api.controller


import com.narea.mall.entity.Item
import com.narea.mall.request.ItemCreateRequest
import com.narea.mall.request.ItemUpdateRequest
import com.narea.mall.response.ItemResponse
import com.narea.mall.service.ItemService
import io.swagger.v3.core.util.Json
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.sql.RowId

@Tag(name = "item", description="아이템 api")
@RestController
@RequestMapping("/api/v1/items")
class ItemController (
    private val itemService: ItemService
        ) {
    @Operation(summary = "아이템 조회", description = "")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "아이템 정보"),
        ApiResponse(responseCode = "404", description = "아이템 찾을 수 없음"),
    )
    @GetMapping("/{itemId}")
    fun getItem(
        @PathVariable itemId:Long
    ):ItemResponse = itemService.getItemResponse(itemId)

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
    ) = itemService.getItems(pageable)

    @Operation(summary = "아이템 생성", description = "어드민 전용")
    @ApiResponses()
    @PostMapping
    fun createItem(
        itemCreateRequest: ItemCreateRequest
    ) = itemService.createItem(itemCreateRequest)

    @Operation(summary = "아이템 수정", description = "어드민 전용")
    @ApiResponses()
    @PutMapping("/{itemId}")
    fun updateItem(
        @PathVariable itemId: Long,
        itemUpdateRequest: ItemUpdateRequest
    ) = itemService.updateItem(itemId, itemUpdateRequest)

    @Operation(summary = "아이템 삭제", description = "어드민 전용")
    @ApiResponses()
    @DeleteMapping("/{itemId}")
    fun deleteItem(
        @PathVariable itemId: Long
    ) = itemService.deleteItem(itemId)

    // 아이템 옵션
    @Operation(summary = "아이템 옵션 생성", description = "어드민 전용")
    @ApiResponses()
    @PostMapping("/{itemId}/options")
    fun createItemOption(
        @PathVariable itemId: Long
    ){}

    @Operation(summary = "아이템 옵션 수정", description = "어드민 전용")
    @ApiResponses()
    @PutMapping("/{itemId}/options/{optionId}")
    fun updateItemOption(
        @PathVariable itemId: Long,
        @PathVariable optionId: Long
    ) {}

    @Operation(summary = "아이템 옵션 삭제", description = "어드민 전용")
    @DeleteMapping("/{itemId}/options/{optionId}")
    fun deleteItemOption(
        @PathVariable itemId:Long,
        @PathVariable optionId: Long
    ){}

    // 아이템 이미지
    @Operation(summary = "아이템 이미지 생성", description = "어드민 전용")
    @PostMapping("/{itemId}/images",
        consumes = [MediaType.MULTIPART_FORM_DATA_VALUE],
    )
    fun createItemImage(
        @PathVariable itemId: Long,
        multipartFiles: List<MultipartFile>
    ) = itemService.createItemImages(itemId, multipartFiles)

    @Operation(summary = "아이템 이미지 수정", description = "어드민 전용")
    @PutMapping("/{itemId}/images/{fileId}",
        consumes = [MediaType.MULTIPART_FORM_DATA_VALUE],
    )
    fun updateItemImage(
        @PathVariable itemId: Long,
        @PathVariable fileId: Long,
        multipartFile: MultipartFile
    ) = itemService.updateItemImage(itemId, fileId, multipartFile)

    @Operation(summary = "아이템 이미지 삭제", description = "어드민 전용")
    @DeleteMapping("/{itemId}/images/{fileId}")
    fun deleteItemImage(
        @PathVariable itemId: Long,
        @PathVariable fileId: Long
    ) = itemService.deleteItemImage(itemId, fileId)

}