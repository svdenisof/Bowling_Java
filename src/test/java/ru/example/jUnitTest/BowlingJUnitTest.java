package ru.example.jUnitTest;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.example.Bowling;

import java.util.HashMap;

public class BowlingJUnitTest {

    Bowling game;
    Integer score;
    HashMap<Integer, int[]> pins;

    @BeforeEach
    public void tearUp()
    {
        game = new Bowling();
        pins = new HashMap<>();
    }

    @Test
    public void startTheGameAndGetTheScore()
    {
        pins.put(1, new int[]{5, 2});
        pins.put(2, new int[]{7, 3});
        pins.put(3, new int[]{0, 2});
        pins.put(4, new int[]{9, 1});
        pins.put(5, new int[]{8, 0});
        pins.put(6, new int[]{7, 0});
        pins.put(7, new int[]{3, 3});
        pins.put(8, new int[]{5, 0});
        pins.put(9, new int[]{9, 1});
        pins.put(10, new int[]{5, 5});

        game.roll(pins);
        score = game.score();

        Assertions.assertEquals(118, score);
    }

    @AfterEach
    public void tearDown()
    {
        System.out.println(score);
    }
}
