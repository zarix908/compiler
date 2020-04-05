import ast.Token
import ast.TokenType
import tokenizer.Tokenizer
import tokenizer.UnexpectedTokenError

fun main() {
    println(mathExpr)
    for (u in mathExpr.definitions!!) {
        println(u)
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