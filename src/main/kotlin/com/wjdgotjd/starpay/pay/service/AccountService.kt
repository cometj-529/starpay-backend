package com.wjdgotjd.starpay.pay.service

import com.wjdgotjd.starpay.pay.domain.Account
import com.wjdgotjd.starpay.pay.dto.AccountDepositDto
import com.wjdgotjd.starpay.pay.projection.OtherAccountProjection
import com.wjdgotjd.starpay.pay.repository.AccountRepository
import com.wjdgotjd.starpay.user.domain.User
import com.wjdgotjd.starpay.user.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class AccountService(
    val repository: AccountRepository,
    val userRepository: UserRepository,
    val logService: LogService
) {

  fun createAccount(user: User): Account {
    val account = Account(
        null,
        0,
        user,
        mutableListOf(),
        mutableListOf()
    )

    return repository.save(account)
  }

  fun getMyAccount(userId: String): Account {
    val user = userRepository.findById(userId).orElseThrow {
      IllegalArgumentException("")
    }

    val account = repository.findByUser(user).orElseThrow {
      IllegalArgumentException("존재하지 않는 계좌입니다.")
    }

    return account
  }

  fun deposit(userId: String, dto: AccountDepositDto) {
    val user = userRepository.findById(userId).orElseThrow {
      IllegalArgumentException("존재하지 않는 유저입니다.")
    }

    val senderAccount = repository.findByUser(user).orElseThrow {
      IllegalArgumentException("존재하지 않는 계좌입니다.")
    }

    val reciverAccount = repository.getReferenceById(dto.code)

    if (senderAccount.balance < dto.amount) {
      throw IllegalArgumentException("잔액 부족입니다.")
    }

    senderAccount.balance -= dto.amount

    reciverAccount.balance += dto.amount

    logService.createLog(senderAccount, reciverAccount, dto.amount)

    repository.save(senderAccount)
    repository.save(reciverAccount)
  }

  fun getAllOther(userId: String): MutableList<OtherAccountProjection> {

    val user = userRepository.findById(userId).orElseThrow {
      IllegalArgumentException("존재하지 않는 유저입니다.")
    }

    return repository.findAllByUserIsNot(user)
  }

  fun getOther(code: Int): OtherAccountProjection {
    val account = repository.findAccountByCode(code).orElseThrow {
      IllegalArgumentException("존재하지 않는 계좌입니다.")
    }

    return account
  }
}