package com.example.catans.model

import com.example.catans.util.Utils
import com.google.gson.annotations.SerializedName

/**
{
    "FlyType": "A",
    "AirlineID": "JX",
    "Airline": "星宇航空",
    "FlightNumber": "786",
    "DepartureAirportID": "MNL",
    "DepartureAirport": "馬尼拉機場",
    "ArrivalAirportID": "TPE",
    "ArrivalAirport": "臺北桃園國際機場",
    "ScheduleTime": "00:10",
    "ActualTime": "00:02",
    "EstimatedTime": "00:02",
    "Remark": "已到ARRIVED",
    "Terminal": "1",
    "Gate": "A9",
    "UpdateTime": "2023-05-23 11:00:28"
},
**/

data class Airport(
    @SerializedName(Utils.FlyType) val flyType: String?,
    @SerializedName(Utils.AirlineID) val airlineID: String?,
    @SerializedName(Utils.Airline) val airline: String?,
    @SerializedName(Utils.FlightNumber) val flightNumber: String?,
    @SerializedName(Utils.DepartureAirportID) val departureAirportID: String?,
    @SerializedName(Utils.DepartureAirport) val departureAirport: String?,
    @SerializedName(Utils.ArrivalAirportID) val arrivalAirportID: String?,
    @SerializedName(Utils.ArrivalAirport) val arrivalAirport: String?,
    @SerializedName(Utils.ScheduleTime) val scheduleTime: String?,
    @SerializedName(Utils.ActualTime) val actualTime: String?,
    @SerializedName(Utils.EstimatedTime) val estimatedTime: String?,
    @SerializedName(Utils.Remark) val remark: String?,
    @SerializedName(Utils.Terminal) val terminal: String?,
    @SerializedName(Utils.Gate) val gate: String?,
    @SerializedName(Utils.UpdateTime) val updateTime: String?,
) {
    fun toMap(): Map<String, String?> {
        return mapOf(
            Utils.FlyType to flyType,
            Utils.AirlineID to airlineID,
            Utils.Airline to airline,
            Utils.FlightNumber to flightNumber,
            Utils.DepartureAirportID to departureAirportID,
            Utils.DepartureAirport to departureAirport,
            Utils.ArrivalAirportID to arrivalAirportID,
            Utils.ArrivalAirport to arrivalAirport,
            Utils.ScheduleTime to scheduleTime,
            Utils.ActualTime to actualTime,
            Utils.EstimatedTime to estimatedTime,
            Utils.Remark to remark,
            Utils.Terminal to terminal,
            Utils.Gate to gate,
            Utils.UpdateTime to updateTime,
        )
    }
}