package com.narea.mall.backend.api.controller


import io.swagger.v3.core.util.Json
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.sql.RowId

@Tag(name = "item", description="아이템 api")
@RestController
@RequestMapping("/api/v1/items")
class ItemController {

    @Operation(summary = "아이템 조회", description = "")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "아이템 정보"),
        ApiResponse(responseCode = "404", description = "아이템 찾을 수 없음"),
    )
    @GetMapping("/{itemId}")
    fun getItem(
        @PathVariable itemId:Long
    ){}

    @Operation(summary = "아이템 리스트 조회", description = "")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "아이템 정보들"),
        ApiResponse(responseCode = "404", description = "아이템 찾을 수 없음"),
    )
    @GetMapping
    fun getItems(){}

    @Operation(summary = "아이템 생성", description = "어드민 전용")
    @ApiResponses()
    @PostMapping
    fun createItem(
        @PathVariable userId:Long
    ){}

    @Operation(summary = "아이템 수정", description = "어드민 전용")
    @ApiResponses()
    @PutMapping("/{itemId}")
    fun updateItem(
        @PathVariable userId:Long,
        @PathVariable itemId: Long
    ){}

    @Operation(summary = "아이템 삭제", description = "어드민 전용")
    @ApiResponses()
    @DeleteMapping("/{itemId}")
    fun deleteItem(
        @PathVariable userId:Long,
        @PathVariable itemId: Long
    ){}

    // 아이템 옵션
    @Operation(summary = "아이템 옵션 생성", description = "어드민 전용")
    @ApiResponses()
    @PostMapping("/{itemId}/options")
    fun createItemOption(
        @PathVariable userId:Long,
        @PathVariable itemId: Long
    ){}

    @Operation(summary = "아이템 옵션 수정", description = "어드민 전용")
    @ApiResponses()
    @PutMapping("/{itemId}/options/{optionId}")
    fun updateItemOption(
        @PathVariable userId:Long,
        @PathVariable itemId: Long,
        @PathVariable optionId: Long
    ) {}

    @Operation(summary = "아이템 옵션 삭제", description = "어드민 전용")
    @DeleteMapping("/{itemId}/options/{optionId}")
    fun deleteItemOption(
        @PathVariable userId:Long,
        @PathVariable itemId:Long,
        @PathVariable optionId: Long
    ){}

    // 아이템 이미지
    @Operation(summary = "아이템 이미지 생성", description = "어드민 전용")
    @PostMapping("/{itemId}/images")
    fun createItemImage(
        @PathVariable userId:Long,
        @PathVariable itemId: Long
    ){}

    @Operation(summary = "아이템 이미지 수정", description = "어드민 전용")
    @PutMapping("/{itemId}/images/{fileId}")
    fun updateItemImage(
        @PathVariable userId:Long,
        @PathVariable itemId: Long,
        @PathVariable fileId: Long
    ){}

    @Operation(summary = "아이템 이미지 삭제", description = "어드민 전용")
    @DeleteMapping("/{itemId}/images/{fileId}")
    fun deleteItemImage(
        @PathVariable userId:Long,
        @PathVariable itemId: Long,
        @PathVariable fileId: Long
    ){}

}