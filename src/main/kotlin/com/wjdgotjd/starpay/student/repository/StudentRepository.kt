package com.wjdgotjd.starpay.student.repository

import com.wjdgotjd.starpay.student.domain.Student
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface StudentRepository: JpaRepository<Student, Int> {
    fun findBySgradeAndSclassOrderBySnumber(sGrade: Int, sClass: Int): Optional<List<Student>>
}