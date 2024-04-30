package com.wjdgotjd.starpay.auth.service

import com.wjdgotjd.starpay.auth.JwtUtils
import com.wjdgotjd.starpay.auth.authentication.IdPwAuthentication
import com.wjdgotjd.starpay.pay.service.AccountService
import com.wjdgotjd.starpay.student.repository.StudentRepository
import com.wjdgotjd.starpay.user.domain.Role
import com.wjdgotjd.starpay.user.domain.User
import com.wjdgotjd.starpay.user.dto.UserCheckDto
import com.wjdgotjd.starpay.user.dto.UserLoginDto
import com.wjdgotjd.starpay.user.dto.UserRegisterDto
import com.wjdgotjd.starpay.user.repository.RoleRepository
import com.wjdgotjd.starpay.user.repository.UserRepository
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.Authentication
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val roleRepository: RoleRepository,
    private val studentRepository: StudentRepository,
    private val passwordEncoder: PasswordEncoder,
    private val authenticationManager: AuthenticationManager,
    private val jwtUtils: JwtUtils,
    private val accountService: AccountService
) {
    fun register(dto: UserRegisterDto): String {
        val role = roleRepository.findById("USER").orElseGet {
            roleRepository.save(Role("USER", "사용자"))
        }

        val student = studentRepository.findById(dto.student).orElseThrow {
            IllegalArgumentException("존재하지 않는 학생입니다.")
        }

        if (userRepository.existsByStudent(student)) {
            throw IllegalArgumentException("이미 존재하는 유저입니다.")
        }

        val user = User(
            null,
            passwordEncoder.encode(dto.pw),
            student,
            mutableListOf(role),
            null
        )

        userRepository.save(user)
        student.user = user
        studentRepository.save(student)

        accountService.createAccount(user)

        val findUser = userRepository.findByStudent(student).get()

        val result = authenticationManager.authenticate(IdPwAuthentication(findUser.id.toString(), dto.pw, emptyList()))

        return createJwtToken(result)
    }

    fun createJwtToken(authentication: Authentication): String {
        val userId = authentication.principal as String
        val authorities = authentication.authorities

        return jwtUtils.createJwt(userId, authorities)
    }

    fun login(dto: UserLoginDto): String {

        val student = studentRepository.findById(dto.student).orElseThrow {
            IllegalArgumentException("존재하지 않는 학생입니다.")
        }

        val user = userRepository.findByStudent(student).orElseThrow {
            IllegalArgumentException("존재하지 않는 유저입니다.")
        }

        val result = authenticationManager.authenticate(IdPwAuthentication(user.id.toString(), dto.pw, emptyList()))
        return createJwtToken(result)
    }

    fun check(dto: UserCheckDto): Boolean {

        val student = studentRepository.findById(dto.student).orElseThrow {
            IllegalArgumentException("존재하지 않는 학생입니다.")
        }

        return userRepository.existsByStudent(student)
    }
}