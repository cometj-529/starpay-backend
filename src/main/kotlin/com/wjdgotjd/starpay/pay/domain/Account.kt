package com.wjdgotjd.starpay.pay.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonManagedReference
import com.wjdgotjd.starpay.user.domain.User
import jakarta.persistence.*

@Entity
class Account(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var code: Int?,
    var balance: Int,
    @OneToOne
    @JoinColumn(name = "user_id")
    var user: User,
    @OneToMany(mappedBy = "receiverAccount")
    @JsonIgnore
    var receiveLog: List<Log>,
    @OneToMany(mappedBy = "senderAccount")
    @JsonIgnore
    var sendLog: List<Log>
) {
}