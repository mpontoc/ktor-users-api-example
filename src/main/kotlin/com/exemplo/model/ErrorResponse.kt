package com.exemplo.model

import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(val error: String)