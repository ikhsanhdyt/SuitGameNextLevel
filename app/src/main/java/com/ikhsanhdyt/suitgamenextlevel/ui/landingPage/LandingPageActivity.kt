package com.ikhsanhdyt.suitgamenextlevel.ui.landingPage

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.github.appintro.AppIntro
import com.github.appintro.AppIntroCustomLayoutFragment
import com.ikhsanhdyt.suitgamenextlevel.R
import com.ikhsanhdyt.suitgamenextlevel.ui.landingPage.fragments.CustomLandingPageFragment


class LandingPageActivity : AppIntro() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        isSkipButtonEnabled = false


        addSlide(AppIntroCustomLayoutFragment.newInstance(R.layout.intro_landing_page_1))
        addSlide(AppIntroCustomLayoutFragment.newInstance(R.layout.intro_landing_page_2))
        addSlide(CustomLandingPageFragment())

    }

    override fun onSkipPressed(currentFragment: Fragment?) {
        super.onSkipPressed(currentFragment)
        finish()
    }

    override fun onDonePressed(currentFragment: Fragment?) {
        super.onDonePressed(currentFragment)
        if (currentFragment is CustomLandingPageFragment){
            currentFragment.goToMenuGame()
        }
    }


}