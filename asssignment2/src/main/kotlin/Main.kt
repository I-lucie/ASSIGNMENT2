import kotlin.random.Random

fun generateSecretCode(): List<Char> {
    val colors = listOf('R', 'B', 'Y', 'G', 'O', 'W')
    return List(4) { colors.random() }
}

fun giveFeedback(secretCode: List<Char>, guess: List<Char>): Pair<Int, Int> {
    val correctPosition = secretCode.zip(guess).count { it.first == it.second }
    val correctColors = secretCode.groupBy { it }.map { minOf(it.value.size, guess.count { color -> color == it.key }) }.sum()

    return Pair(correctPosition, correctColors - correctPosition)
}

fun main() {
    val secretCode = generateSecretCode()
    var attempts = 0

    println("Welcome to the Color Code Breaker Game!")

    while (true) {
        print("Enter your guess (4 colors, e.g., RBOW): ")
        val guess = readLine()?.toUpperCase()?.toList()

        if (guess == null || guess.size != 4 || guess.any { it !in listOf('R', 'B', 'Y', 'G', 'O', 'W') }) {
            println("Invalid input. Please enter a valid guess.")
            continue
        }

        attempts++
        val feedback = giveFeedback(secretCode, guess)

        if (feedback.first == 4) {
            println("Congratulations! You guessed the correct code $secretCode in $attempts attempts.")
            break
        } else {
            println("Feedback: ${feedback.first} correct position(s), ${feedback.second} correct color(s) in wrong position.")
        }
    }
}
