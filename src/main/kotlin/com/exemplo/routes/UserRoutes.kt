package com.exemplo.routes

import com.exemplo.model.ErrorResponse
import com.exemplo.service.UserService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

fun Application.userRoutes() {
    routing {
        authenticate("auth-jwt") {
            route("/users") {
                get {
                    call.respond(UserService.getAll())
                }

                get("/{id}") {
                    val id = call.parameters["id"]?.toIntOrNull()
                    val user = id?.let { UserService.getById(it) }
                    if (user != null) call.respond(user)
                    else call.respond(status = HttpStatusCode.NotFound, ErrorResponse("Usuário não encontrado"))
                }

                post {
                    val request = call.receive<CreateUserRequest>()
                    val user = UserService.create(request.name, request.email)
                    call.respond(status = HttpStatusCode.Created, user)
                }

                delete("/{id}") {
                    val id = call.parameters["id"]?.toIntOrNull()
                    if (id != null && UserService.delete(id))
                        call.respondText("Usuário removido", status = HttpStatusCode.Gone)
                    else
                        call.respond(status = HttpStatusCode.NotFound, ErrorResponse("Usuário não encontrado"))
                }
            }
        }
    }
}

@Serializable
data class CreateUserRequest(val name: String, val email: String)