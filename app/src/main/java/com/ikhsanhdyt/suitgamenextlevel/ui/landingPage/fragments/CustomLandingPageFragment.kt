package com.ikhsanhdyt.suitgamenextlevel.ui.landingPage.fragments

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.ikhsanhdyt.suitgamenextlevel.R
import com.ikhsanhdyt.suitgamenextlevel.databinding.FragmentCustomLandingPageBinding
import com.ikhsanhdyt.suitgamenextlevel.sharedPreferences.PlayerSharedPref
import com.ikhsanhdyt.suitgamenextlevel.ui.menu.MenuActivity

class CustomLandingPageFragment : Fragment() {

    private lateinit var binding: FragmentCustomLandingPageBinding

    private val playerSharedPref: PlayerSharedPref? by lazy {
        context?.let { PlayerSharedPref(it) }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCustomLandingPageBinding.inflate(inflater, container, false)
        return binding.root

    }

    fun goToMenuGame() {
        if (binding.etName.text.isNotEmpty()) {
            playerSharedPref?.playerName = binding.etName.text.toString().trim()
            val intent = Intent(context, MenuActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        } else {
            val snackbar =
                Snackbar.make(binding.root, getString(R.string.snackbar_alert_validation_name), Snackbar.LENGTH_SHORT)
            snackbar.setBackgroundTint(Color.RED)
            snackbar.show()
        }

    }
}