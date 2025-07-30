package ru.example;

import ru.example.bonuses.CalculateBonus;
import ru.example.entity.Frame;
import ru.example.exceptions.BowlingGameException;
import ru.example.exceptions.BreakRuleException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public final class Bowling {

    private final List<Frame> frames = new ArrayList<>();

    private final List<Integer> arrRolls = new ArrayList<>();
    private int[] score;
    private int result;
    private Map<Integer, int[]> thePins;

    public void roll(final Map<Integer, int[]> pins) {

        frames.clear();

        for (int i = 1; i <= 10; i++) {
            final int[] rolls = pins.get(i);

            if (rolls == null) {
                throw new BowlingGameException("Фрейм " + i + " пропущен");
            }

            final Frame frame = createFrame(rolls);

            frames.add(frame);
        }
    }

    private Frame createFrame(final int[] rolls) {

        return switch(rolls.length) {
            case 2 -> new Frame(rolls[0], rolls[1], Optional.empty());
            case 3 -> new Frame(rolls[0], rolls[1], Optional.of(rolls[2]));
            default -> throw new BowlingGameException("Ошибка количества подходов во фрейме");
        };
    }

    public Integer score() {

        return game();
    }

    private int[] getAfterOne(final int index) {

        int[] theIndex = null;

        if (thePins.containsKey(index + 2)) {

            theIndex = thePins.get(index + 2);
        }
        return theIndex;
    }

    private boolean isNotLastSpare(final int index) {

        return isNotLastRoll(index) && score[0] != 0 && score[1] != 0;
    }

    private boolean isNotLastStrike(final int index) {

        return isNotLastRoll(index) && score[0] == 10;
    }

    private boolean isNotLastRoll(final int index) {

        return result == 10 && thePins.containsKey(index + 1);
    }

    private boolean isNeedExtraRoll(final int index) {

        return result == 10 && index == 10;
    }

    private Integer game() {

        final var frames = 10;
        Integer score = 0;
        for (var i = 0; i < frames; i++) {
            score += arrRolls.get(i);
        }

        return score;
    }
}
