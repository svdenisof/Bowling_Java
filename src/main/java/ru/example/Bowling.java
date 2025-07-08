package ru.example;

import ru.example.bonuses.Bonus;
import ru.example.bonuses.BonusImpl;
import ru.example.exceptions.BreakRuleException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Bowling {

    private final List<Integer> rolls = new ArrayList<>();
    private int[] score;
    private int result;
    private HashMap<Integer, int[]> thePins;

    public void roll(HashMap<Integer, int[]> pins)
    {
        boolean doubleStrike = false;
        boolean tripleStrike = false;
        thePins = pins;

        for(var i = 1; i <= pins.size(); i++)
        {
            score = thePins.get(i);

            result = score[0] + score[1];
            if(isNotLastSpare(i))
            {
                result = spareBonus(thePins.get(i + 1));
            }
            else if(isNotLastStrike(i))
            {
                int[] next = thePins.get(i + 1);
                int[] nextNext;
                var strikeResult = 0;

                nextNext = getAfterOne(i);

                if((next[0] != 10 && !doubleStrike && !tripleStrike))
                {
                    strikeResult = strikeBonus(thePins.get(i + 1));
                }
                else if(next[0] == 10 && (nextNext != null && nextNext[0] != 10) && !tripleStrike)
                {
                    strikeResult = doubleStrikeBonus(thePins.get(i + 2));
                    doubleStrike = true;
                }
                else if(next[0] == 10 && (nextNext != null && nextNext[0] == 10))
                {
                    strikeResult = tripleStrikeBonus();
                    tripleStrike = true;
                }
                else if(((doubleStrike && !tripleStrike) || tripleStrike))
                {
                    int[] prev = thePins.get(i - 1);
                    if(prev[0] != 10)
                    {
                        doubleStrike = false;
                        tripleStrike = false;
                        strikeResult = strikeBonus(thePins.get(i + 1));
                    }
                }

                result += strikeResult;

            }
            else if(isNeedExtraRoll(i))
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
        return game();
    }

    private int[] getAfterOne(int index)
    {
        if(thePins.containsKey(index + 2))
        {
            return thePins.get(index + 2);
        }

        return null;
    }

    private boolean isNotLastSpare(int index)
    {
        return isNotLastRoll(index) && score[0] != 0 && score[1] != 0;
    }

    private boolean isNotLastStrike(int index)
    {
        return isNotLastRoll(index) && score[0] == 10;
    }

    private boolean isNotLastRoll(int index)
    {
        return result == 10 && thePins.containsKey(index + 1);
    }

    private boolean isNeedExtraRoll(int index)
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

    private Integer game()
    {
        var frames = 10;
        Integer score = 0;
        for(var i = 0; i < frames; i++)
        {
            score +=  rolls.get(i);
        }

        return score;
    }
}
