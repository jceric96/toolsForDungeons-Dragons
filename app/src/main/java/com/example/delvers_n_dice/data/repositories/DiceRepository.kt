package com.example.delvers_n_dice.data.repositories

import android.content.Context
import com.example.delvers_n_dice.data.dataSources.DiceLocalSource
import com.example.delvers_n_dice.data.dataSources.DiceRemoteSource
import com.example.delvers_n_dice.data.types.Dice
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

/**
 * A repository for getting Dice data from remote and local sources.
 */
class DiceRepository(
    private val diceLocalSource: DiceLocalSource,
    private val diceRemoteSource: DiceRemoteSource
) {

    /**
     * Asynchronous function for constructing a list of dice from both the
     * remote and local data sources
     */
    suspend fun getDiceList(context: Context): Array<Pair<String, Dice>> {
        val localDiceFiles = diceLocalSource.getDiceFileArray(context).map {
            file -> Pair(
                file.name.replace(".dice", ""),
                Json.decodeFromString<Dice>(file.readText())
            )
        }.toTypedArray()

        val remoteDice = diceRemoteSource.getDiceListTask()

        return localDiceFiles + remoteDice
    }
}