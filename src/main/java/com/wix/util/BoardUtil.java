package com.wix.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BoardUtil {
    private static final List<Integer> puzzleNumbers = Arrays.asList(
            1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
            11, 12, 13, 14, 15, 0
    );

    public static int[][] generateRandomBoard(int boardSize) {
        int[][] board = new int[boardSize][boardSize];


        List<Integer> tiles = new ArrayList<>(puzzleNumbers);
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                board[i][j] = tiles.remove((int)(Math.random() * (tiles.size())));
            }
        }

        return board;
    }
}
