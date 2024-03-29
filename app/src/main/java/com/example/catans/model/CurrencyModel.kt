package com.example.catans.model

import com.example.catans.util.Utils
import com.google.gson.annotations.SerializedName

/**
"data": {
"AUD": 1.5243102464,
"BGN": 1.7939302129,
"BRL": 4.9594407933,
"CAD": 1.3478802387,
"CHF": 0.8798901447,
"CNY": 7.1904612191,
"CZK": 23.3991937698,
"DKK": 6.884461162,
"EUR": 0.9236101573,
"GBP": 0.7896601047,
"HKD": 7.8192709186,
"HRK": 6.6849007558,
"HUF": 357.5926902283,
"IDR": 15564.455286269,
"ILS": 3.6353606821,
"INR": 82.8461200369,
"ISK": 137.3424790427,
"JPY": 150.4540874655,
"KRW": 1325.3184043505,
"MXN": 17.0998825547,
"MYR": 4.7726805729,
"NOK": 10.494731872,
"NZD": 1.6142302547,
"PHP": 55.7048609626,
"PLN": 3.990600602,
"RON": 4.5954208695,
"RUB": 93.3431399151,
"SEK": 10.3146015449,
"SGD": 1.3426202517,
"THB": 35.9104058652,
"TRY": 31.0651853959,
"USD": 1,
"ZAR": 19.1568024469
}
**/

data class Currency(
    @SerializedName(Utils.data) val data: Data?,
)

data class Data(
    @SerializedName(Utils.AUD) val aud: Double = 0.0,
//    @SerializedName(Utils.BGN) val bgn: Double = 0.0,
//    @SerializedName(Utils.BRL) val brl: Double = 0.0,
//    @SerializedName(Utils.CAD) val cad: Double = 0.0,
//    @SerializedName(Utils.CHF) val chf: Double = 0.0,
    @SerializedName(Utils.CNY) val cny: Double = 0.0,
//    @SerializedName(Utils.CZK) val czk: Double = 0.0,
//    @SerializedName(Utils.DKK) val dkk: Double = 0.0,
    @SerializedName(Utils.EUR) val eur: Double = 0.0,
//    @SerializedName(Utils.GBP) val gbp: Double = 0.0,
//    @SerializedName(Utils.HRK) val hrk: Double = 0.0,
//    @SerializedName(Utils.HUF) val huf: Double = 0.0,
    @SerializedName(Utils.HKD) val hkd: Double = 0.0,
//    @SerializedName(Utils.IDR) val idr: Double = 0.0,
//    @SerializedName(Utils.ILS) val ils: Double = 0.0,
//    @SerializedName(Utils.INR) val inr: Double = 0.0,
//    @SerializedName(Utils.ISK) val isk: Double = 0.0,
    @SerializedName(Utils.JPY) val jpy: Double = 0.0,
//    @SerializedName(Utils.KRW) val krw: Double = 0.0,
//    @SerializedName(Utils.MXN) val mxn: Double = 0.0,
//    @SerializedName(Utils.MYR) val myr: Double = 0.0,
//    @SerializedName(Utils.NOK) val nok: Double = 0.0,
//    @SerializedName(Utils.NZD) val nzd: Double = 0.0,
//    @SerializedName(Utils.PHP) val php: Double = 0.0,
//    @SerializedName(Utils.PLN) val pln: Double = 0.0,
//    @SerializedName(Utils.RON) val ron: Double = 0.0,
//    @SerializedName(Utils.RUB) val rub: Double = 0.0,
//    @SerializedName(Utils.SEK) val sek: Double = 0.0,
//    @SerializedName(Utils.SGD) val sgd: Double = 0.0,
//    @SerializedName(Utils.THB) val thb: Double = 0.0,
//    @SerializedName(Utils.TRY) val trY: Double = 0.0,
    @SerializedName(Utils.USD) val usd: Double = 0.0,
//    @SerializedName(Utils.ZAR) val zar: Double = 0.0,
) {
    fun toMap(): Map<String, Double?> {
        return mapOf(
            Utils.AUD to aud,
//            Utils.BGN to bgn,
//            Utils.BRL to brl,
//            Utils.CAD to cad,
//            Utils.CHF to chf,
            Utils.CNY to cny,
//            Utils.CZK to czk,
//            Utils.DKK to dkk,
            Utils.EUR to eur,
//            Utils.GBP to gbp,
            Utils.HKD to hkd,
//            Utils.HRK to hrk,
//            Utils.HUF to huf,
//            Utils.IDR to idr,
//            Utils.ILS to ils,
//            Utils.INR to inr,
//            Utils.ISK to isk,
            Utils.JPY to jpy,
//            Utils.KRW to krw,
//            Utils.MXN to mxn,
//            Utils.MYR to myr,
//            Utils.NOK to nok,
//            Utils.NZD to nzd,
//            Utils.PHP to php,
//            Utils.PLN to pln,
//            Utils.RON to ron,
//            Utils.RUB to rub,
//            Utils.SEK to sek,
//            Utils.SGD to sgd,
//            Utils.THB to thb,
//            Utils.TRY to trY,
            Utils.USD to usd,
//            Utils.ZAR to zar,
        )
    }
}

data class DataChild(
    @SerializedName(Utils.code) val code: String = "",
    @SerializedName(Utils.money) val money: String = "0.0",
)