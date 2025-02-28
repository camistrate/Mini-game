package org.example.characters.spells;

import org.example.characters.Enemy;
import org.example.characters.Entity;

public class Fire extends Spell {
    public Fire(int damage, int costMana) {
        super(damage, costMana);
    }

    @Override
    public String toString() {
        return "Fire, damage: " + damage + ", cost mana: " + costMana;
    }

    @Override
    public void visit(Entity entity) {
        if (entity.fire) {
            if (entity instanceof Enemy)
                System.out.println("The enemy is immune to fire damage.");
            else
                System.out.println("You are immune to fire damage.");
        } else {
            entity.receiveDamage(damage, false);
        }
    }
}
