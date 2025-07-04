package ru.example;

import ru.example.exceptions.BreakRuleException;

import javax.sound.midi.SysexMessage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Bowling {

    private final List<Integer> rolls = new ArrayList<>();
    private int score;
    private int strikeRolls = 0;

    public void roll(HashMap<Integer, int[]> pins) {
        var doubleStrike = false;
        var tripleStrike = false;

        for(var i = 1; i <= pins.size(); i++)
        {
            int[] score = pins.get(i);

            var result = score[0] + score[1];
            if(result == 10 && pins.containsKey(i+1) && score[0] != 0 && score[1] != 0)
            {
                result = spareBonus(pins.get(i + 1));
            }
            else if(result == 10 && pins.containsKey(i+1) && score[0] == 10)
            {

                int[] next = pins.get(i + 1);
                int[] nextNext = null;
                var strikeResult = 0;

                if(pins.containsKey(i+2))
                {
                    nextNext = pins.get(i + 2);
                }

                if((next[0] != 10 && !doubleStrike && !tripleStrike))
                {
                    strikeResult = strikeBonus(pins.get(i + 1));
                }
                else if(next[0] == 10 && (nextNext != null && nextNext[0] != 10) && !tripleStrike)
                {
                    strikeResult = doubleStrikeBonus(pins.get(i + 2));
                    doubleStrike = true;
                }
                else if(next[0] == 10 && (nextNext != null && nextNext[0] == 10))
                {
                    strikeResult = tripleStrikeBonus();
                    tripleStrike = true;
                }
                else if(((doubleStrike && !tripleStrike) || tripleStrike))
                {
                    int[] prev = pins.get(i - 1);
                    if(prev[0] != 10)
                    {
                        doubleStrike = false;
                        tripleStrike = false;
                        strikeResult = strikeBonus(pins.get(i + 1));
                    }
                }

                result += strikeResult;

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
        System.out.println("Spare bonus");
        return bonus(10, pin[0]);
    }

    private Integer strikeBonus(int[] pin)
    {
        System.out.println("Strike bonus for one strike ");
        return bonus(10, pin[0] + pin[1]);
    }

    private Integer doubleStrikeBonus(int[] pin)
    {
        System.out.println("Strike bonus for two strike ");
        return bonus(20, pin[0]);
    }

    private Integer tripleStrikeBonus()
    {
        System.out.println("Strike bonus for three strike ");
        return bonus(30, 0);
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
