import syntax.SyntaxUnit

val number = SyntaxUnit("number")
val id = SyntaxUnit("id")
val prime = SyntaxUnit("prime") {id or number}

val mathOper = SyntaxUnit("math_oper")
val mathPrime = SyntaxUnit("math_prime") {prime + mathOper + prime}
val mathExpr = SyntaxUnit("math_expr")
    {mathPrime or mathPrime + mathOper + mathPrime}

