package com.wix;

import com.wix.domain.Direction;
import com.wix.exception.IllegalMovePosition;
import com.wix.exception.IllegalTailNumber;
import com.wix.service.DefaultGameLogicService;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class GameLogicServiceTest {
    private DefaultGameLogicService gameLogicService;
    private static final int BOARD_SIZE = 4;

    private final int[][] board = {
            {1,2,3,4},
            {5,6,7,8},
            {9,10,0,12},
            {13,14,11,15}
    };

    @Before
    public void setUp() {
        gameLogicService = new DefaultGameLogicService(board, BOARD_SIZE);
    }

    @Test
    public void moveLeftSuccess() {
        gameLogicService.makeMove(12, Direction.LEFT);
        int[][] boardAsMatrix = gameLogicService.getBoardAsMatrix();

        assertEquals(12, boardAsMatrix[2][2]);
        assertEquals(0, boardAsMatrix[2][3]);
    }

    @Test(expected = IllegalTailNumber.class)
    public void moveLeftFailEmpty() {
        gameLogicService.makeMove(0, Direction.LEFT);
    }

    @Test(expected = IllegalMovePosition.class)
    public void moveLeftFailOutOfBounds() {
        gameLogicService.makeMove(8, Direction.LEFT);
    }

    @Test(expected = IllegalMovePosition.class)
    public void moveLeftFailToNonEmpty() {
        gameLogicService.makeMove(14, Direction.LEFT);
    }

    @Test
    public void moveRightSuccess() {
        gameLogicService.makeMove(10, Direction.RIGHT);
        int[][] boardAsMatrix = gameLogicService.getBoardAsMatrix();

        assertEquals(10, boardAsMatrix[2][2]);
        assertEquals(0, boardAsMatrix[2][1]);
    }

    @Test(expected = IllegalTailNumber.class)
    public void moveRightFailEmpty() {
        gameLogicService.makeMove(0, Direction.RIGHT);
    }

    @Test(expected = IllegalMovePosition.class)
    public void moveRightFailOutOfBounds() {
        gameLogicService.makeMove(12, Direction.RIGHT);
    }

    @Test(expected = IllegalMovePosition.class)
    public void moveRightFailToNonEmpty() {
        gameLogicService.makeMove(9, Direction.RIGHT);
    }

    @Test
    public void moveUpSuccess() {
        gameLogicService.makeMove(11, Direction.UP);
        int[][] boardAsMatrix = gameLogicService.getBoardAsMatrix();

        assertEquals(11, boardAsMatrix[2][2]);
        assertEquals(0, boardAsMatrix[3][2]);
    }

    @Test(expected = IllegalTailNumber.class)
    public void moveUpFailEmpty() {
        gameLogicService.makeMove(0, Direction.UP);
    }

    @Test(expected = IllegalMovePosition.class)
    public void moveUpFailOutOfBounds() {
        gameLogicService.makeMove(4, Direction.UP);
    }

    @Test(expected = IllegalMovePosition.class)
    public void moveUpFailToNonEmpty() {
        gameLogicService.makeMove(10, Direction.UP);
    }
/*    int[][] board = {
            {1,2,3,4},
            {5,6,7,8},
            {9,10,0,12},
            {13,14,11,15}*/
    @Test
    public void moveDownSuccess() {
        gameLogicService.makeMove(7, Direction.DOWN);
        int[][] boardAsMatrix = gameLogicService.getBoardAsMatrix();

        assertEquals(7, boardAsMatrix[2][2]);
        assertEquals(0, boardAsMatrix[1][2]);
    }

    @Test(expected = IllegalTailNumber.class)
    public void moveDownFailEmpty() {
        gameLogicService.makeMove(0, Direction.DOWN);
    }

    @Test(expected = IllegalMovePosition.class)
    public void moveDownFailOutOfBounds() {
        gameLogicService.makeMove(4, Direction.DOWN);
    }

    @Test(expected = IllegalMovePosition.class)
    public void moveDownFailToNonEmpty() {
        gameLogicService.makeMove(10, Direction.DOWN);
    }

    @Test(expected = IllegalTailNumber.class)
    public void moveDownFailIllegalNumber() {
        gameLogicService.makeMove(16, Direction.DOWN);
    }

    @Test
    public void testIsWinSuccess() {
        int[][] winPosition = {
                {1,2,3,4},
                {5,6,7,8},
                {9,10,11,12},
                {13,14,15,0}
        };
        gameLogicService = new DefaultGameLogicService(winPosition, BOARD_SIZE);
        assertTrue(gameLogicService.isWin());
    }

    @Test
    public void testIsWinFail() {
        int[][] winPosition = {
                {1,2,3,4},
                {5,6,7,8},
                {9,10,11,12},
                {13,14,0,15}
        };
        gameLogicService = new DefaultGameLogicService(winPosition, BOARD_SIZE);
        assertFalse(gameLogicService.isWin());
    }


    @Test
    public void getBoardAsMatrix() {
        int[][] boardAsMatrix = gameLogicService.getBoardAsMatrix();
        for (int i = 0; i < boardAsMatrix.length; i++) {
            assertArrayEquals(board[i], boardAsMatrix[i]);
        }
    }
}