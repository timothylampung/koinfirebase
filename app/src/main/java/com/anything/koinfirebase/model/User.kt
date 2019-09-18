package com.anything.koinfirebase.model

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class User(
    @Exclude
    var key: String? = null,
    var username: String? = null,
    var email: String? = null
)