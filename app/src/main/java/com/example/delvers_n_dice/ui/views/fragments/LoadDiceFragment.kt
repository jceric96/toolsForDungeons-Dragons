package com.example.delvers_n_dice.ui.views.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.delvers_n_dice.ui.viewModels.RollViewModel
import java.lang.IllegalStateException

private const val DICE_LIST = "dice_list"

/**
 * A dialogFragment used to ask the user which set of dice they want to load.
 *
 * Use the [LoadDiceFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoadDiceFragment : DialogFragment() {
    // View Model
    private val rollViewModel: RollViewModel by activityViewModels()
    private var diceList: Array<String>? = emptyArray() // A list of dice that can be loaded

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            diceList = it.getStringArray(DICE_LIST)
        }
    }

    override fun onCreateDialog(savedInstaceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)

            builder.setTitle("Saved Dice Sets")
                .setItems(diceList) { _, which ->
                    rollViewModel.loadDiceFromList(which)
                }
            builder.create()
        } ?: throw IllegalStateException("Null activity found when creating dialog fragment")
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param dl The list of dice
         * @return A new instance of fragment DiceFragment.
         */
        @JvmStatic
        fun newInstance(dl: Array<String>) =
            LoadDiceFragment().apply {
                arguments = Bundle().apply {
                    putStringArray(DICE_LIST, dl)
                }
            }
    }
}