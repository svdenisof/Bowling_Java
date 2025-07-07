package ru.example.bonuses;

public class BonusImpl implements Bonus
{
    private Integer theBonus;
    private Integer theGameBonus;

    @Override
    public void setBonus(Integer bonus) {
        theBonus = bonus;
    }

    @Override
    public void setGameBonus(Integer gameBonus) {
        theGameBonus = gameBonus;
    }

    @Override
    public Integer calculate() {
        return theBonus + theGameBonus;
    }
}
