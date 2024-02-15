package com.example.braingame.presentation

import android.app.Application
import android.os.CountDownTimer
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.braingame.R
import com.example.braingame.data.GameRepositoryImpl
import com.example.braingame.domain.entity.GameResult
import com.example.braingame.domain.entity.GameSettings
import com.example.braingame.domain.entity.Level
import com.example.braingame.domain.entity.Question
import com.example.braingame.domain.usecases.GenerateQuestionUseCase
import com.example.braingame.domain.usecases.GetGameSettingsUseCase

class GameViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = GameRepositoryImpl

    private val generateQuestionUseCase = GenerateQuestionUseCase(repository)
    private val getGameSettingsUseCase = GetGameSettingsUseCase(repository)

    private var timer: CountDownTimer? = null
    private val context = application

    private lateinit var gameSettings: GameSettings
    private lateinit var level: Level

    private val _secondsLeft = MutableLiveData<String>()
    val secondsLeft: LiveData<String>
        get() = _secondsLeft

    private val _gameResult = MutableLiveData<GameResult>()
    val gameResult: LiveData<GameResult>
        get() = _gameResult

    private val _question = MutableLiveData<Question>()
    val question: LiveData<Question>
        get() = _question

    private val _percentOfRightAnswers = MutableLiveData<Int>()
    val percentOfRightAnswers: LiveData<Int>
        get() = _percentOfRightAnswers

    private val _progressAnswers = MutableLiveData<String>()
    val progressAnswers: LiveData<String>
        get() = _progressAnswers

    private val _enoughPercent = MutableLiveData<Boolean>()
    val enoughPercent: LiveData<Boolean>
        get() = _enoughPercent

    private val _enoughRightAnswers = MutableLiveData<Boolean>()
    val enoughRightAnswers: LiveData<Boolean>
        get() = _enoughRightAnswers

    private val _minPercent = MutableLiveData<Int>()
    val minPercent: LiveData<Int>
        get() = _minPercent

    private var winner: Boolean = false
    private var countOfRightAnswers: Int = 0
    private var countOfQuestion: Int = 0

    fun startGame(level: Level) {
        this.level = level
        this.gameSettings = getGameSettingsUseCase(this.level)
        _minPercent.value = gameSettings.minPercentOfRightAnswers
        generateQuestion()
        updateProgress()
        startTimer()
    }

    fun passAnswer(numberString: String) {
        countOfQuestion++
        val number = numberString.toInt()
        val rightAnswer = _question.value?.rightAnswer
        if (number == rightAnswer) {
            countOfRightAnswers++
        }
        updateProgress()
        generateQuestion()
    }

    private fun updateProgress() {
        val percent = getPercentOfRightAnswers()
        _percentOfRightAnswers.value = percent
        _progressAnswers.value = String.format(
            context.resources.getString(R.string.progress_answers),
            countOfRightAnswers,
            gameSettings.minCountOfRightAnswers
        )
        _enoughRightAnswers.value = countOfRightAnswers >= gameSettings.minCountOfRightAnswers
        _enoughPercent.value = percent >= gameSettings.minPercentOfRightAnswers
    }

    private fun getPercentOfRightAnswers(): Int {
        return ((countOfRightAnswers / countOfQuestion.toDouble()) * INT_IN_PERCENT).toInt()
    }

    private fun endGame() {
        winner = _enoughRightAnswers.value == true && _enoughPercent.value == true
        _gameResult.value = (GameResult(winner, countOfRightAnswers, countOfQuestion, gameSettings))
    }

    private fun startTimer() {
        timer = object : CountDownTimer(
            gameSettings.gameTimeInSec * SECONDS_TO_MILLISECONDS,
            SECONDS_TO_MILLISECONDS
        ) {
            override fun onTick(millisUntilFinished: Long) {
                _secondsLeft.value = getFormattedTime(millisUntilFinished)
            }

            override fun onFinish() {
                endGame()
            }
        }
        timer?.start()
    }

    private fun getFormattedTime(millisUntilFinished: Long): String {
        val seconds = millisUntilFinished / SECONDS_TO_MILLISECONDS
        val minutes = seconds / SECONDS_IN_MINUTE
        val leftSeconds = seconds - (minutes * SECONDS_IN_MINUTE)
        return String.format("%02d:%02d", minutes, leftSeconds)
    }

    private fun generateQuestion() {
        _question.value = generateQuestionUseCase.invoke(gameSettings.maxSumValue)
    }

    companion object {
        private const val SECONDS_TO_MILLISECONDS = 1000L
        private const val SECONDS_IN_MINUTE = 60
        private const val INT_IN_PERCENT = 100
    }

    override fun onCleared() {
        super.onCleared()
        timer?.cancel()
    }
}