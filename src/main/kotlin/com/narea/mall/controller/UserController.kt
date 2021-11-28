package com.narea.mall.backend.api.controller


import com.narea.mall.UserCreateRequest
import com.narea.mall.UserUpdateRequest
import com.narea.mall.auth.AUTHORIZATION_HEADER
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

    @Operation(summary = "권한 부여")
    @ApiResponses()
    @PutMapping("/{userId}/roles")
    fun updateUserRole(
        @PathVariable userId: Long,
        @RequestBody roleId: Long,
    ) = userService.addRoleToUser(userId, roleId)
}

@Tag(name = "role")
@RestController
@RequestMapping("/api/v1/roles")
class RoleController(
    private val userService: UserService
){
    @Operation(summary = "권한 조회")
    @ApiResponses()
    @GetMapping("/{roleId}")
    fun getRole(
        @PathVariable roleId:Long
    ) = userService.getRole(roleId)

    @Operation(summary = "권한 목록 조회")
    @ApiResponses()
    @GetMapping
    fun getRoles() = userService.getRoles()

    @Operation(summary = "권한 생성")
    @ApiResponses()
    @PostMapping
    fun createRole(
        @RequestBody roleName: String
    ) = userService.createRole(roleName)

    @Operation(summary = "권한 수정")
    @ApiResponses()
    @PutMapping("/{roleId}")
    fun updateRole(
        @PathVariable roleId: Long,
        @RequestBody roleName: String
    ) = userService.updateRole(roleId, roleName)

}
