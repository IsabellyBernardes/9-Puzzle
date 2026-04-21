package com.isabelly.puzzle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class EightPuzzleBFS {

    // -------------------------------------------------------------------------
    // ESTADO OBJETIVO
    // Toda solução do 8-puzzle termina nesta configuração.
    // -------------------------------------------------------------------------
    static final int[][] GOAL = {
        {1, 2, 3},
        {4, 5, 6},
        {7, 8, 0}   // 0 representa o espaço em branco
    };
 
    // -------------------------------------------------------------------------
    // CLASSE STATE
    // Representa um nó da árvore de busca.
    //   board  → configuração atual do tabuleiro
    //   parent → estado anterior (usado para reconstruir o caminho)
    //   action → nome do movimento que gerou este estado
    // -------------------------------------------------------------------------
    static class State {
        int[][] board;
        State parent;
        String action;
 
        State(int[][] board, State parent, String action) {
            this.board  = board;
            this.parent = parent;
            this.action = action;
        }
    }
 
    // -------------------------------------------------------------------------
    // MOVIMENTOS POSSÍVEIS DO ESPAÇO EM BRANCO
    // Cada movimento = {deslocamento em linha, deslocamento em coluna, nome}
    // -------------------------------------------------------------------------
    static final int[]    MOVE_DR   = {-1,  1,  0,  0};
    static final int[]    MOVE_DC   = { 0,  0, -1,  1};
    static final String[] MOVE_NAME = {"CIMA", "BAIXO", "ESQUERDA", "DIREITA"};
 
    // =========================================================================
    // BFS — Busca em Largura
    // =========================================================================
    static List<State> bfs(int[][] start) {
 
        // --- FILA (estrutura central do BFS) ----------------------------------
        // O BFS usa uma fila FIFO: o primeiro estado inserido é o primeiro
        // a ser processado. Isso garante explorar todos os estados com N passos
        // antes de explorar qualquer estado com N+1 passos → solução ótima.
        Queue<State> queue = new LinkedList<>();
 
        // --- CONJUNTO DE VISITADOS (poda) ------------------------------------
        // Armazena chaves (string) de todos os tabuleiros já avaliados.
        // Sem isso, o BFS revisitaria os mesmos estados infinitamente.
        Set<String> visited = new HashSet<>();
 
        // Insere o estado inicial na fila e marca como visitado
        State initial = new State(start, null, "INÍCIO");
        queue.add(initial);
        visited.add(boardToKey(start));
 
        // --- LOOP PRINCIPAL --------------------------------------------------
        while (!queue.isEmpty()) {
 
            // Retira o próximo estado da fila (FIFO)
            State current = queue.poll();
 
            // Verifica se chegamos ao objetivo
            if (isGoal(current.board)) {
                return buildPath(current); // reconstrói o caminho completo
            }
 
            // Encontra a posição do espaço em branco (0)
            int[] zeroPos = findZero(current.board);
            int zr = zeroPos[0];
            int zc = zeroPos[1];
 
            // Testa cada um dos 4 movimentos possíveis
            for (int i = 0; i < 4; i++) {
                int nr = zr + MOVE_DR[i]; // nova linha do branco
                int nc = zc + MOVE_DC[i]; // nova coluna do branco
 
                // Ignora movimentos que saem dos limites do tabuleiro
                if (nr < 0 || nr > 2 || nc < 0 || nc > 2) continue;
 
                // Gera o novo tabuleiro trocando o branco com a peça vizinha
                int[][] nb = copyBoard(current.board);
                nb[zr][zc] = nb[nr][nc];
                nb[nr][nc] = 0;
 
                String key = boardToKey(nb);
 
                // Poda: só adiciona à fila se ainda não foi visitado
                if (!visited.contains(key)) {
                    visited.add(key);
                    queue.add(new State(nb, current, MOVE_NAME[i]));
                }
            }
        }
 
        return null; // sem solução
    }
 
    // =========================================================================
    // UTILITÁRIOS
    // =========================================================================
 
    // Converte o tabuleiro em uma string única para usar no Set
    // Ex: [[1,2,3],[4,5,6],[7,8,0]] → "1,2,3,4,5,6,7,8,0"
    static String boardToKey(int[][] b) {
        StringBuilder sb = new StringBuilder();
        for (int[] row : b)
            for (int v : row) sb.append(v).append(',');
        return sb.toString();
    }
 
    // Verifica se o tabuleiro atual é igual ao objetivo
    static boolean isGoal(int[][] b) {
        return boardToKey(b).equals(boardToKey(GOAL));
    }
 
    // Localiza a posição do 0 (espaço em branco)
    static int[] findZero(int[][] b) {
        for (int r = 0; r < 3; r++)
            for (int c = 0; c < 3; c++)
                if (b[r][c] == 0) return new int[]{r, c};
        return null;
    }
 
    // Cria uma cópia independente do tabuleiro (evita referências compartilhadas)
    static int[][] copyBoard(int[][] b) {
        int[][] copy = new int[3][3];
        for (int r = 0; r < 3; r++)
            copy[r] = Arrays.copyOf(b[r], 3);
        return copy;
    }
 
    // Reconstrói o caminho do estado inicial até o objetivo
    // seguindo os ponteiros "parent" de trás para frente
    static List<State> buildPath(State goal) {
        List<State> path = new ArrayList<>();
        State cur = goal;
        while (cur != null) {
            path.add(0, cur); // insere no início para ordenar corretamente
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
                    System.out.print("  "); // espaço em branco visível
                else
                    System.out.print(board[r][c] + " ");
            }
            System.out.println("|");
        }
        System.out.println("+-------+");
    }
 
    // Exibe a solução completa passo a passo
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
