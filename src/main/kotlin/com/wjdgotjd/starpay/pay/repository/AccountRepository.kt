package com.wjdgotjd.starpay.pay.repository

import com.wjdgotjd.starpay.pay.domain.Account
import com.wjdgotjd.starpay.pay.projection.OtherAccountProjection
import com.wjdgotjd.starpay.user.domain.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface AccountRepository: JpaRepository<Account, Int> {
  fun findByUser(user: User): Optional<Account>
  fun findAllByUserIsNot(user: User): MutableList<OtherAccountProjection>
  fun findAccountByCode(code: Int): Optional<OtherAccountProjection>
}