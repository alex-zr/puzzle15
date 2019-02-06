package com.wix.controller;

import com.wix.domain.Direction;
import com.wix.exception.IllegalMovePosition;
import com.wix.exception.IllegalTailNumber;
import com.wix.service.GameLogicService;

import java.io.BufferedReader;
import java.io.IOException;

public class ConsoleGameController implements GameController {
    private GameLogicService gameLogicService;
    private BufferedReader reader;

    public ConsoleGameController(GameLogicService gameLogicService, BufferedReader reader) {
        this.gameLogicService = gameLogicService;
        this.reader = reader;
    }

    public void start() {
        while (true) {
            try {
                System.out.println();
                printBoard(gameLogicService.getBoardAsMatrix());
                System.out.println();
                System.out.println("Input tile number(1-15)");
                String tileNumberStr = reader.readLine();
                int tileNumber = Integer.parseInt(tileNumberStr);
                System.out.println("Input direction(right, left, up, down)");
                String directionStr = reader.readLine();
                Direction direction;
                try {
                    direction = Direction.valueOf(directionStr.toUpperCase());
                } catch (IllegalArgumentException e) {
                    System.err.println("Illegal direction: " + directionStr);
                    continue;
                }
                gameLogicService.makeMove(tileNumber, direction);

                if (gameLogicService.isWin()) {
                    System.out.println("You are win!");
                    return;
                }
            } catch (IOException ie) {
                ie.printStackTrace();
            } catch (NumberFormatException nfe) {
                System.err.println("Incorrect input: " + nfe.getMessage());
            } catch (IllegalMovePosition wmp) {
                System.err.println("Incorrect destination position: " + wmp.getMessage() + ", move impossible");
            } catch (IllegalTailNumber itn) {
                System.err.println("Number incorrect: " + itn.getMessage());
            }
        }
    }

    private void printBoard(int[][] board) {
        for (int[] tiles : board) {
            System.out.print("[");
            for (int i = 0; i < tiles.length; i++) {
                int tileNumber = tiles[i];
                if (tileNumber < 10) {
                    System.out.print(" ");
                }
                if (tileNumber == 0) {
                    System.out.print(" ");
                } else {
                    System.out.print(tileNumber);
                }
                if (i != tiles.length - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println("]");
        }
    }
}
