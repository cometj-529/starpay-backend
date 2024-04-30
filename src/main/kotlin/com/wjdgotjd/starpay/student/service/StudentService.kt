package com.wjdgotjd.starpay.student.service

import com.wjdgotjd.starpay.student.domain.Student
import com.wjdgotjd.starpay.student.dto.StudentFindInfoDto
import com.wjdgotjd.starpay.student.repository.StudentRepository
import org.springframework.stereotype.Service

@Service
class StudentService(
    val repository: StudentRepository
) {
    fun getAllByInfo(sGrade: Int, sClass: Int): List<Student> {
        val student = repository.findBySgradeAndSclassOrderBySnumber(sGrade, sClass).orElseThrow {
            IllegalArgumentException("존재하지 않는 학반입니다.")
        }

        return student
    }
}