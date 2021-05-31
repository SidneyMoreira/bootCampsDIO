package com.example.santaderdevweek.data.local

import com.example.santaderdevweek.data.Cartao
import com.example.santaderdevweek.data.Cliente
import com.example.santaderdevweek.data.Conta

class FakeData {
    fun getLocalData(): Conta {
        val cliente = Cliente("Sidney")
        val cartao = Cartao("332")
        return Conta(
            "4121232",
            "45453-4",
            "R$ 5.434,50",
            "R$ 9.232,45",
            cliente,
            cartao
        );
    }
}