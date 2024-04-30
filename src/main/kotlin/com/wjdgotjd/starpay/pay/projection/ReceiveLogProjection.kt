package com.wjdgotjd.starpay.pay.projection

import com.wjdgotjd.starpay.pay.domain.Account
import java.time.LocalDateTime

interface ReceiveLogProjection {
    var code: Int
    var receiverAccount: Account
    var senderAccount: OtherAccountProjection
    var amount: Int
    var depositAt: LocalDateTime
    var receiverBalance: Int
}