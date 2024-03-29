package com.example.braingame.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GameResult(
    val winner: Boolean,
    val countOfRightAnswers: Int,
    val countOfQuestion: Int,
    val gameSettings: GameSettings
) : Parcelable {
    val scorePercent: Int
        get() = ((countOfRightAnswers / countOfQuestion.toDouble()) * 100).toInt()
}