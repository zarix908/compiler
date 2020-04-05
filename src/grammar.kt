import parser.SyntaxUnit

val number = SyntaxUnit("number")
val id = SyntaxUnit("id")
val lb = SyntaxUnit("lb")
val rb = SyntaxUnit("rb")
val semicolon = SyntaxUnit("semi")

val prime = SyntaxUnit("prime") { id or number }

val mathOper = SyntaxUnit("math_oper")
val mathPrime = SyntaxUnit("math_prime") { prime + mathOper + prime }
val mathExpr = SyntaxUnit("math_expr")
{ mathPrime or mathPrime + mathOper + mathPrime or lb + mathPrime + rb }

val expr = SyntaxUnit("expr") { mathExpr + semicolon }