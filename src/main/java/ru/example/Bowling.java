package ru.example;

import ru.example.bonuses.Bonus;
import ru.example.bonuses.BonusImpl;
import ru.example.exceptions.BreakRuleException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Bowling {

    private final List<Integer> rolls = new ArrayList<>();
    private int score;

    public void roll(HashMap<Integer, int[]> pins) {
        var doubleStrike = false;
        var tripleStrike = false;

        for(var i = 1; i <= pins.size(); i++)
        {
            int[] score = pins.get(i);

            var result = score[0] + score[1];
            if(isSpare(result, pins, i, score))
            {
                result = spareBonus(pins.get(i + 1));
            }
            else if(isStrike(result, pins, i, score))
            {
                int[] next = pins.get(i + 1);
                int[] nextNext;
                var strikeResult = 0;

                nextNext = getAfterOne(pins, i);

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
            else if(isNeedExtraRoll(i, result))
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

    private int[] getAfterOne(HashMap<Integer, int[]> pins, int index)
    {
        if(pins.containsKey(index + 2))
        {
            return pins.get(index + 2);
        }

        return null;
    }

    private boolean isSpare(Integer result, HashMap<Integer, int[]> pins, int index, int[] scores)
    {
        return isNotLastRoll(result, pins, index) && scores[0] != 0 && scores[1] != 0;
    }

    private boolean isStrike(Integer result, HashMap<Integer, int[]> pins, int index, int[] scores)
    {
        return isNotLastRoll(result, pins, index) && scores[0] == 10;
    }

    private boolean isNotLastRoll(Integer result, HashMap<Integer, int[]> pins, int index)
    {
        return result == 10 && pins.containsKey(index + 1);
    }

    private boolean isNeedExtraRoll(int index, int result)
    {
        return result == 10 && index == 10;
    }

    private Integer spareBonus(int[] pin)
    {
        System.out.println("Spare bonus");
        Bonus bonus = new BonusImpl();
        bonus.setBonus(10);
        bonus.setGameBonus(pin[0]);

        return bonus.calculate();
    }

    private Integer strikeBonus(int[] pin)
    {
        System.out.println("Strike bonus for one strike ");
        Bonus bonus = new BonusImpl();
        bonus.setBonus(10);
        bonus.setGameBonus(pin[0] + pin[1]);

        return bonus.calculate();
    }

    private Integer doubleStrikeBonus(int[] pin)
    {
        System.out.println("Strike bonus for two strike ");
        Bonus bonus = new BonusImpl();
        bonus.setBonus(20);
        bonus.setGameBonus(pin[0]);

        return bonus.calculate();
    }

    private Integer tripleStrikeBonus()
    {
        System.out.println("Strike bonus for three strike ");
        Bonus bonus = new BonusImpl();
        bonus.setBonus(30);
        bonus.setGameBonus(0);

        return bonus.calculate();
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
