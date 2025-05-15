package ru.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Bowling {

    private final List<Integer> rolls = new ArrayList<>();
    private int score;

    public void roll(HashMap<Integer, int[]> pins)
    {
        for(var i = 1; i <= pins.size(); i++)
        {
            int[] score = pins.get(i);
            var result = score[0] + score[1];

            if(result == 10 && pins.containsKey(i+1))
            {
                System.out.println("Spare frame " + i + " score " + result);
                result += spareBonus(pins.get(i + 1));
            }

            rolls.add(result);
        }
    }

    public Integer score()
    {
        game();

        return score;
    }

    private Integer spareBonus(int[] pin)
    {
        return bonus(10, pin[0]);
    }

    private Integer bonus(int bonus, int gameBonus)
    {
        return bonus + gameBonus;
    }

    private void game()
    {
        var frames = 10;
        for(var i = 0; i < frames; i++)
        {
            score +=  rolls.get(i);
        }
    }
}
