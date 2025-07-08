package ru.example.bonuses;

public interface Bonus {

    void setBonus(Integer bonus);
    void setGameBonus(Integer gameBonus);
    Integer calculate();
}
