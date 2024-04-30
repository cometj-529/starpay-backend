package com.wjdgotjd.starpay.user.repository

import com.wjdgotjd.starpay.user.domain.Role

import org.springframework.data.jpa.repository.JpaRepository
interface RoleRepository: JpaRepository<Role, String> {
}