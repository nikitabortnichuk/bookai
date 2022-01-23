package com.wellenkugel.bookai.features.characters.presentation.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.wellenkugel.bookai.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val characterSearch =
            ChatWithBotFragment()
        supportFragmentManager.beginTransaction()
            .add(R.id.main_host, characterSearch, ChatWithBotFragment.TAG)
            .commit()
    }
}
