package com.example.braingame.domain.usecases

import com.example.braingame.domain.entity.GameSettings
import com.example.braingame.domain.entity.Level
import com.example.braingame.domain.repository.GameRepository

class GetGameSettingsUseCase(
    private val repository: GameRepository
) {
    operator fun invoke(level: Level): GameSettings{
        return repository.getGameSettings(level)
    }
}