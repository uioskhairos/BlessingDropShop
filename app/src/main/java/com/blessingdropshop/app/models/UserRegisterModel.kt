package com.blessingdropshop.app.models


data class UserRegisterModel(
    val userId: Any? ="",
    val userEmail: String? = "",
    val userFullName: String? = "",
    val userShopName: String? = "",
    val userReferrer: String? = "",
    val userReferrerId: String? = "",
    val time: String = "") {

}