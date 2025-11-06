package com.example.l4_geoquiz

data class QuizState (
    val currentQuestionIndex: Int = 0,
    val userAnswers: List<Boolean?> = emptyList(),
    val isQuizFinished: Boolean = false,
    val score: Int = 0,
    val isQuestionAnswered: Boolean = false,
    val isAnswerCorrect: Boolean? = null
)