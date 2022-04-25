package com.example.delvers_n_dice.data.dataSources

import android.content.Context
import com.example.delvers_n_dice.data.types.Dice
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

/**
 * A class for storing, retrieving, and deleting dice data from the phone's local storage
 */
class DiceLocalSource {

    /**
     * Returns an array of .dice files found in the provided context
     * @param context The context where dice files are stored
     * @return An array of .dice files
     */
    fun getDiceFileArray(context: Context): Array<File> {
        return context.filesDir.listFiles()?.filter {
            it.name.endsWith(".dice")
        }?.toTypedArray() ?: emptyArray()
    }

    /**
     * Saves dice in local storage
     * @param context The context to store the dice in
     * @param dice The dice object to the saved
     * @param name The name of the file to store the dice object in
     */
    fun saveDice(context: Context, dice: Dice, name: String) {
        File(context.filesDir, "${name}.dice").writeText(
            Json.encodeToString(dice)
        )
    }
}