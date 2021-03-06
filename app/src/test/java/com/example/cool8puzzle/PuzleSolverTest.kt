package com.example.cool8puzzle

import com.example.cool8puzzle.solver.Puzle
import com.example.cool8puzzle.solver.PuzleSolver
import org.junit.Assert
import org.junit.Test

class PuzleSolverTest {

    @Test
    fun testSolver1() {
        val puzzle = Puzle(
            arrayOf(
                intArrayOf(1, 2, 3),
                intArrayOf(4, 5, 6),
                intArrayOf(7, 8, 0)
            )
        )
        val solver = PuzleSolver(puzzle)
        Assert.assertEquals(solver.moves(), 0)
    }

    @Test
    fun testSolver2() {
        val puzzle = Puzle(
            arrayOf(
                intArrayOf(2, 8, 5),
                intArrayOf(6, 4, 1),
                intArrayOf(7, 0, 3)
            )
        )
        val solver = PuzleSolver(puzzle)
        Assert.assertEquals(solver.moves(), 23)
    }


    @Test
    fun testSolver3() {
        val puzzle = Puzle(
            arrayOf(
                intArrayOf(2, 3, 6),
                intArrayOf(5, 4, 1),
                intArrayOf(0, 7, 8)
            )
        )
        val solver = PuzleSolver(puzzle)
        Assert.assertNotNull(solver.solution())
        Assert.assertEquals(solver.moves(), 18)
    }

}