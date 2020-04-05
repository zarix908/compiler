package parser

import java.util.*

class LRItem(val definition: MutableList<SyntaxUnit>, val name: String) {
    companion object Factory {
        fun buildFromUnit(unit: SyntaxUnit) =
            unit.definitions!!.map {def -> LRItem(def, unit.name)}
    }

    fun shift() =
        LRItem(definition.drop(1).toMutableList(), name)

    val hasShift get () = definition.size != 0

    fun closure(): MutableList<LRItem> {
        val closure = mutableListOf<LRItem>()

        val visited = hashSetOf(this)
        val queue: Queue<LRItem> = LinkedList()
        queue.add(this)

        while (!queue.isEmpty()) {
            val currentItem: LRItem = queue.peek()
            closure.add(currentItem)
            queue.remove()

            val leftUnit: SyntaxUnit? = currentItem.definition[0]
            if (leftUnit?.definitions == null) {
                continue
            }

            val closureItems = leftUnit.definitions
                .map {def -> LRItem(def, leftUnit.name)}
                .filterNot(visited::contains)
            queue.addAll(closureItems)
        }

        return closure
    }

    fun leftUnit() = definition.first()
}