package ru.example;

import ru.example.exceptions.BreakRuleException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Bowling {

    private final List<Integer> rolls = new ArrayList<>();
    private int score;

    public void roll(HashMap<Integer, int[]> pins) {
        var doubleStrike = false;
        for(var i = 1; i <= pins.size(); i++)
        {
            int[] score = pins.get(i);
            var result = score[0] + score[1];

            if(result == 10 && pins.containsKey(i+1) && score[0] != 0 && score[1] != 0)
            {
                System.out.println("Spare frame " + i + " score " + result);
                result = spareBonus(pins.get(i + 1));
            }
            else if(result == 10 && pins.containsKey(i+1) && score[0] == 10)
            {
                System.out.println("Strike frame " + i + " score " + result);
                result = strikeBonus(pins.get(i + 1));
                if(!doubleStrike)
                {
                    System.out.println("Set Double");
                    doubleStrike = true;
                }
                else
                {
                    // TBD
                    System.out.println("unSet Double");
                    doubleStrike = false;
                }
            }
            else if(i == 10 && result == 10)
            {
                if(score.length < 3)
                    throw new BreakRuleException("Вы должны бросить еще один шар!");

                int[] extraPin = new int[]{score[1], score[0]};
                if(score[0] == 10)
                {
                   result = strikeBonus(extraPin);
                }
                else
                {
                    result = spareBonus(extraPin);
                }
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

    private Integer strikeBonus(int[] pin)
    {
        return bonus(10, pin[0] + pin[1]);
    }

    private Integer doubleStrikeBonus(int[] pin)
    {
        return bonus(20, pin[0]);
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
