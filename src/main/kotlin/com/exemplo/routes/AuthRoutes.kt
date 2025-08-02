package com.exemplo.routes

import com.exemplo.generateToken
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

fun Application.authRoutes() {
    routing {
        post("/login") {
            val credentials = call.receive<LoginRequest>()
            if (credentials.username == "admin" && credentials.password == "1234") {
                call.respond(LoginResponse(generateToken(credentials.username)))
            } else {
                call.respondText("Credenciais inválidas", status = io.ktor.http.HttpStatusCode.Unauthorized)
            }
        }
    }
}

@Serializable
data class LoginRequest(val username: String, val password: String)

@Serializable
data class LoginResponse(val token: String)