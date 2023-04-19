package com.disruptivestudio.pruebadsapp.data

data class Apartments(
    val imagen: String,
    val title: String,
    val descripcion: String,
    val latitude: Float? = 0.0f,
    val longitude: Float? = 0.0f,
    val recomendation: Int? = 0,
    var key: String? = ""
) {
    constructor() : this("", "", "") {}
}
