package com.idv.pokedex.view.mapper

import com.idv.pokedex.view.presenter.PokemonViewModel
import com.idv.pokemon_entity.Pokemon

internal class MainMapperImpl : MainMapper {
    override fun map(pokemon: Pokemon): PokemonViewModel {
        return PokemonViewModel(pokemon.name)
    }

}