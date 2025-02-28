package org.example.characters;

import java.util.Random;

public class Rogue extends Characterrr {
    public Rogue(String name, Integer experience, int level) {
        super(name, experience, level);
        this.dexterity = 4 * level;
        this.strength = 3 * level;
        this.charisma = 2 * level;
        this.earth = true;
        this.ice = false;
        this.fire = false;
    }

    @Override
    public int getDamage(boolean isComingFromInterface) {
        if(dexterity > 20) {
            Random random = new Random();
            int nr = random.nextInt(10);
            if(nr < 5) {
                if(!isComingFromInterface) {
                    System.out.println("###  WOW! DOUBLE DAMAGE: " + ((dexterity + 10) * 2) + "  ###");
                }
                return (dexterity + 10) * 2;
            }
        }
        if(!isComingFromInterface) {
            System.out.println("###  DAMAGE: " + (dexterity + 10) + "  ###");
        }
        return (dexterity + 10);
    }

    @Override
    public void receiveDamage(int damage, boolean isComingFromInterface) {
        if(strength > 15 && charisma > 10) {
            Random random = new Random();
            int nr = random.nextInt(10);
            if(nr < 5) {
                if(!isComingFromInterface) {
                    System.out.println("###  LUCKY DAY! YOU RECEIVED LESS DAMAGE: " + (damage / 2) + "  ###");
                }
                currentHealth = currentHealth - (damage / 2);
            } else {
                if(!isComingFromInterface) {
                    System.out.println("###  OH NO! YOU RECEIVED " + damage + " DAMAGE  ###");
                }
                currentHealth = currentHealth  - damage;
            }
        } else {
            if(!isComingFromInterface) {
                System.out.println("###  OH NO! YOU RECEIVED " + damage + " DAMAGE  ###");
            }
            currentHealth = currentHealth  - damage;
        }
    }

    @Override
    public String toString() {
        return name.toUpperCase() + ":  " +
                "EXPERIENCE = " + experience +
                ",  LEVEL = " + level +
                ", TYPE = ROGUE" ;

    }
}
