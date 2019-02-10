package com.wix.service;

public interface GameLogicService {
    void makeMove(int tileNumber);

    boolean isWin();

    int[][] getBoardAsMatrix();
}
