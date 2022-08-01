package SnakeAndLadder.Models;

import java.util.Deque;
import java.util.LinkedList;

public class Game {
    Board board;
    Dice dice;
    Player winner;
    Deque<Player> playersList = new LinkedList<>();


    public Game() {
        initializeGame();
    }

    private void initializeGame() {
        board = new Board(5, 2, 2);
        dice = new Dice(1);
        winner = null;
        addPlayer();
    }

    private void addPlayer() {
        Player p1 = new Player("P1", 0);
        Player p2 = new Player("P", 0);
        playersList.add(p1);
        playersList.add(p2);
    }

    public void startGame() {

        while (winner == null) {
            Player playerTurn = findPlayerTurn();
            System.out.println("player turn is:" + playerTurn.Id + " current position is: " + playerTurn.currentPosition);

            int diceNumber = dice.rollDice();

            int playerNewPosition = playerTurn.currentPosition + diceNumber;
            playerNewPosition = jumpCheck(playerNewPosition);
            playerTurn.currentPosition = playerNewPosition;

            System.out.println("player turn is:" + playerTurn.Id + " new Position is: " + playerNewPosition);
            //check for winning condition
            if (playerNewPosition >= board.cells.length * board.cells.length - 1) {

                winner = playerTurn;
            }

        }

    }

    private int jumpCheck(int playerNewPosition) {
        if (playerNewPosition > board.cells.length * board.cells.length - 1) {
            return playerNewPosition;
        }

        Cell cell = board.getCell(playerNewPosition);
        if (cell.jump != null && cell.jump.startPoint == playerNewPosition) {
            String jumpBy = (cell.jump.startPoint < cell.jump.endPoint) ? "ladder" : "snake";
            System.out.println("jump done by: " + jumpBy);
            return cell.jump.endPoint;
        }
        return playerNewPosition;

    }

    private Player findPlayerTurn() {
        Player player = playersList.removeFirst();
        playersList.addFirst(player);
        return player;
    }

}
