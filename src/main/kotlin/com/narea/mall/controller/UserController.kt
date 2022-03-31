package com.narea.mall.controller

import com.narea.mall.auth.AUTHORIZATION_HEADER
import com.narea.mall.dto.UserCreateRequest
import com.narea.mall.dto.UserUpdateRequest
import com.narea.mall.service.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.access.prepost.PreFilter
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@Tag(name = "user")
@RestController
@RequestMapping("/api/v1/users")
@SecurityRequirement(name = AUTHORIZATION_HEADER)
class UserController(
    private val userService: UserService
){

    @PreAuthorize("@authService.hasAuthByUserId(#userId)")
    @Operation(summary = "유저 조회")
    @ApiResponses()
    @GetMapping("/{userId}")
    fun getUser(
        @PathVariable userId:Long
    ) = userService.getUserResponse(userId)

    @Operation(summary = "유저 정보 수정")
    @ApiResponses()
    @PreAuthorize("@authService.hasAuthByUserId(#userId)")
    @PatchMapping("/{userId}")
    fun updateUser(
        @PathVariable
        userId: Long,
        @RequestBody @Valid
        userUpdateRequest: UserUpdateRequest
    ) = userService.updateUser(userId, userUpdateRequest)


    // admin
    @Operation(summary = "유저 목록 조회 ")
    @ApiResponses()
    @PreAuthorize("@authService.hasAdminAuth(#userId)")
    @GetMapping
    fun getUsers(
        @PageableDefault
        @Parameter(hidden = true)
        pageable: Pageable
    ) = userService.getUsersResponse(pageable)



}

