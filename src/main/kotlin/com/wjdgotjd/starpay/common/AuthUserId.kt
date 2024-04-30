package com.wjdgotjd.starpay.common

import org.springframework.security.core.annotation.AuthenticationPrincipal

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.VALUE_PARAMETER)
@AuthenticationPrincipal(expression = "#this == 'anonymousUser' ? null: #this")
annotation class AuthUserId {
}