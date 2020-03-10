package com.idv.pokemon_list.view

import android.app.SearchManager
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.Spannable
import android.text.SpannableString
import android.text.TextWatcher
import android.text.style.ForegroundColorSpan
import android.util.TypedValue
import android.view.*
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.globo.globosatplay.core.ImageConverter
import com.globo.globosatplay.core.fragment.StatelessFragment
import com.idv.core.extensions.runOnBackground
import com.idv.core.extensions.runOnUI
import com.idv.pokemon_list.PokemonListController
import com.idv.pokemon_list.R
import com.idv.pokemon_list.view.adapter.PokemonAdapter
import com.idv.pokemon_list.view.presenter.PokemonViewModel
import kotlinx.android.synthetic.main.fragment_pokemonlist.*
import java.util.*


class PokemonListFragment : StatelessFragment(), SearchView.OnQueryTextListener, TextWatcher {

    private lateinit var topHitsAdapter: PokemonAdapter
    private lateinit var searchView: SearchView
    private lateinit var searchViewText: EditText
    private var query : String? = null
    private var controller: PokemonListController? = null
    private var timer = Timer()

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
            .setPokemonObserver(pokemonObserver)
            .setPaginatedPokemonsObserver(paginatedPokemonsObserver)
            .setPaginateLoadingObserver(paginateLoadingObserver)
            .build()
        runOnBackground {
            controller?.getFirstPokemons()
        }

        pokemonRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    if (controller?.isScrolling == false) {
                        controller?.getNextPage()
                    }
                }
            }
        })
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

    override fun afterTextChanged(text: Editable?) {
        query = text.toString()
        timer.cancel()
        timer = Timer()
        timer.schedule(
            object : TimerTask() {
                override fun run() {
                    runOnUI {
                        if (text.toString().length >= AUTO_SEARCH_TEXT_MIN_SIZE) {
                            controller?.getPokemon(text.toString())
                            searchView.clearFocus()
                        } else {
                            pokemonRecyclerView.visibility = View.VISIBLE
                            searchedPokemonConstraintLayout.visibility = View.GONE
                            searchView.clearFocus()
                        }
                    }
                }
            },
            DELAY
        )
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onQueryTextSubmit(pokemon: String?): Boolean {
        pokemon?.let {
            controller?.getPokemon(pokemon)
            searchView.clearFocus()
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }

    fun setSpelledQuery(message: String) {
        query = message
        val editableText = Editable.Factory().newEditable(message)
        afterTextChanged(editableText)

        runOnUI {
            searchViewText.text = editableText
            searchViewText.clearFocus()
        }
    }

    private val paginatedPokemonsObserver =
        Observer<List<PokemonViewModel>> { pokemons ->
            runOnUI {
                topHitsAdapter.addItems(pokemons)
            }
        }

    private val pokemonObserver =
        Observer<PokemonViewModel> { pokemon ->
            runOnUI {
                pokemon?.let { pokemon ->
                    pokemonRecyclerView.visibility = View.GONE
                    searchedPokemonConstraintLayout.visibility = View.VISIBLE
                    searchEmptyState?.visibility = View.GONE
                    pokemonName.text = pokemon.name
                    ImageConverter.load(
                        requireContext(),
                        pokemon.image,
                        pokemonImage,
                        PokemonAdapter.PokemonViewHolder.POKEMON_WIDTH,
                        PokemonAdapter.PokemonViewHolder.POKEMON_HEIGHT
                    )
                }?:run {
                    query?.let { query ->
                        pokemonRecyclerView.visibility = View.GONE
                        searchedPokemonConstraintLayout.visibility = View.VISIBLE
                        pokemonName.visibility = View.GONE
                        pokemonImage.visibility = View.GONE
                        val noResultFoundText = getString(R.string.fragment_pokemonlist_search_view_no_results_found)
                        val spannableString = SpannableString("$noResultFoundText \"$query\"")
                        spannableString.setSpan(
                            ForegroundColorSpan(Color.WHITE),
                            spannableString.length - query.length - 1,
                            spannableString.length - 1,
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                        )
                        searchEmptyState?.text = spannableString
                        searchEmptyState?.visibility = View.VISIBLE

                        searchViewText.clearFocus()
                    }
                }

            }
        }

    private val pokemonsObserver =
        Observer<List<PokemonViewModel>> { pokemons ->
            runOnUI {
                topHitsAdapter =
                    PokemonAdapter(
                        requireContext(),
                        pokemons as MutableList<PokemonViewModel>
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

    private val paginateLoadingObserver = Observer<Boolean> { showLoading ->
        if (showLoading)
            paginateProgressBar.visibility = View.VISIBLE
        else
            paginateProgressBar.visibility = View.GONE
    }

    private val errorObserver = Observer<Boolean> { hasError ->
        if (hasError) {
            Toast.makeText(requireContext(), TOAST_FAIL_REQUEST, Toast.LENGTH_LONG).show()
        }
    }

    companion object {
        const val TOAST_FAIL_REQUEST = "Não foi possível fazer a requisição."
        private const val DELAY: Long = 1000
        private const val AUTO_SEARCH_TEXT_MIN_SIZE = 3
    }
}