package com.wix;

import com.wix.controller.ConsoleGameController;
import com.wix.controller.GameController;
import com.wix.service.DefaultGameLogicService;
import com.wix.service.GameLogicService;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Application {
    public static void main(String[] args) {
        final int boardSize = 4;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        final int shuffleDepth = (int) Math.pow(boardSize, boardSize);
        GameLogicService gameLogicService = new DefaultGameLogicService(boardSize, shuffleDepth);
        GameController gameController = new ConsoleGameController(gameLogicService, reader);
        gameController.start();
    }
}
