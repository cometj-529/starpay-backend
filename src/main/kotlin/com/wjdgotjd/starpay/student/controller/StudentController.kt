package com.wjdgotjd.starpay.student.controller

import com.wjdgotjd.starpay.student.domain.Student
import com.wjdgotjd.starpay.student.dto.StudentFindInfoDto
import com.wjdgotjd.starpay.student.service.StudentService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/students")
class StudentController(
    val service: StudentService
) {
    @GetMapping("")
    fun getAllByInfo(@RequestParam("sGrade") sGrade: Int, @RequestParam("sClass") sClass: Int): List<Student> {
        return service.getAllByInfo(sGrade, sClass)
    }
}