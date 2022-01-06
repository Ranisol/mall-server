package com.narea.mall.response

import com.narea.mall.utils.EMPTY_STRING


data class TokenResponse (
    var accessToken:String = EMPTY_STRING,
    var refreshToken:String = EMPTY_STRING,
)