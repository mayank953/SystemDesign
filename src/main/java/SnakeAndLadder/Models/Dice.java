package SnakeAndLadder.Models;

import java.util.concurrent.ThreadLocalRandom;

public class Dice {
    int diceCount=1;
    int min =1;
    int max=6;


    public Dice(int diceCount){
        this.diceCount = diceCount;
    }

    public int rollDice(){
        int totalSum =0;
        int diceRolled=0;

        while(diceRolled!=diceCount){
            int number = ThreadLocalRandom.current().nextInt(min,max+1);
            totalSum+=number;
            diceRolled++;
        }

        return totalSum;
    }
}
