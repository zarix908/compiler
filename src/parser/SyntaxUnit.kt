package parser

class SyntaxUnit(
    val name: String,
    getDefinitions: () -> SyntaxUnitDefinitions? = { null }
) {
    val definitions = getDefinitions()

    override fun toString() = "$name = ${definitions ?: "empty"}"

    operator fun plus(other: SyntaxUnit) =
        SyntaxUnitDefinitions(this) + other

    infix fun or(other: SyntaxUnit) =
        SyntaxUnitDefinitions(this) or other

    infix fun or(defs: SyntaxUnitDefinitions) =
        SyntaxUnitDefinitions(this) or defs

    override fun equals(other: Any?): Boolean {
        if (other !is SyntaxUnit) {
            return false
        }

        return name == other.name
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }
}

class SyntaxUnitDefinitions(leftUnit: SyntaxUnit): Iterable<MutableList<SyntaxUnit>> {
    private val definitions = mutableListOf(
        mutableListOf(leftUnit)
    )

    operator fun plus(unit: SyntaxUnit): SyntaxUnitDefinitions {
        definitions.last().add(unit)
        return this
    }

    infix fun or(unit: SyntaxUnit): SyntaxUnitDefinitions {
        definitions.add(mutableListOf(unit))
        return this
    }

    infix fun or(other: SyntaxUnitDefinitions): SyntaxUnitDefinitions {
        definitions.addAll(other.definitions)
        return this
    }

    override fun toString() = definitions
        .joinToString(" | ") {defs ->
            defs.joinToString(" ") {def -> def.name}
        }

    override fun iterator() = definitions.iterator()
}