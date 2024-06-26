package com.wjdgotjd.starpay.auth.authentication.provider

import com.wjdgotjd.starpay.auth.authentication.IdPwAuthentication
import com.wjdgotjd.starpay.user.repository.UserRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.context.annotation.Lazy
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.core.Authentication
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import java.lang.IllegalArgumentException

@Component
class IdPwAuthenticationProvider(
    private val repository: UserRepository,
    @Lazy private val passwordEncoder: PasswordEncoder
) : AuthenticationProvider {
    override fun authenticate(authentication: Authentication?): Authentication {
        val id = (authentication as IdPwAuthentication).principal as String
        val rawPassword = authentication.credentials as String
        val user = repository.findById(id).orElseThrow { EntityNotFoundException("사용자가 없음") }

        if (passwordEncoder.matches(rawPassword, user.pw)) {
            return IdPwAuthentication(id, null, user.generateGrantedAuthorities())
        }
        throw IllegalArgumentException("인증에 실패")
    }

    override fun supports(authentication: Class<*>?): Boolean {
        return authentication == IdPwAuthentication::class.java
    }

}