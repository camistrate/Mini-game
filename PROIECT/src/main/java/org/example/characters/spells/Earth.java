package org.example.characters.spells;

import org.example.characters.Enemy;
import org.example.characters.Entity;

public class Earth extends Spell {
    public Earth(int damage, int costMana) {
        super(damage, costMana);
    }

    @Override
    public String toString() {
        return "Earth, damage: " + damage + ", cost mana: " + costMana;
    }

    @Override
    public void visit(Entity entity) {
        if (entity.earth) {
            if (entity instanceof Enemy)
                System.out.println("The enemy is immune to earth damage.");
            else
                System.out.println("You are immune to earth damage.");
        } else {
            entity.receiveDamage(damage, false);
        }
    }
}
