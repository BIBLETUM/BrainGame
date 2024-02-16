package com.example.braingame.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.braingame.R
import com.example.braingame.databinding.FragmentGameFinishedBinding
import com.example.braingame.domain.entity.GameResult

class GameFinishedFragment : Fragment() {

    private var _binding: FragmentGameFinishedBinding? = null
    private val binding: FragmentGameFinishedBinding
        get() = _binding ?: throw RuntimeException("FragmentGameFinishedBinding == null")


    private val args by navArgs<GameFinishedFragmentArgs>()

    private lateinit var gameResult: GameResult

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameFinishedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        gameResult = args.gameResult
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonRetry.setOnClickListener {
            retryGame()
        }
        setTexts()
        drawFace()
    }

    private fun drawFace() {
        val resId = if (gameResult.winner) {
            R.drawable.ic_smile
        } else {
            R.drawable.ic_sad
        }
        val drawable = ContextCompat.getDrawable(requireContext(), resId)
        binding.emojiResult.background = drawable
    }

    private fun setTexts() {
        binding.gameResult = gameResult
        with(binding) {

            val context = requireContext()

//            val minCountOfRightAnswersText = String.format(
//                context.getString(R.string.required_score),
//                gameResult.gameSettings.minCountOfRightAnswers
//            )
//            tvRequiredAnswers.text = minCountOfRightAnswersText
//
//            val scoreText = String.format(
//                context.getString(R.string.score_answers),
//                gameResult.countOfRightAnswers
//            )
//            tvScoreAnswers.text = scoreText
//
//            val minPercentText = String.format(
//                context.getString(R.string.required_percentage),
//                gameResult.gameSettings.minPercentOfRightAnswers
//            )
//            tvRequiredPercentage.text = minPercentText

//            val scorePercentageText = String.format(
//                context.getString(R.string.score_percentage),
//                gameResult.scorePercent
//            )
//            tvScorePercentage.text = scorePercentageText
        }
    }

    private fun retryGame() {
        findNavController().popBackStack()
    }
}