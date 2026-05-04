package com.isabelly.puzzle;

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
 
        // Puzzle 3 (≥ 5 passos)
        int[][] p3 = {
            {1, 2, 3},
            {7, 0, 5},
            {8, 4, 6}
        };
 
        // Puzzle 4 (≥ 5 passos)
        int[][] p4 = {
            {1, 5, 2},
            {4, 8, 3},
            {7, 6, 0}
        };
 
        NinePuzzleBFS.printSolution("Puzzle 1  [4 6 2 / 8 1 3 / 7 5 X]", p1, NinePuzzleBFS.bfs(p1));
        NinePuzzleBFS.printSolution("Puzzle 2  [6 4 2 / 8 1 3 / 7 5 X]", p2, NinePuzzleBFS.bfs(p2));
        NinePuzzleBFS.printSolution("Puzzle 3  [1 2 3 / 7 X 5 / 8 4 6]", p3, NinePuzzleBFS.bfs(p3));
        NinePuzzleBFS.printSolution("Puzzle 4  [1 5 2 / 4 8 3 / 7 6 X]", p4, NinePuzzleBFS.bfs(p4));
    }
}
