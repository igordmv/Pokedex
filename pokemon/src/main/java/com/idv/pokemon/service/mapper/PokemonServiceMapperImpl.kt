package com.idv.pokemon.service.mapper

import com.idv.pokemon.service.PokemonDetailsResult
import com.idv.pokemon.service.retrofitmodel.*
import com.idv.pokemon.service.retrofitmodel.PokemonResponseModel
import com.idv.pokemon.service.retrofitmodel.PokemonsResponseModel
import com.idv.pokemon.service.retrofitmodel.ResultResponseModel
import com.idv.pokemon.service.retrofitmodel.SpritesResponseModel
import com.idv.pokemon.service.retrofitmodel.TypesResponseModel
import com.idv.pokemon_entity.Pokemon
import com.idv.pokemon_entity.PokemonAbility
import com.idv.pokemon_entity.PokemonDetails

internal class PokemonServiceMapperImpl : PokemonServiceMapper {
    override fun mapPokemon(body: PokemonResponseModel): Pokemon {
        return Pokemon(
            "id",
            body.name ?: "not found",
            body.sprites?.front ?: ""
        )
    }

    override fun mapPokemons(pokemons: PokemonsResponseModel): List<Pokemon> {
        val pokemons = pokemons.result?.map { result -> mapPokemon(result) }
        return pokemons ?: emptyList()
    }

    override fun mapPokemonDetails(pokemonDetails: PokemonDetailsResult): PokemonDetails {
        return PokemonDetails(
            pokemonDetails.pokemonDetails.id,
            pokemonDetails.pokemonDetails.name,
            pokemonDetails.pokemonDetails.height,
            pokemonDetails.pokemonDetails.weight,
            pokemonDetails.pokemonDetails.sprites?.front,
            getAllImages(pokemonDetails.pokemonDetails.sprites),
            getAllTypes(pokemonDetails.pokemonDetails.types),
            getSpeed(pokemonDetails.pokemonDetails.stats),
            getDefence(pokemonDetails.pokemonDetails.stats),
            getSpecialDefence(pokemonDetails.pokemonDetails.stats),
            getAttack(pokemonDetails.pokemonDetails.stats),
            getSpecialAttack(pokemonDetails.pokemonDetails.stats),
            getHitpoints(pokemonDetails.pokemonDetails.stats),
            getAbilities(pokemonDetails.pokemonDetails.abilities),
            getPokemonChain(pokemonDetails.evolutionChain)
        )
    }

    private fun getPokemonChain(evolutionChain: PokemonEvolutionChainResponseModel?): List<Pokemon>? {
        val pokemonList = mutableListOf<Pokemon>()
        evolutionChain?.chain?.evolvesTo?.forEach {
            val newPokemons = it.evolvesTo?.let { evolvesTo -> getPokemonsEvolvesToFromChild(evolvesTo)}
            newPokemons?.let { news -> pokemonList.addAll(news)}
            val id = getPokemonId(it.specie?.url?: "")
            pokemonList.add(Pokemon(id, it.specie?.name?: "Not Found", "$IMAGE_URL$id.png"))
        }
        return pokemonList
    }

    private fun getPokemonsEvolvesToFromChild(evolvesTo: List<EvolvesToResponseModel>?): List<Pokemon> {
        val pokemonList = mutableListOf<Pokemon>()
        evolvesTo?.forEach {
            it.evolvesTo?.let { evolvesTo -> getPokemonsEvolvesToFromChild(evolvesTo)}
            val id = getPokemonId(it.specie?.url?: "")
            pokemonList.add(Pokemon(id, it.specie?.name?: "Not Found", "$IMAGE_URL$id.png"))
        }
        return pokemonList
    }

    override fun mapAbilities(abilityName: String, abilityDetails: PokemonEffectEntriesResponseModel): PokemonAbility {
        return PokemonAbility(abilityName, abilityDetails?.effectEntries?.first()?.effect, abilityDetails?.effectEntries?.first()?.shortEffect)
    }

    override fun mapPokemonByType(pokemons: PokemonsTypeResponseModel): List<Pokemon> {
        return pokemons.pokemonList?.map { mapPokemonWithType(it) } ?: emptyList()
    }

    private fun mapPokemonWithType(pokemon: PokemonTypeResponseModel): Pokemon {
        val id = getPokemonId(pokemon.pokemon?.url?: "")
        return Pokemon(id, pokemon.pokemon?.name?: "Not Found", "$IMAGE_URL$id.png")
    }

    private fun getAbilities(abilities: List<AbilitiesResponseModel>?): List<String>? {
        val abilityList = mutableListOf<String>()
        abilities?.forEach { ability ->
            if (ability.isHidden == false) {
                ability.ability?.name?.let {
                    abilityList.add(it)
                }
            }
        }
        return abilityList
    }

    private fun getSpeed(stats: List<StatsResponseModel>?): Int? {
        stats?.forEach { stat ->
            if (stat.stat?.name == "speed")
                return stat.baseStat
        }
        return null
    }

    private fun getDefence(stats: List<StatsResponseModel>?): Int? {
        stats?.forEach { stat ->
            if (stat.stat?.name == "defense")
                return stat.baseStat
        }
        return null
    }

    private fun getSpecialDefence(stats: List<StatsResponseModel>?): Int? {
        stats?.forEach { stat ->
            if (stat.stat?.name == "special-defense")
                return stat.baseStat
        }
        return null
    }

    private fun getAttack(stats: List<StatsResponseModel>?): Int? {
        stats?.forEach { stat ->
            if (stat.stat?.name == "attack")
                return stat.baseStat
        }
        return null
    }

    private fun getSpecialAttack(stats: List<StatsResponseModel>?): Int? {
        stats?.forEach { stat ->
            if (stat.stat?.name == "special-attack")
                return stat.baseStat
        }
        return null
    }

    private fun getHitpoints(stats: List<StatsResponseModel>?): Int? {
        stats?.forEach { stat ->
            if (stat.stat?.name == "hp")
                return stat.baseStat
        }
        return null
    }

    private fun getAllTypes(types: List<TypesResponseModel>?): List<String>? {
        val typeList = mutableListOf<String>()
        types?.map { type -> typeList.add(type.type?.name ?: "") }
        return typeList
    }

    private fun getAllImages(sprites: SpritesResponseModel?): List<String>? {
        val spriteList = mutableListOf<String>()
        sprites?.back?.let { spriteList.add(it) }
        sprites?.backFemale?.let { spriteList.add(it) }
        sprites?.backShiny?.let { spriteList.add(it) }
        sprites?.backShinyFemale?.let { spriteList.add(it) }
        sprites?.front?.let { spriteList.add(it) }
        sprites?.frontFemale?.let { spriteList.add(it) }
        sprites?.frontShiny?.let { spriteList.add(it) }
        sprites?.frontShinyFemale?.let { spriteList.add(it) }
        return spriteList
    }

    private fun mapPokemon(result: ResultResponseModel): Pokemon {
        val id = getPokemonId(result.url?: "")
        return Pokemon(id, result.name ?: "not found", "$IMAGE_URL$id.png")
    }

    private fun getPokemonId(url : String) : String {
       return url?.replace("v2".toRegex(), "")?.replace("[^0-9]".toRegex(), "")
    }

    companion object {
        private const val IMAGE_URL =
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/"
    }
}