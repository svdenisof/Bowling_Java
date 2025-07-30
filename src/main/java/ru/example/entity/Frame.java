package ru.example.entity;

import ru.example.exceptions.BreakRuleException;

import java.util.Optional;

public record Frame(int first, int second, Optional<Integer> third) {

    public Frame {
        rollIsInvalidRange(first);
        rollIsInvalidRange(second);
        third.ifPresent(this::rollIsInvalidRange);
        frameResultIsInvalidRange();
        third.ifPresent(t -> this.isTheValidThirdRollInFrame());
    }

    public boolean isStrike() {
        return first == 10;
    }

    public boolean isSpare() {
        return first < 10 && first + second == 10;
    }

    public boolean hasBonusRoll() {
        return third.isPresent();
    }

    public int rollCount() {
        final int count = 2;
        if (third.isPresent()) {
            return count + 1;
        }
        return count;
    }

    public int firstBonus() {
        return third.orElse(second);
    }

    public int totalPins() {
        return first + second + third().orElse(0);
    }

    private void rollIsInvalidRange(final int rollCount) throws BreakRuleException {
        if (rollCount < 0 || rollCount > 10) {
            throw new BreakRuleException("Количество очков броска за пределами правил игры");
        }
    }

    private void frameResultIsInvalidRange() throws BreakRuleException {
        if (first < 10 && first + second > 10) {
            throw new BreakRuleException("Количество очков фрейма за пределами правил игры");
        }
    }

    private void isTheValidThirdRollInFrame() throws BreakRuleException {
        if (first != 10 && second != 10 && first + second != 10) {
            throw new BreakRuleException("Третий (опциональный) бросок разрешен после страйка или спеар");
        }
    }
}
