package com.example.santanderdevweek.data.local

import com.example.santanderdevweek.data.Account
import com.example.santanderdevweek.data.Card
import com.example.santanderdevweek.data.Client

class FakeData {
    fun getLocalData() : Account {
        val client = Client("Olá, Leandro Venâncio")
        val card = Card("3300")
        return Account(
            "Cc 01107954-5",
            "Ag 2031",
            "R$ 1.424,65",
            "R$ 3.424,65",
            client,
            card
        )
    }
}