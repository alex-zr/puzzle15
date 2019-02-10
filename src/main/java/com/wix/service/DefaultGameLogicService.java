package com.wix.service;

import com.wix.domain.Direction;
import com.wix.domain.Position;
import com.wix.exception.IllegalMovePosition;
import com.wix.exception.IllegalTailNumber;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.wix.domain.Direction.DOWN;
import static com.wix.domain.Direction.LEFT;
import static com.wix.domain.Direction.RIGHT;
import static com.wix.domain.Direction.UP;

public class DefaultGameLogicService implements GameLogicService {
    private static final int EMPTY_TILE = 0;
    private static final int[][] WIN_POSITION = {
            {1, 2, 3, 4},
            {5, 6, 7, 8},
            {9, 10, 11, 12},
            {13, 14, 15, EMPTY_TILE}
    };
    private int boardSize;
    private int[][] board;

    public DefaultGameLogicService(int[][] board, int boardSize) {
        this.board = board;
        this.boardSize = boardSize;
    }

    public DefaultGameLogicService(int boardSize, int maxShuffleDepth) {
        this.boardSize = boardSize;
        this.board = generateRandomBoard(maxShuffleDepth);
    }

    @Override
    public void makeMove(int tileNumber) {
        makeMove(this.board, tileNumber);
    }

    private void makeMove(int[][] newBoard, int tileNumber) {
        if (tileNumber == 0) {
            throw new IllegalTailNumber(String.valueOf(tileNumber));
        }
        Position currentPosition = getPositionByNumber(newBoard, tileNumber)
                .orElseThrow(() -> new IllegalTailNumber(String.valueOf(tileNumber)));

        Position destinationPosition = getPositionByNumber(newBoard, EMPTY_TILE)
                .orElseThrow(() -> new IllegalTailNumber(String.valueOf(EMPTY_TILE)));

        shiftTiles(newBoard, currentPosition, destinationPosition);
    }

    private Direction getDirection(Position src, Position dst) {
        if (src.getColumn() == dst.getColumn()) {
            if (src.getRow() > dst.getRow()) {
                return UP;
            } else {
                return DOWN;
            }
        } else if (src.getRow() == dst.getRow()) {
            if (src.getColumn() > dst.getColumn()) {
                return LEFT;
            } else {
                return RIGHT;
            }
        }
        throw new IllegalMovePosition("invalid move to " + dst.getRow() + ", " + dst.getColumn());
    }

    private Optional<Position> getPositionByNumber(int[][] newBoard, int tileNumber) {
        for (int i = 0; i < newBoard.length; i++) {
            for (int j = 0; j < newBoard[i].length; j++) {
                if (newBoard[i][j] == tileNumber) {
                    return Optional.of(new Position(i, j));
                }
            }
        }
        return Optional.empty();
    }

    private void shiftTiles(int[][] newBoard, Position src, Position dst) {
        Direction direction = getDirection(src, dst);
        if (direction == UP) {
            for (int i = dst.getRow(); i < src.getRow(); i++) {
                int dstNumber = newBoard[i + 1][dst.getColumn()];
                newBoard[i + 1][src.getColumn()] = newBoard[i][src.getColumn()];
                newBoard[i][src.getColumn()] = dstNumber;
            }
        } else if (direction == DOWN) {
            for (int i = dst.getRow(); i > src.getRow(); i--) {
                int dstNumber = newBoard[i][dst.getColumn()];
                newBoard[i][src.getColumn()] = newBoard[i - 1][src.getColumn()];
                newBoard[i - 1][src.getColumn()] = dstNumber;
            }
        } else if (direction == LEFT) {
            for (int i = dst.getColumn(); i < src.getColumn(); i++) {
                int dstNumber = newBoard[dst.getRow()][i + 1];
                newBoard[dst.getRow()][i + 1] = newBoard[src.getRow()][i];
                newBoard[src.getRow()][i] = dstNumber;
            }
        } else if (direction == RIGHT) {
            for (int i = dst.getColumn(); i > src.getColumn(); i--) {
                int dstNumber = newBoard[dst.getRow()][i - 1];
                newBoard[dst.getRow()][i - 1] = newBoard[src.getRow()][i];
                newBoard[src.getRow()][i] = dstNumber;
            }
        }
    }

    private int[][] getBoardClone(int[][] board) {
        int[][] clone = board.clone();
        for (int i = 0; i < board.length; i++) {
            clone[i] = board[i].clone();
        }
        return clone;
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
        return getBoardClone(this.board);
    }

    private int[][] generateRandomBoard(int shuffleDepth) {
        int[][] newBoard = getBoardClone(WIN_POSITION);
        int move = -1;
        List<Integer> possibleMoves;

        for (int i = 0; i < shuffleDepth; i++) {
            possibleMoves = getPossibleMoves(newBoard, move);
            move = possibleMoves.get((int) (Math.random() * (possibleMoves.size())));
            makeMove(newBoard, move);
        }

        return newBoard;
    }

    private List<Integer> getPossibleMoves(int[][] newBoard, int previousMove) {
        List<Integer> possibleMoves = new ArrayList<>();
        Position emptyPosition = getPositionByNumber(newBoard, EMPTY_TILE)
                .orElseThrow(() -> new IllegalTailNumber("Empty tail not found"));

        if (emptyPosition.getRow() - 1 >= 0) {
            possibleMoves.add(newBoard[emptyPosition.getRow() - 1][emptyPosition.getColumn()]);
        }

        if (emptyPosition.getRow() + 1 < newBoard.length - 1) {
            possibleMoves.add(newBoard[emptyPosition.getRow() + 1][emptyPosition.getColumn()]);
        }

        if (emptyPosition.getColumn() - 1 >= 0) {
            possibleMoves.add(newBoard[emptyPosition.getRow()][emptyPosition.getColumn() - 1]);
        }

        if (emptyPosition.getColumn() + 1 < newBoard.length - 1) {
            possibleMoves.add(newBoard[emptyPosition.getRow()][emptyPosition.getColumn() + 1]);
        }

        if (previousMove >= 0 && possibleMoves.contains(previousMove)) {
            possibleMoves.remove(Integer.valueOf(previousMove));
        }

        return possibleMoves;
    }
}
