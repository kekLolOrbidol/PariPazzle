package com.example.cool8puzzle.solver

import java.util.*

class PuzleSolver(initial: Puzle) {
    private var steps: MutableList<Puzle>? = null
    private var solvedNode: Node? = null
    private var isSolvable = false

    // find a solution using the A* algorithm
    init {
        val initialNode = Node(initial, 0, null)

        // Java's PriorityQueue is a minimum one by default (ordered in ascending order)
        val solver = PriorityQueue<Node>()

        // insert initial nodes
        solver.add(initialNode)
        solve(solver)
    }

    private fun solve(solver: PriorityQueue<Node>) {
        while (true) {
            // delete min from priority queue
            val delMin = solver.poll()

            if (delMin != null) {
                // if deleted node is the solved puzzle, break
                if (delMin.puzle.isSolved) {
                    solvedNode = delMin
                    isSolvable = true
                    return
                }
                // insert all the neighbouring nodes to the deleted one
                for (p in delMin.puzle.neighbors()) {
                    // do not insert the neighbour identical to deleted board's previous one
                    if (delMin.prevNode == null || p != delMin.prevNode.puzle) {
                        solver.add(
                            Node(
                                p,
                                delMin.numOfMoves + 1,
                                delMin
                            )
                        )
                    }
                }
            }
        }
    }

    // min number of moves to solve initial board
    fun moves(): Int {
        if (steps == null) {
            // if solution hasn't been called yet
            solution()
        }
        // then get size of steps
        return if (isSolvable) {
            steps!!.size - 1
        } else {
            -1
        }
    }

    // sequence of boards in a shortest solution
    fun solution(): MutableList<Puzle>? {
        return if (steps == null) {
            // if solution() not called yet
            steps = mutableListOf()
            if (isSolvable) {
                var current = solvedNode
                while (current != null) {
                    if (current.movement != null) {
                        // add the movement text
                        current.puzle.movement = current.movement
                    } else {
                        // first puzzle (with no previous node, so no movement)
                        current.puzle.movement = "Starting puzzle: "
                    }
                    steps!!.add(current.puzle)
                    current = current.prevNode
                }
                // steps were placed in reverse order (starting with the solved node)
                steps!!.reverse()
                // returns steps
                steps
            } else {
                // if unsolvable return null
                null
            }
        } else {
            // else solution() has already been called; return the stored solution
            steps
        }
    }
    // create immutable object Node to represent each state of the puzzle tiles
    private class Node(
        val puzle: Puzle,
        val numOfMoves: Int,
        val prevNode: Node?
    ) : Comparable<Node> {
        private val priority: Int = puzle.manhattanDistance() + numOfMoves

        var movement: String? = null

        // implement Comparable methods for PriorityQueue
        override fun compareTo(other: Node): Int {
            return when {
                priority > other.priority -> +1
                priority < other.priority -> -1
                else -> numOfMoves.compareTo(other.numOfMoves)
            }
        }
        init {
            // define the priority function as its manhattan distance plus the number of moves so far.
            if (prevNode != null) {
                movement = prevNode.puzle.movement(puzle)
            }
        }

    }
    companion object {
        private const val TAG = "PuzzleSolver.java"

    }
}