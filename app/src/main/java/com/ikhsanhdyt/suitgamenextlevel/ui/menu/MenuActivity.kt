package com.ikhsanhdyt.suitgamenextlevel.ui.menu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ikhsanhdyt.suitgamenextlevel.R
import com.ikhsanhdyt.suitgamenextlevel.databinding.ActivityMenuBinding
import com.ikhsanhdyt.suitgamenextlevel.sharedPreferences.PlayerSharedPref

class MenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvPlayerName.text = PlayerSharedPref(this).playerName.toString()
    }
}