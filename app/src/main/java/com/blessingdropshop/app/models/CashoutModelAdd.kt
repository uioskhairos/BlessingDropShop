package com.blessingdropshop.app.models

data class CashoutModelAdd(
    var uid: String? ="",
    var shopName: String? ="",
    var method: String? ="",
    var cashoutAmount: Any? = "",
    var status: String? = "",
    var desc: String? = "",
    var time: String? = "")
