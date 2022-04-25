package com.example.delvers_n_dice.ui.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.delvers_n_dice.R
import com.example.delvers_n_dice.data.types.Dice
import com.example.delvers_n_dice.ui.viewModels.RollViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.NumberFormatException

/**
 * A fragment that represents a reusable set of dice.
 */
class DiceFragment : Fragment() {
    // View Model
    private val rollViewModel: RollViewModel by activityViewModels()

    // UI elements
    private var multiplierTextBox: EditText? = null
    private var sidesTextBox: EditText? = null
    private var modifierTextBox: EditText? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflated = inflater.inflate(R.layout.fragment_dice, container, false)

        // Get the text boxes
        multiplierTextBox = inflated.findViewById(R.id.editNumberMul)
        sidesTextBox = inflated.findViewById(R.id.editNumberSides)
        modifierTextBox = inflated.findViewById(R.id.editNumberMod)

        // Update text boxes with current data
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                rollViewModel.uiState.collect {
                    multiplierTextBox?.setText("${it.dice.multiplier}")
                    sidesTextBox?.setText("${it.dice.sides}")
                    modifierTextBox?.setText("${it.dice.modifier}")
                }
            }
        }

        // Update the state with user input
        multiplierTextBox?.setOnFocusChangeListener { _, _ -> updateDice() }
        sidesTextBox?.setOnFocusChangeListener { _, _ -> updateDice() }
        modifierTextBox?.setOnFocusChangeListener { _, _ -> updateDice() }

        // Inflate the layout for this fragment
        return inflated
    }

    /**
     * Update the view model with the dice values currently in the text boxes
     */
    fun updateDice() {
        rollViewModel.updateDice(Dice(
            getIntOrZero(multiplierTextBox),
            getIntOrZero(sidesTextBox),
            getIntOrZero(modifierTextBox),
        ))
    }

    /**
     * Parses a texbox's input as an int, or returns 0 if it can't
     */
    private fun getIntOrZero(textBox: EditText?): Int {
        return try {
            Integer.parseInt(textBox?.text.toString())
        } catch (e: NumberFormatException) {
            0
        }
    }
}