package com.example.renta

class HelperClass(
    var name: String,
    var email: String,
    var username: String,
    var password: String,
    var phoneNumber: String = ""
) {

    // Secondary constructor with default values
    constructor() : this("", "", "", "")

}
