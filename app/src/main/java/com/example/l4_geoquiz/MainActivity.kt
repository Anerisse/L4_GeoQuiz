package com.example.l4_geoquiz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.l4_geoquiz.data.QuestionRepository
import com.example.l4_geoquiz.ui.theme.L4_GeoQuizTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            L4_GeoQuizTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    QuizShow(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun QuizShow( modifier: Modifier = Modifier) {

    var quizState by remember { mutableStateOf(QuizState()) }
    val questions = QuestionRepository.questions


    fun onAnswer(answer: Boolean){
        val correctAnswer = questions[quizState.currentQuestionIndex].answer;
        val isCorrect = answer == correctAnswer
        val newScore = if (isCorrect) quizState.score + 1 else quizState.score


        quizState = quizState.copy(
            isQuestionAnswered = true,
            score = newScore,
            isAnswerCorrect = isCorrect
        )
    }

    fun onNextQuestion() {
        val nextIndex = quizState.currentQuestionIndex + 1
        if (nextIndex < questions.size) {
            quizState = quizState.copy(
                currentQuestionIndex = nextIndex,
                isQuestionAnswered = false, // Сбрасываем флаг для нового вопроса
                isAnswerCorrect = null  // Сбрасываем результат для нового вопроса
            )
        } else {
            quizState = quizState.copy(isQuizFinished = true)
        }
    }

    fun restartQuiz() {
        quizState = QuizState() // создаем новое начальное состояние
    }


    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = questions[quizState.currentQuestionIndex].text,
            textAlign = TextAlign.Center,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(24.dp))
        // Текущий счет (всегда виден)
        Text(
            text = "Счёт: ${quizState.score}/${questions.size}",
            fontSize = 18.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween){
            if (!quizState.isQuestionAnswered) {
                Button(onClick = { onAnswer(true) }) {
                    Text("TRUE")
                }
                Button(onClick = { onAnswer(false) }) {
                    Text(text = "FALSE")
                }
            }
        }
        if (quizState.isQuestionAnswered) {
            val resultText = if (quizState.isAnswerCorrect == true) "✅ Верно!" else "❌ Неверно"
            val resultColor = if (quizState.isAnswerCorrect == true) Color.Green else Color.Red


            Text(
                text = resultText,
                color = resultColor,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(24.dp))
        }

        //Видно только если вопрос отвечен и не последний
        if (quizState.isQuestionAnswered && quizState.currentQuestionIndex < questions.size - 1) {
            Row (modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End){

                Button(onClick = { onNextQuestion() }) {
                    Text("NEXT")
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowRight,
                        contentDescription = "Next"
                    )


                }
            }
        }
        if (quizState.isQuestionAnswered && quizState.currentQuestionIndex == questions.size - 1) {

            //Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = { restartQuiz() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Blue,
                        contentColor = Color.White
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Restart"
                    )
                    Text("RESTART")
                }
            }
        }
    }


}

@Preview(showBackground = true)
@Composable
fun QuizShowPreview() {
    L4_GeoQuizTheme {
        QuizShow()
    }
}