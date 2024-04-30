package com.wjdgotjd.starpay.user.repository

import com.wjdgotjd.starpay.student.domain.Student
import com.wjdgotjd.starpay.user.domain.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface UserRepository: JpaRepository<User, String> {
    fun findByStudent(student: Student): Optional<User>
    fun existsByStudent(student: Student): Boolean
}