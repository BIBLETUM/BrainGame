package com.example.braingame.domain.repository

import com.example.braingame.domain.entity.GameSettings
import com.example.braingame.domain.entity.Level
import com.example.braingame.domain.entity.Question

interface GameRepository {

    fun generateQuestion(
        maxSumValue: Int,
        countOfOptions: Int
    ): Question

    fun getGameSettings(level: Level): GameSettings
}