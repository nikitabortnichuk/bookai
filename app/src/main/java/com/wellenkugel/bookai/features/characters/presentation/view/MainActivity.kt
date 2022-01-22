package com.wellenkugel.bookai.features.characters.presentation.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.wellenkugel.bookai.R
import com.wellenkugel.bookai.features.characters.presentation.view.PopularBooksFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val characterSearch =
            PopularBooksFragment()
        supportFragmentManager.beginTransaction()
            .add(R.id.main_host, characterSearch, PopularBooksFragment.TAG)
            .commit()
    }
}
