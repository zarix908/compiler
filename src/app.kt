import ast.Token
import ast.TokenType
import parser.LRItem
import parser.buildParser
import tokenizer.Tokenizer
import tokenizer.UnexpectedTokenError

fun main() {
    val t = buildParser(mathExpr)
    println("  | math_prime | math_operator | prime | number | id | rb | lb ")
    for ((i, transitions) in t) {
        print("$i | ")
        for (u in listOf(mathPrime, mathOper, prime, number, id, rb, lb)) {
            val entry = transitions[u]
            print("${entry ?: "-"} | ")
        }
        println()
    }
//    val code = "let number=0;\nlet count=0;\nif(number==0)кер{count=1;}else" +
//                      "{while(number!=0){count=count+1;number=number/10;}}"
//    val tokenizer = Tokenizer(
//        ::Token,
//        TokenType.values()
//    ) { type: TokenType -> type.regexp }
//
//    fun handleError(e: UnexpectedTokenError) {
//        val line = """\n""".toRegex()
//            .findAll(code.substring(0, e.sourcePosition))
//            .count()
//        println("Unexpected token \"${e.value}\", line $line")
//    }
//
//    for (t in tokenizer.tokenize(code, ::handleError)) {
//        println(t)
//    }
}