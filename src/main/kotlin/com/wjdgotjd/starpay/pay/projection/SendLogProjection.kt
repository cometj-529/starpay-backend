package com.wjdgotjd.starpay.pay.projection

import com.wjdgotjd.starpay.pay.domain.Account
import java.time.LocalDateTime

interface SendLogProjection {
    var code: Int
    var amount: Int
    var depositAt: LocalDateTime
    var receiverAccount: OtherAccountProjection
    var senderAccount: Account
    var senderBalance: Int
}