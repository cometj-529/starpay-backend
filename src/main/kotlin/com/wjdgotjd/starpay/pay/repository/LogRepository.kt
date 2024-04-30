package com.wjdgotjd.starpay.pay.repository

import com.wjdgotjd.starpay.pay.domain.Account
import com.wjdgotjd.starpay.pay.domain.Log
import com.wjdgotjd.starpay.pay.projection.ReceiveLogProjection
import com.wjdgotjd.starpay.pay.projection.SendLogProjection
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface LogRepository: JpaRepository<Log, Int> {
    fun findAllByReceiverAccountOrSenderAccountOrderByDepositAt(receiverAccount: Account, senderAccount: Account): MutableList<Log>
    fun findAllByReceiverAccount(account: Account): List<ReceiveLogProjection>
    fun findAllBySenderAccount(account: Account): List<SendLogProjection>
}
