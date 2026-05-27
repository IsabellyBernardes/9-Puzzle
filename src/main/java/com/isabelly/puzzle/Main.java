package com.isabelly.puzzle;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        // Puzzle 1
        int[][] p1 = {
            {4, 6, 2},
            {8, 1, 3},
            {7, 5, 0}
        };

        // Puzzle 2
        int[][] p2 = {
            {6, 4, 2},
            {8, 1, 3},
            {7, 5, 0}
        };

        // Puzzle 3 (>= 5 passos)
        int[][] p3 = {
            {1, 2, 3},
            {7, 0, 5},
            {8, 4, 6}
        };

        // Puzzle 4 (>= 5 passos)
        int[][] p4 = {
            {1, 5, 2},
            {4, 8, 3},
            {7, 6, 0}
        };

        run("Puzzle 1  [4 6 2 / 8 1 3 / 7 5 X]", p1);
        run("Puzzle 2  [6 4 2 / 8 1 3 / 7 5 X]", p2);
        run("Puzzle 3  [1 2 3 / 7 X 5 / 8 4 6]", p3);
        run("Puzzle 4  [1 5 2 / 4 8 3 / 7 6 X]", p4);
    }

    static void run(String name, int[][] board) {
        NinePuzzleBFS.State goal = NinePuzzleBFS.bfs(board);
        List<NinePuzzleBFS.State> path = goal != null ? NinePuzzleBFS.buildPath(goal) : null;
        NinePuzzleBFS.printSolution(name, board, path);
    }
}
