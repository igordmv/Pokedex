package com.idv.pokemon_list.view

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.TypedValue
import android.view.*
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.globo.globosatplay.core.fragment.StatelessFragment
import com.idv.core.extensions.runOnBackground
import com.idv.core.extensions.runOnUI
import com.idv.pokemon_list.PokemonListController
import com.idv.pokemon_list.R
import com.idv.pokemon_list.view.adapter.PokemonAdapter
import com.idv.pokemon_list.view.presenter.PokemonViewModel
import kotlinx.android.synthetic.main.fragment_pokemonlist.*

class PokemonListFragment : StatelessFragment(), SearchView.OnQueryTextListener, TextWatcher {

    private lateinit var searchView: SearchView
    private lateinit var searchViewText: EditText
    private var controller: PokemonListController? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_pokemonlist, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (context as AppCompatActivity).setSupportActionBar(searchToolbar)

        controller = PokemonListController.Builder()
            .setActivity(this)
            .setLoadingObserver(loadingObserver)
            .setErrorObserver(errorObserver)
            .setPokemonsObserver(pokemonsObserver)
            .build()
        runOnBackground {
            controller?.getFirstPokemons()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_searchview, menu)
        searchView = menu.findItem(R.id.action_search).actionView as SearchView
        configureSearchView()

        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun configureSearchView() {
        val searchManager = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager?
        searchView.setSearchableInfo(searchManager?.getSearchableInfo(activity?.componentName))
        searchView.setIconifiedByDefault(false)
        searchView.setOnQueryTextListener(this)

        searchViewText = searchView.findViewById(androidx.appcompat.R.id.search_src_text)
        searchViewText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)

        searchViewText.setHintTextColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.toolbar_text_color
            )
        )
        searchViewText.addTextChangedListener(this)
    }

    override fun afterTextChanged(p0: Editable?) {
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }

    fun setSpelledQuery(message: String) {
        Log.e("RECEBIDO", "RECEBIDO: $message")
    }

    private val pokemonsObserver =
        Observer<List<PokemonViewModel>> { pokemons ->
            runOnUI {
                Log.e("pokemons", pokemons.toString())
                val topHitsAdapter =
                    PokemonAdapter(
                        requireContext(),
                        pokemons
                    )
                val mLayoutManager = LinearLayoutManager(requireContext())
                pokemonRecyclerView?.apply {
                    setHasFixedSize(true)
                    layoutManager = mLayoutManager
                    adapter = topHitsAdapter
                    visibility = View.VISIBLE
                }
            }
        }

    private val loadingObserver = Observer<Boolean> { showLoading ->
        if (showLoading)
            progressBar.visibility = View.VISIBLE
        else
            progressBar.visibility = View.GONE
    }

    private val errorObserver = Observer<Boolean> { hasError ->
        if (hasError) {
            Toast.makeText(requireContext(), TOAST_FAIL_REQUEST, Toast.LENGTH_LONG).show()
        } else {

        }
    }

    companion object {
        const val TOAST_FAIL_REQUEST = "Erro ao fazer request."
    }
}