package com.ikhsanhdyt.suitgamenextlevel.landingPage.fragments

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.appintro.AppIntro
import com.google.android.material.slider.Slider
import com.ikhsanhdyt.suitgamenextlevel.databinding.FragmentCustomLandingPageBinding

class CustomLandingPageFragment : Fragment(){

    private lateinit var binding: FragmentCustomLandingPageBinding

    interface OnChangeListener {
        fun name(s: String?)
    }

    var onChangeListener: OnChangeListener? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCustomLandingPageBinding.inflate(inflater,container,false)
        return binding.root

        onChangeListener?.name(binding.etName.text.toString())

    }

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        try {
            onChangeListener = activity as OnChangeListener
        } catch (e: ClassCastException) {
            throw ClassCastException("$activity must implement onSomeEventListener")
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() : CustomLandingPageFragment {
            return CustomLandingPageFragment()
        }
    }
}