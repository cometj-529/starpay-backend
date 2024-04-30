package com.wjdgotjd.starpay.auth.controller

import com.wjdgotjd.starpay.auth.service.AuthService
import com.wjdgotjd.starpay.user.dto.UserRegisterDto
import com.wjdgotjd.starpay.common.AuthUserId
import com.wjdgotjd.starpay.user.dto.UserCheckDto
import com.wjdgotjd.starpay.user.dto.UserLoginDto
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(
    private val service: AuthService
) {
    @PostMapping("/join")
    fun register(@RequestBody dto: UserRegisterDto): String {
        return service.register(dto)
    }

    @PostMapping("/login")
    fun login(@RequestBody dto: UserLoginDto): String {
        return service.login(dto)
    }

    @PostMapping("/check")
    fun check(@RequestBody dto: UserCheckDto): Boolean {
        return service.check(dto)
    }
}