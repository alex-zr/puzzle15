package com.wix.service;

import com.wix.domain.Direction;
import com.wix.exception.IllegalMovePosition;
import com.wix.exception.IllegalTailNumber;
import com.wix.domain.Position;

import java.util.Optional;

public class DefaultGameLogicService implements GameLogicService {
    private int boardSize;
    private int[][] board;

    public DefaultGameLogicService(int [][] board, int boardSize) {
        this.board = board;
        this.boardSize = boardSize;
    }

    @Override
    public void makeMove(int tileNumber, Direction direction) {
        if (tileNumber == 0) {
            throw new IllegalTailNumber("Impossible to move empty tail");
        }
        Optional<Position> currentPosOpt = getPositionByNumber(tileNumber);
        currentPosOpt.orElseThrow(() -> new IllegalTailNumber(String.valueOf(tileNumber)));
        Position currentPosition = currentPosOpt.get();
        Position destinationPosition = new Position(0, 0);
        int destRow;
        int destColumn;
        switch (direction) {
            case LEFT:
                destColumn = currentPosition.getColumn() - 1;
                if (destColumn < 0) {
                    throw new IllegalMovePosition("column is " + destColumn);
                }
                destinationPosition = new Position(currentPosition.getRow(), destColumn);
                break;
            case RIGHT:
                destColumn = currentPosition.getColumn() + 1;
                if (destColumn > boardSize - 1) {
                    throw new IllegalMovePosition("column is " + destColumn);
                }
                destinationPosition = new Position(currentPosition.getRow(), destColumn);
                break;
            case UP:
                destRow = currentPosition.getRow() - 1;
                if (destRow < 0) {
                    throw new IllegalMovePosition("row is " + destRow);
                }
                destinationPosition = new Position(destRow, currentPosition.getColumn());
                break;
            case DOWN:
                destRow = currentPosition.getRow() + 1;
                if (destRow > boardSize - 1) {
                    throw new IllegalMovePosition("row is " + destRow);
                }
                destinationPosition = new Position(destRow, currentPosition.getColumn());
                break;
        }
        int dstNumber = board[destinationPosition.getRow()][destinationPosition.getColumn()];
        if (dstNumber != 0) {
            throw new IllegalMovePosition("Impossible to move to non empty place");
        }
        swapTiles(currentPosition, destinationPosition);
    }

    private Optional<Position> getPositionByNumber(int tileNumber) {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (board[i][j] == tileNumber) {
                    return Optional.of(new Position(i, j));
                }
            }
        }
        return Optional.empty();
    }

    private void swapTiles(Position src, Position dst) {
        int dstNumber = this.board[dst.getRow()][dst.getColumn()];
        this.board[dst.getRow()][dst.getColumn()] = this.board[src.getRow()][src.getColumn()];
        this.board[src.getRow()][src.getColumn()] = dstNumber;
    }

    private int[][] createViewPresentation() {
        return this.board.clone();
    }


    @Override
    public boolean isWin() {
        int counter = 1;
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (i == boardSize - 1 && j == boardSize - 1 && board[i][j] == 0) {
                    return true;
                }
                if (board[i][j] != counter++) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public int[][] getBoardAsMatrix() {
        return createViewPresentation();
    }
}
