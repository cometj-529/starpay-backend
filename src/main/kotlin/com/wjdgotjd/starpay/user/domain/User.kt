package com.wjdgotjd.starpay.user.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import com.wjdgotjd.starpay.pay.domain.Account
import com.wjdgotjd.starpay.student.domain.Student
import jakarta.persistence.*
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority

@Entity
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int?,
    @JsonIgnore
    var pw: String,
    @OneToOne
    var student: Student,
    @ManyToMany(cascade = arrayOf(CascadeType.REMOVE))
    var roles: MutableList<Role>,
    @OneToOne(mappedBy = "user", cascade = arrayOf(CascadeType.REMOVE))
    @JsonIgnore
    var account: Account?
) {
    fun generateGrantedAuthorities(): List<GrantedAuthority> {
        return roles.map {
            SimpleGrantedAuthority("ROLE_${it.id}")
        }
    }
}