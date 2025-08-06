package cymru.asheiou.numberparser


fun main() {
  println("Enter the number you wish to parse.")
  val input = readln()
  val parser = NumberParser(input)
  parser.parse()
}
