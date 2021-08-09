package com.example.santanderdevweek.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.santanderdevweek.R
import com.example.santanderdevweek.data.Account

class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel : MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        searchAccountClient()
    }

    private fun searchAccountClient() {
        mainViewModel.searchAccountClient().observe(this, Observer { result ->
            bindOnView(result)
        })
    }

    private fun bindOnView(account : Account) {
        findViewById<TextView>(R.id.tv_user).text = account.client.name
        findViewById<TextView>(R.id.tv_agency).text = account.agency
        findViewById<TextView>(R.id.tv_current_account).text = account.number
        findViewById<TextView>(R.id.tv_balance).text = account.balance
        findViewById<TextView>(R.id.tv_sum_limit_balance).text = account.limit
        findViewById<TextView>(R.id.tv_value_final_card).text = account.card.finalNumber
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflate = menuInflater
        inflate.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.item_1 -> {
                Log.d("CLICK", "Click no item 1")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}