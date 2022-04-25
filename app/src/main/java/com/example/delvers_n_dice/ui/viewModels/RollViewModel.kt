package com.example.delvers_n_dice.ui.viewModels

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.delvers_n_dice.data.dataSources.DiceRemoteSource
import com.example.delvers_n_dice.data.types.Dice
import com.example.delvers_n_dice.ui.states.RollUiState
import com.example.delvers_n_dice.data.dataSources.DiceLocalSource
import com.example.delvers_n_dice.data.repositories.DiceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

class RollViewModel: ViewModel() {
    // UI State values
    private val _uiState = MutableStateFlow(RollUiState())
    val uiState: StateFlow<RollUiState> = _uiState.asStateFlow()

    // Repositories
    private val diceRepository = DiceRepository(DiceLocalSource(), DiceRemoteSource())

    /**
     * Rolls the dice and updates the ui state's result
     */
    fun roll() {
        _uiState.update {
            it.copy(result = it.dice.roll())
        }
    }

    /**
     * Update the ui state's dice with a new dice
     */
    fun updateDice(dice: Dice) {
        // Only update the state if the new dice is different than the last
        if (!_uiState.value.dice.isEqual(dice)) {
            _uiState.update {
                it.copy(dice = dice)
            }
        }
    }

    /**
     * Save the current dice values
     */
    fun saveDice(context: Context, name: String) {
        val saveFile = File(context.filesDir, "$name.dice")

        saveFile.writeText(Json.encodeToString(_uiState.value.dice))

        Log.d("RollViewModel", "Saved $name to ${context.filesDir}/$name")
    }

    /**
     * Load the saved dice both locally and from firestore
     */
    suspend fun loadDiceList(context: Context) {
       val diceList = diceRepository.getDiceList(context)

        // Update the ui state with the new list
        _uiState.update {
            it.copy(
                diceLoadList = diceList,
                displayLoadFragment = true
            )
        }
    }

    /**
     * Load a specific dice from the list into the main UI
     */
    fun loadDiceFromList(index: Int) {
        _uiState.update {
            it.copy(dice = it.diceLoadList[index].second)
        }
    }

    /**
     * Used to set the displayLoadFragment to false so it doesn't load
     * infinitely
     */
    fun setLoadFlagFalse() {
        _uiState.update {
            it.copy(displayLoadFragment = false)
        }
    }
}