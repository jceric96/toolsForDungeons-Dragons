package com.example.delvers_n_dice.data.types

import kotlinx.serialization.Serializable
import kotlin.random.Random

/**
 * A class representing a single set of dice. This class is serializable so that it can saved and
 * loaded easily.
 */
@Serializable
class Dice {
    var multiplier = 1  // # of dice
    var sides = 6       // # of sides on the dice
    var modifier = 0    // An integer to add after rolling

    /**
     * Construct a new Dice by specifying the number of dice (mul), the number of sides (sid),
     * and the additive modifier (mod)
     */
    constructor(mul: Int, sid: Int, mod: Int) {
        multiplier = mul
        sides = sid
        modifier = mod
    }

    // Looks unnecessary, but an empty constructor is required for deserialization
    constructor()

    /**
     * Rolls the set of dice. It will roll a `sides`-sided die, `multiplier` times,
     * and add the `modifier` at the end
     */
    fun roll(): Int {
        if (multiplier == 0 || sides < 2) return 0

        var sum = 0

        for (i in 1..multiplier) {
            sum += Random.nextInt(1,sides + 1)
        }

        return sum + modifier
    }

    fun isEqual(otherDice: Dice): Boolean {
        return (multiplier == otherDice.multiplier &&
                sides == otherDice.sides &&
                modifier == otherDice.modifier)
    }
}