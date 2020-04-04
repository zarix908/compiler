package tokenizer

typealias TokenFactory<Token, TokenType> = (
    type: TokenType, value: String, pos: Int
) -> Token

class Tokenizer<Token, TokenType>(
    private val tokenFactory: TokenFactory<Token, TokenType>,
    private val tokenTypes: Array<TokenType>,
    private val tokenTypeToRegexp: (type: TokenType) -> String
) where TokenType : Enum<*> {
    fun tokenize(source: String, errorHandler: (e: UnexpectedTokenError) -> Unit) = sequence {
        val whitespace = "WHITESPACE"
        val regex = tokenTypes
            .joinToString("|", "(?:", """|(?<$whitespace>[\n\r\t ])|.)""")
            { t -> "(?<${t.name}>${tokenTypeToRegexp(t)})" }
            .toRegex()

        for (match in regex.findAll(source)) {
            val token = tryGetToken(match)

            if (token != null) {
                yield(token!!) // smart cast not work
                continue
            }

            if (match.groups[whitespace] == null) {
                val error = UnexpectedTokenError(
                    match.range.first,
                    match.groups[0]?.value
                )
                errorHandler(error)
            }
        }
    }

    private fun tryGetToken(match: MatchResult): Token? {
        for (type in tokenTypes) {
            val matchGroup = match.groups[type.name]

            if (matchGroup != null) {
                return tokenFactory(
                    type,
                    matchGroup.value,
                    matchGroup.range.first
                )
            }
        }

        return null
    }
}