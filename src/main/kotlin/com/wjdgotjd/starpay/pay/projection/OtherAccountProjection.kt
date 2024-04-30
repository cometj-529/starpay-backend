package com.wjdgotjd.starpay.pay.projection

import com.wjdgotjd.starpay.user.domain.User

interface OtherAccountProjection {
    var code: Int
    var user: User
}