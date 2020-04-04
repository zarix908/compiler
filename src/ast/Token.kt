package ast

data class Token(
    val type: TokenType,
    val value: String,
    val startSourcePosition: Int
) {
    val endSourcePosition = startSourcePosition + value.length
}

enum class TokenType(val regexp: String) {
    RESERVED(
        ";|" + "if|else|while|let|" + """\(|\)|\{|\}"""
    ),
    ID("[a-zA-Z][a-zA-Z0-9]*"),
    LOGICOPERATORS("<|>|!=|=="),
    MATHOPERATORS("""\+|\*|-|\/"""),
    NUMBERS("[1-9][0-9]*|0"),
    ASSIGN("=")
}