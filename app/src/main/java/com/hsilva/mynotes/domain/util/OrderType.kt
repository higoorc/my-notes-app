package com.hsilva.mynotes.domain.util

sealed class OrderType {
    object Ascending: OrderType()
    object Descending: OrderType()
}
