package SnakeAndLadder.Models;

import java.util.concurrent.ThreadLocalRandom;

public class Board {

    Cell [][] cells;

    Board(int boardSize, int numSnakes, int numLadder){
        initBoard(boardSize);
        addSnakeAndLadder(cells,numSnakes,numLadder);
    }

    private void addSnakeAndLadder(Cell[][] cells, int numSnakes, int numLadder) {
        while(numSnakes>0){
            int startPoint = ThreadLocalRandom.current().nextInt(0,(cells.length* cells.length)-1);
            int endPoint = ThreadLocalRandom.current().nextInt(0,(cells.length* cells.length)-1);

            if(endPoint>startPoint) continue;
            numSnakes--;

            Jump snakeObj = new Jump(startPoint,endPoint);
            Cell cell = getCell(startPoint);
            cell.jump = snakeObj;


        }

        while(numLadder>0){
            int startPoint = ThreadLocalRandom.current().nextInt(0,(cells.length* cells.length)-1);
            int endPoint = ThreadLocalRandom.current().nextInt(0,(cells.length* cells.length)-1);

            if(endPoint<startPoint) continue;
            numLadder--;

            Jump ladderObj = new Jump(startPoint,endPoint);
            Cell cell = getCell(startPoint);
            cell.jump = ladderObj;
        }

    }

    Cell getCell(int playerPosition) {
        int boardRow = playerPosition / cells.length;
        int boardColumn = (playerPosition % cells.length);
        return cells[boardRow][boardColumn];
    }

    private void initBoard(int boardSize) {
        cells = new Cell[boardSize][boardSize];

        for(int i=0;i<boardSize;i++){
            for(int j=0;j<boardSize;j++){
                cells[i][j] = new Cell();
            }
        }
    }


}
