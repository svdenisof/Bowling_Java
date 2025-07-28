package ru.example.bonuses;

class BonusImpl implements Bonus
{
    private final Integer theBonus;
    private final Integer theGameBonus;

    BonusImpl (final Integer bonus, final Integer gameBonus) {
        theBonus = bonus;
        theGameBonus = gameBonus;
    }

    public Integer calculate() {
        return theBonus + theGameBonus;
    }
}
