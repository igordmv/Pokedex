package com.idv.pokedex

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.idv.pokedex.view.MainActivity
import com.idv.pokedex.view.presenter.MainPresenter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference

internal class MainController(private val presenter : MainPresenter
)  : ViewModel()  {

    fun onViewCreated() = viewModelScope.launch(Dispatchers.IO) {
        presenter.showPokemonList()
    }

    class Builder() {

        private lateinit var showPokemonListObservable: Observer<Boolean>
        private lateinit var activityRef: WeakReference<MainActivity>

        fun setContext(activity : MainActivity) = apply {
            this.activityRef = WeakReference(activity)
        }

        fun setPokemonListObservable(showPokemonListObservable: Observer<Boolean>) = apply {
            this.showPokemonListObservable = showPokemonListObservable
        }

        fun build() : MainController? {
            val activity = activityRef.get()

            activity?.let { activity ->
                val presenter = MainPresenter.make()
                presenter.getShowPokemonListObservable().observe(activity, showPokemonListObservable)
                return MainController(presenter)
            }

            return null
        }

    }

}