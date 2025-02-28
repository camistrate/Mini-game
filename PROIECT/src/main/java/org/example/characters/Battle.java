package org.example.characters;

public interface Battle {
    void receiveDamage(int damage, boolean isComingFromInterface);
    int getDamage(boolean isComingFromInterface);
}
