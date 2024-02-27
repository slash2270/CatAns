package com.example.catans.util

fun <T> List<T>.listEquals(other: List<T>) =
    size == other.size && asSequence()
        .mapIndexed { index, element -> element == other[index] }
        .all { it }

fun <T> ArrayList<T>.arrayListEquals(other: ArrayList<T>) =
    size == other.size && asSequence()
        .mapIndexed { index, element -> element == other[index] }
        .all { it }