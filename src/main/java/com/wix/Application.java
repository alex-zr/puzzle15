package com.wix;

import com.wix.controller.ConsoleGameController;
import com.wix.controller.GameController;
import com.wix.service.DefaultGameLogicService;
import com.wix.service.GameLogicService;
import com.wix.util.BoardUtil;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Application {
    public static void main(String[] args) {
        final int boardSize = 4;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        GameLogicService gameLogicService = new DefaultGameLogicService(
                BoardUtil.generateRandomBoard(boardSize),
                boardSize);
        GameController gameController = new ConsoleGameController(gameLogicService, reader);
        gameController.start();
    }
}
