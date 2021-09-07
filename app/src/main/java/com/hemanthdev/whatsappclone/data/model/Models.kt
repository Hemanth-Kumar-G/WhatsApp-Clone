package com.hemanthdev.whatsappclone.data.model


data class User(
    var accountCreatedOn: Long = 0,
    var appVersion: String = "",
    var deviceId: String = "",
    var deviceModel: String = "",
    var deviceOs: String = "",
    var lastLoggedIn: Long = 0,
    var profilePic: String = "",
    var userId: String = "",
    var userName: String = "",
    var name: String = "",
    var status: String = "",
    var phoneNumber: String = "",
    var countryNameCode: String = "",
    var countryPhoneCode: String = "",
    var countryName: String = "",
    var isDetailsAdded: Boolean = false,
    var emailId: String = ""
)