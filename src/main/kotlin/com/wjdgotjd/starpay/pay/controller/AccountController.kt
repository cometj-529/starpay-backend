package com.wjdgotjd.starpay.pay.controller

import com.wjdgotjd.starpay.common.AuthUserId
import com.wjdgotjd.starpay.pay.domain.Account
import com.wjdgotjd.starpay.pay.domain.Log
import com.wjdgotjd.starpay.pay.dto.AccountDepositDto
import com.wjdgotjd.starpay.pay.projection.OtherAccountProjection
import com.wjdgotjd.starpay.pay.projection.ReceiveLogProjection
import com.wjdgotjd.starpay.pay.service.AccountService
import com.wjdgotjd.starpay.pay.service.LogService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/accounts")
class AccountController(
    val service: AccountService,
    val logService: LogService
) {
  @GetMapping("/all/other")
  fun getAllOther(@AuthUserId userId: String): MutableList<OtherAccountProjection> {
    return service.getAllOther(userId)
  }

  @GetMapping("/other")
  fun getOther(@RequestParam("code") code: Int): OtherAccountProjection {
    return service.getOther(code)
  }

  @GetMapping("/me")
  fun getMyAccount(@AuthUserId userId: String): Account {
    return service.getMyAccount(userId)
  }

  @GetMapping("/me/log")
  fun getMyAccountLog(@AuthUserId userId: String, pageable: Pageable): Page<Any> {
    return logService.getMyAccountLog(userId, pageable)
  }

  @PutMapping("/deposit")
  fun deposit(@AuthUserId userId: String, @RequestBody dto: AccountDepositDto) {
    return service.deposit(userId, dto)
  }
}