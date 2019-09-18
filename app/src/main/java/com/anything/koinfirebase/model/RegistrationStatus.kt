package com.anything.koinfirebase.model

class RegistrationStatus(
    var status: Status? = Status.DEFAULT,
    var zzhj: String? = null,
    var zza: String? = null,
    var detailMessage: String? = null
)

enum class Status {
    DEFAULT,
    SUCCESS,
    FAILED
}