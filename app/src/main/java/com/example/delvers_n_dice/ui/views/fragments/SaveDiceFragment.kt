package com.example.delvers_n_dice.ui.views.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.delvers_n_dice.R
import com.example.delvers_n_dice.ui.viewModels.RollViewModel
import java.lang.IllegalStateException

/**
 * A DialogFragment for saving dice. It prompts the user to enter a name for the dice set, and will
 * then save it.
 */
class SaveDiceFragment : DialogFragment() {
    // View Model
    private val rollViewModel: RollViewModel by activityViewModels()

    override fun onCreateDialog(savedInstaceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)

            val inflatedView = layoutInflater.inflate(R.layout.savedice_fragment, null)

            // Set the button functionality
            builder.setView(inflatedView)
                .setPositiveButton("Save") { _, _ ->
                    val fileName = inflatedView.findViewById<EditText>(R.id.editTextDiceName)?.text.toString()
                    if (fileName.isNotEmpty()) {
                        rollViewModel.saveDice(requireActivity().applicationContext, fileName)
                    }
                }
                .setNegativeButton("Cancel") { _, _ ->
                    dialog?.cancel()
                }

            builder.create()
        } ?: throw IllegalStateException("Null activity found when creating dialog fragment")
    }
}