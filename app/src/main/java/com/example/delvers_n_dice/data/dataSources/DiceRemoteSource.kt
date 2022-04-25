package com.example.delvers_n_dice.data.dataSources

import android.util.Log
import com.example.delvers_n_dice.data.types.Dice
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

/**
 * A class for retrieving Dice information from a remote database
 * All calls to retrieve data from the firebase API will be done here.
 * These functions should mostly return tasks that will eventually contain the result.
 * It is the job of objects that call this service to subscribe to these tasks with listeners and
 * process the results.
 */
class DiceRemoteSource {
    private val diceDB = Firebase.firestore

    /**
     * Used to get the list of prefabricated dice from the Firestore database
     * @return A task that should eventually return the list of prefabricated dice
     */
    suspend fun getDiceListTask(): Array<Pair<String,Dice>> {
        Log.d("FirestoreService","Querying database for dice list")
        return diceDB.collection("DiceList").get().await().map {
            dice -> Pair(dice.id, dice.toObject(Dice::class.java))
        }.toTypedArray()
    }
}