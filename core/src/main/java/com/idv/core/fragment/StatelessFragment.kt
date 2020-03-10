package com.globo.globosatplay.core.fragment

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

abstract class StatelessFragment : Fragment() {

    open fun saveState(supportFragmentManager: FragmentManager){}

    open fun restoreState(){}
}