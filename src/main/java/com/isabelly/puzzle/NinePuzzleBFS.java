package com.isabelly.puzzle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class NinePuzzleBFS {

    // -------------------------------------------------------------------------
    // ESTADO OBJETIVO
    // -------------------------------------------------------------------------
    static final int[][] GOAL = {
        {1, 2, 3},
        {4, 5, 6},
        {7, 8, 0}
    };

    // -------------------------------------------------------------------------
    // CLASSE STATE — Seção 3.1
    //   board  → configuração atual do tabuleiro (matriz 3x3)
    //   parent → estado anterior (para reconstruir o caminho)
    //   action → movimento que gerou este estado
    //   cost   → custo acumulado g(n) — usado pelo listOrd.sort()
    // -------------------------------------------------------------------------
    static class State {
        int[][] board;
        State   parent;
        String  action;
        int     cost;

        State(int[][] board, State parent, String action, int cost) {
            this.board  = board;
            this.parent = parent;
            this.action = action;
            this.cost   = cost;
        }
    }
    static final int[]    MOVE_DR   = {-1,  1,  0,  0};
    static final int[]    MOVE_DC   = { 0,  0, -1,  1};
    static final String[] MOVE_NAME = {"CIMA", "BAIXO", "ESQUERDA", "DIREITA"};

    // =========================================================================
    // criarFilhos — Seção 3.2
    // Gera todos os sucessores válidos de um nó, testando os 4 movimentos
    // e descartando os que saem dos limites do tabuleiro.
    // =========================================================================
    static List<State> criarFilhos(State no) {
        List<State> filhos = new ArrayList<>();
        int[] zero = findZero(no.board);
        int zr = zero[0], zc = zero[1];

        for (int i = 0; i < 4; i++) {
            int nr = zr + MOVE_DR[i];
            int nc = zc + MOVE_DC[i];
            if (nr < 0 || nr > 2 || nc < 0 || nc > 2) continue;

            int[][] nb = copyBoard(no.board);
            nb[zr][zc] = nb[nr][nc]; // troca branco com vizinho
            nb[nr][nc] = 0;

            filhos.add(new State(nb, no, MOVE_NAME[i], no.cost + 1));
        }
        return filhos;
    }

    // =========================================================================
    // BFS — Seção 3.3
    // Implementa fielmente o pseudocódigo da professora:
    //   - LinkedList<State> listOrd
    //   - HashSet<String>   listaV
    //   - do/while
    //   - poda na remoção
    //   - addAll(criarFilhos())
    //   - sort() explícito por custo após inserir os filhos
    // =========================================================================
    static State bfs(int[][] start) {
        LinkedList<State> listOrd = new LinkedList<>(); // listOrd
        Set<String> listaV = new HashSet<>();           // listaV (hash)
        boolean achou = false;
        State no = null;

        listOrd.add(new State(start, null, "INICIO", 0));

        do {
            no = listOrd.removeFirst();                          // removeFirst()
            if (!listaV.contains(boardToKey(no.board))) {       // poda na remoção
                listaV.add(boardToKey(no.board));

                if (ehObjetivo(no.board)) {
                    achou = true;
                } else {
                    listOrd.addAll(criarFilhos(no));             // criarFilhos()
                    listOrd.sort(Comparator.comparingInt(s -> s.cost)); // sort()
                }
            }
        } while (!achou && !listOrd.isEmpty());

        return achou ? no : null;
    }

    // =========================================================================
    // UTILITÁRIOS
    // =========================================================================

    static String boardToKey(int[][] b) {
        StringBuilder sb = new StringBuilder();
        for (int[] row : b)
            for (int v : row) sb.append(v).append(',');
        return sb.toString();
    }

    static boolean ehObjetivo(int[][] b) {
        return boardToKey(b).equals(boardToKey(GOAL));
    }

    static int[] findZero(int[][] b) {
        for (int r = 0; r < 3; r++)
            for (int c = 0; c < 3; c++)
                if (b[r][c] == 0) return new int[]{r, c};
        return null;
    }

    static int[][] copyBoard(int[][] b) {
        int[][] copy = new int[3][3];
        for (int r = 0; r < 3; r++)
            copy[r] = Arrays.copyOf(b[r], 3);
        return copy;
    }

    // =========================================================================
    // buildPath — Seção 3.4
    // Percorre os ponteiros parent de trás para frente,
    // inserindo cada estado no índice 0 para obter a sequência correta.
    // =========================================================================
    static List<State> buildPath(State goal) {
        List<State> path = new ArrayList<>();
        State cur = goal;
        while (cur != null) {
            path.add(0, cur); // insere no inicio
            cur = cur.parent;
        }
        return path;
    }

    // =========================================================================
    // IMPRESSÃO DO TABULEIRO NO CONSOLE
    // =========================================================================
    static void printBoard(int[][] board) {
        System.out.println("+-------+");
        for (int r = 0; r < 3; r++) {
            System.out.print("| ");
            for (int c = 0; c < 3; c++) {
                if (board[r][c] == 0)
                    System.out.print("  ");
                else
                    System.out.print(board[r][c] + " ");
            }
            System.out.println("|");
        }
        System.out.println("+-------+");
    }

    static void printSolution(String puzzleName, int[][] start, List<State> path) {
        System.out.println("=".repeat(45));
        System.out.println("  PUZZLE: " + puzzleName);
        System.out.println("=".repeat(45));

        if (path == null) {
            System.out.println("  Sem solução para este tabuleiro.");
            System.out.println();
            return;
        }

        System.out.println("  Total de passos: " + (path.size() - 1));
        System.out.println("-".repeat(45));

        for (int i = 0; i < path.size(); i++) {
            State s = path.get(i);
            if (i == 0)
                System.out.println("  Passo 0 — ESTADO INICIAL");
            else if (i == path.size() - 1)
                System.out.println("  Passo " + i + " — " + s.action + "  ✓ OBJETIVO!");
            else
                System.out.println("  Passo " + i + " — Mover branco: " + s.action);

            printBoard(s.board);
        }
        System.out.println();
    }
}
