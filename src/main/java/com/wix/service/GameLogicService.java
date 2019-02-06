package com.wix.service;

import com.wix.domain.Direction;

public interface GameLogicService {
    boolean isWin();

    void makeMove(int tileNumber, Direction direction);

    int[][] getBoardAsMatrix();
}
