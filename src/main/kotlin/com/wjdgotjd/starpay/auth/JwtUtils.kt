package com.wjdgotjd.starpay.auth

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.GrantedAuthority
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets

@Component
class JwtUtils(
        @Value("\${jwt.key}") private val jwtKey: String
) {
    fun parseClaimsJws(token: String): Jws<Claims> {

        val key = Keys.hmacShaKeyFor(jwtKey.toByteArray(StandardCharsets.UTF_8))

        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
    }

    fun vailidate(token: String):Boolean {
        return try {
            parseClaimsJws(token)

            true
        } catch(e: Exception) {
            false
        }
    }

    fun createJwt(userId: String, authorities: Collection<GrantedAuthority>): String {
        val header = mapOf("alg" to "HS512", "typ" to "JWT")

        val key = Keys.hmacShaKeyFor(jwtKey.toByteArray(StandardCharsets.UTF_8))

        return Jwts.builder()
                .setHeader(header)
                .setSubject(userId)
                .claim("roles", authorities.map{it.authority})
                .signWith(key, SignatureAlgorithm.HS512)
                .compact()
    }

    fun getUserId(token: String): String {
        return parseClaimsJws(token).body.subject;
    }

    fun getRoles(token: String): List<String> {
        return parseClaimsJws(token).body.get("roles", List::class.java) as List<String>
    }
}