package ru.example.bonuses;

import lombok.experimental.UtilityClass;

@UtilityClass
public class CalculateBonus {

    public static Integer calculateStrikeBonus(final int... gameBonus) {
        final Bonus bonus = new BonusImpl(10, gameBonus[0] + gameBonus[1]);

        return bonus.calculate();
    }

    public static Integer calculateSpareBonus(final int... gameBonus) {
        final Bonus bonus = new BonusImpl(10, gameBonus[0]);

        return bonus.calculate();
    }

    public static Integer calculateDoubleStrikeBonus(final int... gameBonus) {
        final Bonus bonus = new BonusImpl(20, gameBonus[0]);

        return bonus.calculate();
    }

    public static Integer calculateTripleStrikeBonus() {
        final Bonus bonus = new BonusImpl(30, 0);

        return bonus.calculate();
    }
}
