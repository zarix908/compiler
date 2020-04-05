package parser

import java.util.*

typealias TransitionsTable = MutableMap<Int, MutableMap<SyntaxUnit, Transition>>

fun buildParser(unit: SyntaxUnit): TransitionsTable {
    val transitionsTable =
        mutableMapOf<Int, MutableMap<SyntaxUnit, Transition>>()

    val queue: Queue<Pair<Int, Set<LRItem>>> = LinkedList()
    val startSet= LRItem.buildFromUnit(unit)
            .flatMap { i -> i.closure() }
            .toSet()
    queue.add(Pair(0, startSet))

    var setsCount = 1
    while (!queue.isEmpty()) {
        val (currentState, currentSet) = queue.peek()
        queue.remove()

        val newSets =
            mutableMapOf<SyntaxUnit, MutableSet<LRItem>>()
        val actions = mutableMapOf<SyntaxUnit, Action>()
        val reduceTo = mutableMapOf<SyntaxUnit, String?>()

        for (item in currentSet) {
            if (item.hasShift) {
                var set = newSets[item.leftUnit()]
                if (set == null) {
                    set = mutableSetOf()
                }
                val shiftedItem = item.shift()
                set.add(shiftedItem)
                newSets[item.leftUnit()] = set
                actions[item.leftUnit()] = if
                   (shiftedItem.hasShift) Action.SHIFT
                    else Action.REDUCE
                reduceTo[item.leftUnit()] = if (shiftedItem.hasShift) null
                    else shiftedItem.name
            }
        }

        val transitions = mutableMapOf<SyntaxUnit, Transition>()
        for ((unit, set) in newSets) {
            transitions[unit] = Transition(actions[unit]!!, setsCount, reduceTo[unit])
            queue.add(Pair(setsCount++, set.closure()))
        }

        transitionsTable[currentState] = transitions
    }

    return transitionsTable
}

data class Transition(val type: Action, val newState: Int, val reduceTo: String?)

enum class Action {
    SHIFT,
    REDUCE
}