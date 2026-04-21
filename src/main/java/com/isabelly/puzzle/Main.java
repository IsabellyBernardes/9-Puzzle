package com.isabelly.puzzle;

public class Main {
    public static void main(String[] args) {
 
        // Puzzle 1 (do enunciado)
        int[][] p1 = {
            {4, 6, 2},
            {8, 1, 3},
            {7, 5, 0}
        };
 
        // Puzzle 2 (do enunciado)
        int[][] p2 = {
            {6, 4, 2},
            {8, 1, 3},
            {7, 5, 0}
        };
 
        // Puzzle 3 — escolha própria (≥ 5 passos)
        int[][] p3 = {
            {1, 2, 3},
            {4, 0, 6},
            {7, 5, 8}
        };
 
        // Puzzle 4 — escolha própria (≥ 5 passos)
        int[][] p4 = {
            {1, 2, 3},
            {5, 6, 0},
            {4, 7, 8}
        };
 
        EightPuzzleBFS.printSolution("Puzzle 1  [4 6 2 / 8 1 3 / 7 5 X]", p1, EightPuzzleBFS.bfs(p1));
        EightPuzzleBFS.printSolution("Puzzle 2  [6 4 2 / 8 1 3 / 7 5 X]", p2, EightPuzzleBFS.bfs(p2));
        EightPuzzleBFS.printSolution("Puzzle 3  [1 2 3 / 4 _ 6 / 7 5 8]", p3, EightPuzzleBFS.bfs(p3));
        EightPuzzleBFS.printSolution("Puzzle 4  [1 2 3 / 5 6 _ / 4 7 8]", p4, EightPuzzleBFS.bfs(p4));
    }
}
