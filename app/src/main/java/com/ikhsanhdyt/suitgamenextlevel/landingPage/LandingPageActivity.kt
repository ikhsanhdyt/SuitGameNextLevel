package com.ikhsanhdyt.suitgamenextlevel.landingPage

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.github.appintro.AppIntro
import com.github.appintro.AppIntroCustomLayoutFragment
import com.ikhsanhdyt.suitgamenextlevel.MainActivity
import com.ikhsanhdyt.suitgamenextlevel.R
import com.ikhsanhdyt.suitgamenextlevel.databinding.FragmentCustomLandingPageBinding
import com.ikhsanhdyt.suitgamenextlevel.landingPage.fragments.CustomLandingPageFragment


class LandingPageActivity : AppIntro() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        addSlide(AppIntroCustomLayoutFragment.newInstance(R.layout.intro_landing_page_1))
        addSlide(AppIntroCustomLayoutFragment.newInstance(R.layout.intro_landing_page_2))
        addSlide(CustomLandingPageFragment.newInstance())

    }

    override fun onSkipPressed(currentFragment: Fragment?) {
        super.onSkipPressed(currentFragment)
        finish()
    }

    override fun onDonePressed(currentFragment: Fragment?) {
        super.onDonePressed(currentFragment)
        goToMainAct()
        finish()
    }

    private fun goToMainAct() {

    }


}