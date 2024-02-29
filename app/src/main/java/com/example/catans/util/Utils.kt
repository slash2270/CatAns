package com.example.catans.util

import android.content.Context
import android.util.DisplayMetrics
import kotlin.math.roundToInt

class Utils {

    companion object {
        const val URI_BASE_CURRENCY = "&base_currency="
        const val URI_API_KEY = "latest?apikey=fca_live_u8YybRyp0D3N7bhJg9oynaaTJitTuzMlW9qhaphB"
        const val URI_CURRENCY = "latest?apikey=fca_live_u8YybRyp0D3N7bhJg9oynaaTJitTuzMlW9qhaphB&base_currency=USD"
        var URL_CURRENCY = "https://api.freecurrencyapi.com/v1/"
        const val data = "data"
        const val AUD = "AUD"
        const val BGN = "BGN"
        const val BRL = "BRL"
        const val CAD = "CAD"
        const val CHF = "CHF"
        const val CNY = "CNY"
        const val DKK = "DKK"
        const val CZK = "CZK"
        const val EUR = "EUR"
        const val GBP = "GBP"
        const val HKD = "HKD"
        const val HRK = "HRK"
        const val HUF = "HUF"
        const val IDR = "IDR"
        const val ILS = "ILS"
        const val INR = "INR"
        const val ISK = "ISK"
        const val JPY = "JPY"
        const val KRW = "KRW"
        const val MXN = "MXN"
        const val MYR = "MYR"
        const val NOK = "NOK"
        const val NZD = "NZD"
        const val PHP = "PHP"
        const val PLN = "PLN"
        const val RON = "RON"
        const val RUB = "RUB"
        const val SEK = "SEK"
        const val SGD = "SGD"
        const val THB = "THB"
        const val TRY = "TRY"
        const val USD = "USD"
        const val ZAR = "ZAR"
        const val code = "code"
        const val money = "money"
        const val URI_AIRPORT_DEPARTURE = "AirPortFlyAPI/D/TPE"
        const val URI_AIRPORT_INBOUND = "AirPortFlyAPI/A/TPE"
        const val URL_AIRPORT = "https://e-traffic.taichung.gov.tw/DataAPI/api/"
        const val FlyType = "FlyType"
        const val AirlineID = "AirlineID"
        const val Airline = "Airline"
        const val FlightNumber = "FlightNumber"
        const val DepartureAirportID = "DepartureAirportID"
        const val DepartureAirport = "DepartureAirport"
        const val ArrivalAirportID = "ArrivalAirportID"
        const val ArrivalAirport = "ArrivalAirport"
        const val ScheduleTime = "ScheduleTime"
        const val ActualTime = "ActualTime"
        const val EstimatedTime = "EstimatedTime"
        const val Remark = "Remark"
        const val Terminal = "Terminal"
        const val Gate = "Gate"
        const val UpdateTime = "UpdateTime"
        const val TIME_REPEAT: Long = 10000

        fun dpToPixel(context: Context, dp: Int): Int {
            val displayMetrics: DisplayMetrics = context.resources.displayMetrics
            return if (dp < 0) dp else (dp * displayMetrics.density).roundToInt()
        }

        fun pixelToDp(context: Context, pixel: Int): Int {
            val displayMetrics: DisplayMetrics = context.resources.displayMetrics
            return if (pixel < 0) pixel else (pixel * displayMetrics.density).roundToInt()
        }

    }

}