package com.wjdgotjd.starpay.user.domain

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "roles")
class Role(
    @Id
    var id: String,
    var title: String
) {
}