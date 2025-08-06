package cymru.asheiou.numberparser

class NumberParser (string: String) {
  private val parts = splitWordNumberToParts(string)

  val bigValueMap = mapOf(
    "billion" to 100000000L,
    "million" to 1000000L,
    "thousand" to 1000L,
    "units" to 1L
  )

  fun parse() {
    var running = 0L
    for ((name, value) in parts) {
      val baseNumber = hundredsToNumber(value)
      running += (baseNumber * bigValueMap[name]!!)
    }
    println(running)
  }

  fun splitWordNumberToParts(input: String): Map<String, String> {
    val categories = listOf("billion", "million", "thousand", "units")
    val result = mutableMapOf<String, String>()

    val tokens = input
      .replace(",", "")
      .replace(" and ", " ")
      .split(" ")
      .filter { it.isNotBlank() }

    val current = mutableListOf<String>()

    for (token in tokens) {
      current.add(token)

      if (token in categories && token != "units") {
        val phrase = current.dropLast(1).joinToString(" ")
        result[token] = phrase.trim()
        current.clear()
      }
    }

    if (current.isNotEmpty()) {
      result["units"] = current.joinToString(" ")
    }

    for (category in categories) {
      if (!result.containsKey(category)) {
        result[category] = ""
      }
    }

    return result
  }

  fun hundredsToNumber(input: String): Int {
    if (input.isBlank()) return 0

    val units = mapOf(
      "zero" to 0, "one" to 1, "two" to 2, "three" to 3, "four" to 4,
      "five" to 5, "six" to 6, "seven" to 7, "eight" to 8, "nine" to 9,
      "ten" to 10, "eleven" to 11, "twelve" to 12, "thirteen" to 13,
      "fourteen" to 14, "fifteen" to 15, "sixteen" to 16,
      "seventeen" to 17, "eighteen" to 18, "nineteen" to 19
    )

    val tens = mapOf(
      "twenty" to 20, "thirty" to 30, "forty" to 40,
      "fifty" to 50, "sixty" to 60, "seventy" to 70,
      "eighty" to 80, "ninety" to 90
    )

    var result = 0
    var current = 0

    val words = input.lowercase()
      .replace("-", " ")
      .split("\\s+".toRegex())
      .filter { it.isNotEmpty() }

    for (word in words) {
      when {
        units.containsKey(word) -> current += units[word]!!
        tens.containsKey(word) -> current += tens[word]!!
        word == "hundred" -> current *= 100
        else -> throw IllegalArgumentException("Invalid number word: $word")
      }
    }
    result += current
    return result
  }
}