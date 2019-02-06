package com.wix.util;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class BoardUtilTest {

    @Test
    public void generateRandomBoard() {
        final int boardSize = 4;
        int[][] matrix1 = BoardUtil.generateRandomBoard(boardSize);
        int[][] matrix2 = BoardUtil.generateRandomBoard(boardSize);
        int[][] matrix3 = BoardUtil.generateRandomBoard(boardSize);

        assertFalse(Arrays.deepEquals(matrix1, matrix2));
        assertFalse(Arrays.deepEquals(matrix1, matrix3));
        assertFalse(Arrays.deepEquals(matrix2, matrix3));
    }

}