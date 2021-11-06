package com.spring.security.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

data class Test(
    var test:String,
    var age:Int
)

@Tag(name = "test", description = "test")
@RestController
@RequestMapping("/test")
class TestController(){
    @Operation(summary = "test")
    @GetMapping
    fun hello(): Test {
        return Test("test", 20)
    }
}

@Tag(name="account", description = "account")
@RestController
@RequestMapping("account")
class AccountController{
    @Operation(summary = "test")
    @GetMapping
    fun get(): String {
        return "est"
    }

    @Operation(summary = "sub")
    @GetMapping("/hi")
    fun gets():String{
        return "sdjk"
    }

    @Operation(summary = "sub")
    @GetMapping("/hi/kj")
    fun getjs():String{
        return "sdjk"
    }
}