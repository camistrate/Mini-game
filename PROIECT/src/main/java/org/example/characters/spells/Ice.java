package org.example.characters.spells;

import org.example.characters.Enemy;
import org.example.characters.Entity;

public class Ice extends Spell {
    public Ice(int damage, int costMana) {
        super(damage, costMana);
    }

    @Override
    public String toString() {
        return "Ice, damage: " + damage + ", cost mana: " + costMana;
    }

    @Override
    public void visit(Entity entity) {
        if (entity.ice) {
            if (entity instanceof Enemy)
                System.out.println("The enemy is immune to ice damage.");
            else
                System.out.println("You are immune to ice damage.");
        } else {
            entity.receiveDamage(damage, false);
        }
    }
}
