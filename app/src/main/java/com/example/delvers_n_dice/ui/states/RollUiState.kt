package com.example.delvers_n_dice.ui.states

import com.example.delvers_n_dice.data.types.Dice

/**
 * This object represents the state of the UI for the roll fragment
 * @param dice A dice object to represent that dice that will be rolled
 * @param result The integer result of the last dice roll
 */
data class RollUiState(
    val dice: Dice = Dice(2, 15, 3),
    val result: Int = 53,
    val diceLoadList: Array<Pair<String, Dice>> =  emptyArray(),
    val displayLoadFragment: Boolean = false
) {
    // AUTOMATICALLY GENERATED CODE FOR COMPARISONS
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as RollUiState

        if (dice != other.dice) return false
        if (result != other.result) return false
        if (!diceLoadList.contentEquals(other.diceLoadList)) return false
        if (displayLoadFragment != other.displayLoadFragment) return false

        return true
    }

    override fun hashCode(): Int {
        var result1 = dice.hashCode()
        result1 = 31 * result1 + result
        result1 = 31 * result1 + diceLoadList.contentHashCode()
        result1 = 31 * result1 + displayLoadFragment.hashCode()
        return result1
    }
}