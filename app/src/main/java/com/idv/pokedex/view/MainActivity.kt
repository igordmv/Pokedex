package com.idv.pokedex.view

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.TypedValue
import android.view.Menu
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.idv.core.extensions.runOnBackground
import com.idv.pokedex.MainController
import com.idv.pokedex.R
import com.idv.pokedex.view.presenter.PokemonsViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener, TextWatcher {

    private lateinit var searchView: SearchView
    private lateinit var searchViewText: EditText
    private var controller : MainController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.setSupportActionBar(searchToolbar)

        controller = MainController.Builder()
            .setActivity(this)
            .setLoadingObserver(loadingObserver)
            .setErrorObserver(errorObserver)
            .setPokemonsObserver(pokemonsObserver)
            .build()

        runOnBackground {
            controller?.getFirstPokemons()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_searchview, menu)
        searchView = menu.findItem(R.id.action_search).actionView as SearchView
        configureSearchView()
        return true
    }

    private fun configureSearchView() {
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager?
        searchView.setSearchableInfo(searchManager?.getSearchableInfo(componentName))
        searchView.setIconifiedByDefault(false)
        searchView.setOnQueryTextListener(this)

        searchViewText = searchView.findViewById(androidx.appcompat.R.id.search_src_text)
        searchViewText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)

            searchViewText.setHintTextColor(
                ContextCompat.getColor(
                    this,
                    R.color.toolbar_text_color
                )
            )
        searchViewText.addTextChangedListener(this)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if (Intent.ACTION_SEARCH == intent?.action) {
            val query = intent.getStringExtra(SearchManager.QUERY)

        }
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

    private val pokemonsObserver =
        Observer<PokemonsViewModel> { pokemonsObserver ->
        }

    private val loadingObserver = Observer<Boolean> { showLoading ->
        if(showLoading)
            progressBar.visibility = View.VISIBLE
        else
            progressBar.visibility = View.GONE
    }

    private val errorObserver = Observer<Boolean> { hasError ->
        if (hasError) {
            Toast.makeText(this, TOAST_FAIL_REQUEST, Toast.LENGTH_LONG).show()
        } else {

        }
    }

    companion object {
        const val TOAST_FAIL_REQUEST = "Erro ao fazer request."
    }
}
