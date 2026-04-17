package com.ecohabit.compass.ui.quiz

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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

private enum class QuizStage {
    Start,
    Quiz,
    Result
}

private data class QuizQuestion(
    val title: String,
    val options: List<String>,
    val correctOptionIndex: Int
)

@Composable
fun QuizScreen(onBackClick: () -> Unit) {
    val questions = listOf(
        QuizQuestion(
            title = "What color bin is usually used for paper recycling?",
            options = listOf("Blue", "Red", "Black", "Yellow"),
            correctOptionIndex = 0
        ),
        QuizQuestion(
            title = "Can aluminum be recycled many times without losing quality?",
            options = listOf("Yes", "No"),
            correctOptionIndex = 0
        ),
        QuizQuestion(
            title = "Which item should NOT go in a standard recycling bin?",
            options = listOf("Clean glass bottle", "Plastic bag", "Cardboard box", "Aluminum can"),
            correctOptionIndex = 1
        ),
        QuizQuestion(
            title = "Rinsing food containers before recycling helps reduce contamination.",
            options = listOf("True", "False"),
            correctOptionIndex = 0
        ),
        QuizQuestion(
            title = "What is the best first step to reduce household waste?",
            options = listOf("Reuse products", "Recycle everything", "Burn trash", "Buy more bins"),
            correctOptionIndex = 0
        )
    )

    var stage by remember { mutableStateOf(QuizStage.Start) }
    var currentQuestionIndex by remember { mutableIntStateOf(0) }
    var selectedOptionIndex by remember { mutableStateOf<Int?>(null) }
    var score by remember { mutableIntStateOf(0) }

    fun startQuiz() {
        currentQuestionIndex = 0
        selectedOptionIndex = null
        score = 0
        stage = QuizStage.Quiz
    }

    fun submitAnswer() {
        val selected = selectedOptionIndex ?: return
        if (selected == questions[currentQuestionIndex].correctOptionIndex) {
            score++
        }
        if (currentQuestionIndex < questions.lastIndex) {
            currentQuestionIndex++
            selectedOptionIndex = null
        } else {
            stage = QuizStage.Result
        }
    }

    Scaffold { padding ->
        when (stage) {
            QuizStage.Start -> QuizStartScreen(
                modifier = Modifier.padding(padding),
                questionCount = questions.size,
                onStartClick = ::startQuiz,
                onBackClick = onBackClick,
            )

            QuizStage.Quiz -> QuizQuestionScreen(
                modifier = Modifier.padding(padding),
                question = questions[currentQuestionIndex],
                questionIndex = currentQuestionIndex,
                questionCount = questions.size,
                selectedOptionIndex = selectedOptionIndex,
                onOptionClick = { selectedOptionIndex = it },
                onSubmitClick = ::submitAnswer,
                onBackClick = {
                    stage = QuizStage.Start
                },
            )

            QuizStage.Result -> QuizResultScreen(
                modifier = Modifier.padding(padding),
                score = score,
                totalQuestions = questions.size,
                onLeaveClick = onBackClick,
                onTryAgainClick = ::startQuiz,
                onBackClick = onBackClick,
            )
        }
    }
}

@Composable
private fun QuizStartScreen(
    modifier: Modifier = Modifier,
    questionCount: Int,
    onStartClick: () -> Unit,
    onBackClick: () -> Unit,
) {
    QuizContainer("Recycling Knowledge Quiz", onBackClicked = onBackClick, modifier = modifier) {
        Text(
            text = "Test yourself with $questionCount mixed-format questions and learn better recycling habits.",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = onStartClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Start Quiz")
        }
    }
}

@Composable
private fun QuizQuestionScreen(
    modifier: Modifier = Modifier,
    question: QuizQuestion,
    questionIndex: Int,
    questionCount: Int,
    selectedOptionIndex: Int?,
    onOptionClick: (Int) -> Unit,
    onSubmitClick: () -> Unit,
    onBackClick: () -> Unit,
) {
    QuizContainer("Question ${questionIndex + 1} of $questionCount", onBackClicked = onBackClick, modifier = modifier) {

        Text(
            text = question.title,
            style = MaterialTheme.typography.headlineSmall,
            lineHeight = 30.sp
        )
        Spacer(modifier = Modifier.height(20.dp))

        question.options.forEachIndexed { index, option ->
            val selected = selectedOptionIndex == index
            OutlinedButton(
                onClick = { onOptionClick(index) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = if (selected) {
                        MaterialTheme.colorScheme.primary.copy(.33f)
                    } else Color.Transparent
                ),
                border = BorderStroke(
                    1.dp, LocalContentColor.current
                )
            ) {
                Text(
                    text = option,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Start,
                    fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = onSubmitClick,
            enabled = selectedOptionIndex != null,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Submit")
        }
    }
}

@Composable
private fun QuizResultScreen(
    modifier: Modifier = Modifier,
    score: Int,
    totalQuestions: Int,
    onBackClick: () -> Unit,
    onLeaveClick: () -> Unit,
    onTryAgainClick: () -> Unit
) {
    QuizContainer("Quiz Complete", onBackClicked = onBackClick, modifier = modifier) {
        Text(
            text = "You scored $score out of $totalQuestions",
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(24.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedButton(
                onClick = onLeaveClick,
                modifier = Modifier.weight(1f),
                border = BorderStroke(
                    1.dp, LocalContentColor.current
                )
            ) {
                Text("Leave")
            }
            Button(
                onClick = onTryAgainClick,
                modifier = Modifier.weight(1f)
            ) {
                Text("Try Again")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun QuizContainer(
    title: String,
    onBackClicked: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(title) },
                navigationIcon = {
                    IconButton(onClick = onBackClicked) {
                        Icon(Icons.AutoMirrored.Rounded.ArrowBack, "Back")
                    }
                }
            )
        }
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(it)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(PaddingValues(20.dp)),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    content = content
                )
            }
        }

    }
}

@Preview
@Composable
private fun QuizScreenPreview() {
    QuizScreen({})
}