package com.idv.pokemondetails.view.mapper

import com.idv.pokemon_entity.PokemonDetails
import com.idv.pokemondetails.view.PokemonDetailsViewModel

internal class PokemonDetailsMapperImpl : PokemonDetailsMapper {
    override fun mapPokemonDetails(pokemonDetails: PokemonDetails): PokemonDetailsViewModel {
        return PokemonDetailsViewModel(
            pokemonDetails.id,
            pokemonDetails.name,
            pokemonDetails.height,
            pokemonDetails.weight,
            pokemonDetails.defaultImage,
            pokemonDetails.images,
            pokemonDetails.types,
            pokemonDetails.speed,
            pokemonDetails.specialDefence,
            pokemonDetails.defence,
            pokemonDetails.attack,
            pokemonDetails.specialAttack,
            pokemonDetails.hitPoints,
            pokemonDetails.abilities,
            pokemonDetails.evolutionChain
        )
    }
}