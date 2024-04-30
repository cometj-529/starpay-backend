package com.wjdgotjd.starpay.student.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import com.wjdgotjd.starpay.user.domain.User
import jakarta.persistence.*

@Entity
@Table(uniqueConstraints = [UniqueConstraint(columnNames = ["sgrade", "sclass", "snumber"])])
class Student(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var code: Int,
    var sgrade: Int,
    var sclass: Int,
    var snumber: Int,
    var name: String,
    @JsonIgnore
    @OneToOne(mappedBy = "student")
    var user: User?
) {
}