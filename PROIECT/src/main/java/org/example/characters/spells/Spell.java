package org.example.characters.spells;

import org.example.characters.Entity;
import org.example.characters.Visitor;

public abstract class Spell implements Visitor<Entity> {
    int damage;
    int costMana;

    public Spell(int damage, int costMana) {
        this.damage = damage;
        this.costMana = costMana;
    }

    public int getDamage() {
        return damage;
    }

    public int getCostMana() {
        return costMana;
    }

    public String getName() {
        if(this instanceof Ice) {
            return "Ice";
        } else if(this instanceof Fire) {
            return "Fire";
        } else {
            return "Earth";
        }
    }

    @Override
    public String toString() {
        return "damage=" + damage +
                ", costMana=" + costMana;
    }
}
