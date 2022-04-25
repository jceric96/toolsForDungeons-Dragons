package com.example.delvers_n_dice.ui.views.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.delvers_n_dice.ui.views.fragments.DiceFragment
import com.example.delvers_n_dice.ui.views.fragments.LoadDiceFragment
import com.example.delvers_n_dice.R
import com.example.delvers_n_dice.ui.views.fragments.SaveDiceFragment
import com.example.delvers_n_dice.ui.viewModels.RollViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    // UI elements
    private var resultLabel: TextView? = null
    private var rollButton: Button? = null
    private var saveButton: Button? = null
    private var loadButton: Button? = null
    private var diceList: DiceFragment? = null

    // View Model
    private val rollViewModel: RollViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Get the ui elements that we'll be updating
        resultLabel = findViewById(R.id.textViewResult)
        diceList = supportFragmentManager.findFragmentById(R.id.fragmentDiceView) as DiceFragment?
        rollButton = findViewById(R.id.buttonRoll)
        saveButton = findViewById(R.id.buttonSave)
        loadButton = findViewById(R.id.buttonLoad)

        // Set to consistently update the UI from the view model
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                rollViewModel.uiState.collect {
                    resultLabel?.text = it.result.toString()

                    // Display the loadFragment if the flag is set
                    if (it.displayLoadFragment) {
                        val loadFragment = LoadDiceFragment.newInstance(
                            it.diceLoadList.map{ pair -> pair.first }.toTypedArray()
                        )
                        loadFragment.show(supportFragmentManager, "load_dice_fragment")
                        rollViewModel.setLoadFlagFalse()
                    }

                }
            }
        }

        // Rolls the dice on a button click
        rollButton?.setOnClickListener{
            // need to update diceList data because the button doesn't count as a focusChange
            diceList?.updateDice()
            rollViewModel.roll()
        }

        // Bring up the save dialog when the save button is hit
        saveButton?.setOnClickListener{
            val saveFragment = SaveDiceFragment()
            saveFragment.show(supportFragmentManager, "save_dice_fragment")
        }

        // Bring up the load dialog when the load button is hit
        loadButton?.setOnClickListener {
            lifecycleScope.launch {
                rollViewModel.loadDiceList(applicationContext)
            }
        }
    }
}