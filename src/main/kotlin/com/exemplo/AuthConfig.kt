package com.exemplo

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.application.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.auth.*

fun Application.configureSecurity() {
    install(Authentication) {
        jwt("auth-jwt") {
            realm = "ktor-sample"
            verifier(
                JWT
                    .require(Algorithm.HMAC256("secret"))
                    .withAudience("ktor-audience")
                    .withIssuer("ktor-issuer")
                    .build()
            )
            validate { credential ->
                if (credential.payload.getClaim("username").asString().isNotEmpty()) JWTPrincipal(credential.payload) else null
            }
        }
    }
}

fun generateToken(username: String): String =
    JWT.create()
    .withAudience("ktor-audience")
    .withIssuer("ktor-issuer")
    .withClaim("username", username)
    .sign(Algorithm.HMAC256("secret"))