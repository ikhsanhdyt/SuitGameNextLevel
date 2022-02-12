package com.ikhsanhdyt.suitgamenextlevel.ui.menu

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ikhsanhdyt.suitgamenextlevel.ui.game.GameActivity
import com.ikhsanhdyt.suitgamenextlevel.R
import com.ikhsanhdyt.suitgamenextlevel.databinding.ActivityMenuBinding
import com.ikhsanhdyt.suitgamenextlevel.sharedPreferences.PlayerSharedPref

class MenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        setNameOnTitle()
        setMenuClickListeners()

    }

    private fun setMenuClickListeners() {
        binding.btnSelectCom.setOnClickListener {
            GameActivity.startActivity(this, GameActivity.GAMEPLAY_MODE_VS_COMPUTER)
        }
        binding.btnSelectPlayer.setOnClickListener {
            GameActivity.startActivity(this, GameActivity.GAMEPLAY_MODE_VS_PLAYER)
        }
    }

    private fun setNameOnTitle() {
        val playerName = PlayerSharedPref(this).playerName
        binding.tvTitleCom.text =
            getString(
                R.string.tv_title_com,
                playerName
            )

        binding.tvTitlePlayer.text =
            getString(
                R.string.tv_title_player,
                playerName
            )
    }
}