package com.seonjk.upbit_websocket.data.model

data class Ticket(val ticket: String)

data class Type(
    val type: String = "ticker",
    val codes: List<String>
)
