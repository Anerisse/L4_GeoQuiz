package com.example.l4_geoquiz

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class QuizState (
    val currentQuestionIndex: Int = 0,
    val isQuizFinished: Boolean = false,
    val score: Int = 0,
    val isQuestionAnswered: Boolean = false,
    val isAnswerCorrect: Boolean? = null
) : Parcelable