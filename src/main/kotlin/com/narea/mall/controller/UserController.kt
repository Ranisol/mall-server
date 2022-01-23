package com.narea.mall.controller

import com.narea.mall.auth.AUTHORIZATION_HEADER
import com.narea.mall.dto.UserCreateRequest
import com.narea.mall.dto.UserUpdateRequest
import com.narea.mall.service.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
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
    @Operation(summary = "토큰으로 유저 조회")
    @ApiResponses()
    @GetMapping("/me")
    fun getMe(){}

    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "유저 조회")
    @ApiResponses()
    @GetMapping("/{userId}")
    fun getUser(
        @PathVariable userId:Long
    ) = userService.getUser(userId)

    @Operation(summary = "유저 목록 조회 ")
    @ApiResponses()
    @GetMapping
    fun getUsers() = userService.getUsers()

    @Operation(summary = "유저 생성")
    @ApiResponses()
    @PostMapping
    fun createUser(
        @RequestBody @Valid
        userCreateRequest: UserCreateRequest
    ) = userService.createUser(userCreateRequest)

    @Operation(summary = "유저 수정")
    @ApiResponses()
    @PatchMapping("/{userId}")
    fun updateUser(
        @PathVariable
        userId: Long,
        @RequestBody @Valid
        userUpdateRequest: UserUpdateRequest
    ) = userService.updateUser(userId, userUpdateRequest)

}

