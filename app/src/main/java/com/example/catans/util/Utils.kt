package com.example.catans.util

import android.app.Activity
import android.content.Context
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import org.mariuszgromada.math.mxparser.Expression
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

//        fun closeKeyboard(activity: Activity) {
//            val view = activity.window?.peekDecorView();
//            if (view != null) {
//                closeKeyboard(activity, view)
//            }
//        }
//
//        fun closeKeyboard(view: View?) {
//            if (view != null) {
//                closeKeyboard(view.activity, view)
//            }
//        }

        fun closeKeyboard(activity: Activity?, view: View) {
            if (activity != null) {
                val inputMethodManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
                if (inputMethodManager != null && view.windowToken != null) {
                    inputMethodManager.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
                }
            }
        }

        fun regSymbol(text: String, symbol: String): Boolean {
            val match1 = text.indexOf(symbol) == text.length - 1
            val regex = Regex("[%x/)(+.-]")
            val match2 = regex.containsMatchIn(text)
            return  match1 || match2
        }

        private fun regOperator(text: String): Boolean = Regex("[+x/-]").containsMatchIn(text)

        fun regCalculatorPrevious(text: String): Boolean = Regex("[+x/-]").containsMatchIn(text[text.length - 1].toString())

        fun regCalculatorNext(text: String, index: Int): Boolean = Regex("[+x/-]").containsMatchIn(text[index].toString())

        fun regNumberPreviousZero(text: String): Boolean {
            if (text.isNotEmpty()) {
                return Regex("0").containsMatchIn(text[text.length - 1].toString())
            }
            return false
        }

        fun regNumberPreviousBackBracket(text: String): Boolean {
            if (text.isNotEmpty()) {
                return Regex("[0-9)]").containsMatchIn(text[text.length - 1].toString())
            }
            return false
        }

        fun regNumberPrevious(text: String): Boolean = Regex("[0-9]").containsMatchIn(text[text.length - 1].toString())

        fun regNumberNext(text: String, index: Int): Boolean = Regex("[0-9]").containsMatchIn(text[index].toString())

        fun regBracketOpenNext(text: String, index: Int): Boolean = Regex("[(]").containsMatchIn(text[index].toString())

        fun regBracketBackNext(text: String, index: Int): Boolean = Regex("[)]").containsMatchIn(text[index].toString())

        private fun regOperatorBetweenBracket(text: String): List<Int> {
            val list = arrayListOf<Int>()
            for (i in text.indices) {
                if (text.length >= 5 && regOperator(text[i].toString()) && text[i - 1].toString() == ")") {
                    list.add(i)
                }
            }
            Log.d("regOperatorBetween", list.toString())
            return list
        }

        fun regOperatorBracket(text: String): Any {
            var listBracket = arrayListOf<String>()
            val listOperator = regOperatorBetweenBracket(text)
            for(i in listOperator.indices) {
                var subText = if (i == 0) text.substring(0, listOperator[i]) else text.substring(listOperator[i - 1] + 1, listOperator[i])
                listBracket = subText(subText, listBracket)
                if (i == listOperator.size - 1) {
                    subText = text.substring(listOperator[i] + 1, text.length).replace("x", "*")
                    listBracket = subText(subText, listBracket)
                }
            }
            Log.d("listBracket", listBracket.toString())
            var getText = ""
            for (j in listBracket.indices) {
                getText += listBracket[j]
                Log.d("getText bracket", getText)
                if (j <= listOperator.size - 1) {
                    getText += text[listOperator[j]]
                    getText = getText.replace("x", "*")
                    Log.d("getText operator", getText)
                }
            }
            val number = Expression(getText).calculate()
            Log.d("regOperatorBracket", number.toString())
            return if (number % 1 == 0.0) number.toInt() else number
        }

        private fun subText(subText: String, listBracket: ArrayList<String>): ArrayList<String> {
            val sub = subText.replace("x", "*")
            listBracket.add(
                if (sub.contains("(")) {
                    expressText(subText.replace("(", "").replace(")", ""))
                } else {
                    sub
                })
            return listBracket
        }

        fun expressText(text: String): String {
            val number = Expression(text).calculate()
            return if (number % 1.0 == 0.0)  number.toInt().toString() else number.toString()
        }

    }

}