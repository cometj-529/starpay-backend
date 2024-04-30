package com.wjdgotjd.starpay.pay.domain

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "account-log")
class Log(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var code: Int?,
    @ManyToOne(fetch = FetchType.LAZY)
    var receiverAccount: Account,
    var receiverBalance: Int,
    @ManyToOne(fetch = FetchType.LAZY)
    var senderAccount: Account,
    var senderBalance: Int,
    var amount: Int,
    var depositAt: LocalDateTime
) {
}