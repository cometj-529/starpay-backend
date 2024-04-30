package com.wjdgotjd.starpay.pay.service

import com.wjdgotjd.starpay.pay.domain.Account
import com.wjdgotjd.starpay.pay.domain.Log
import com.wjdgotjd.starpay.pay.projection.ReceiveLogProjection
import com.wjdgotjd.starpay.pay.projection.SendLogProjection
import com.wjdgotjd.starpay.pay.repository.AccountRepository
import com.wjdgotjd.starpay.pay.repository.LogRepository
import com.wjdgotjd.starpay.user.repository.UserRepository
import org.slf4j.Logger
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.Date

@Service
class LogService(
    val repository: LogRepository,
    val userRepository: UserRepository,
    val accountRepository: AccountRepository
) {
    fun createLog(senderAccount: Account, reciverAccount: Account, amount: Int) {

        System.out.println(senderAccount.code)

        val log = Log(
            null,
            reciverAccount,
            reciverAccount.balance,
            senderAccount,
            senderAccount.balance,
            amount,
            LocalDateTime.now()
        )

        repository.save(log)
    }

    fun getMyAccountLog(userId: String, pageable: Pageable): Page<Any> {
        val user = userRepository.findById(userId).orElseThrow {
            IllegalArgumentException("존재하지 않는 유저입니다.")
        }

        val account = accountRepository.findByUser(user).orElseThrow {
            IllegalArgumentException("존재하지 않는 계좌입니다.")
        }

        val receiveLogs = repository.findAllByReceiverAccount(account)
        val sendLogs = repository.findAllBySenderAccount(account)

        val resultList: List<Any> = receiveLogs.plus(sendLogs)

        val soredData = resultList.sortedWith(Comparator {a, b -> (
                if (a is ReceiveLogProjection && b is ReceiveLogProjection) {
                    -compareValues(a.depositAt, b.depositAt)
                } else if (a is SendLogProjection && b is SendLogProjection) {
                    -compareValues(a.depositAt, b.depositAt)
                } else if (a is ReceiveLogProjection && b is SendLogProjection) {
                    -compareValues(a.depositAt, b.depositAt)
                } else if (a is SendLogProjection && b is ReceiveLogProjection) {
                    -compareValues(a.depositAt, b.depositAt)
                } else {
                    0
                }
            )})

        val start = pageable.offset.toInt()
        val end = Math.min((start + pageable.pageSize), soredData.size)

        val resultPage: Page<Any> = PageImpl<Any>(soredData.subList(start, end), pageable, soredData.size.toLong())

        return resultPage
    }
}