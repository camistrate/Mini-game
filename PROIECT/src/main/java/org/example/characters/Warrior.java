package org.example.characters;

import java.util.Random;

public class Warrior extends Characterrr {
    public Warrior(String name, Integer experience, int level) {
        super(name, experience, level);
        this.strength = 4 * level;
        this.charisma = 3 * level;
        this.dexterity = 2 * level;
        this.fire = true;
        this.ice = false;
        this.earth = false;
    }

    @Override
    public int getDamage(boolean isComingFromInterface) {
        if(strength > 20) {
            Random random = new Random();
            int nr = random.nextInt(10);
            if(nr < 5) {
                if(!isComingFromInterface) {
                    System.out.println("###  WOW! DOUBLE DAMAGE: " + ((strength + 10) * 2) + "  ###");
                }
                return (strength + 10) * 2;
            }
        }
        if(!isComingFromInterface) {
            System.out.println("###  DAMAGE: " + (strength + 10) + "  ###");
        }
        return (strength + 10);
    }

    @Override
    public void receiveDamage(int damage, boolean isComingFromInterface) {
        if(charisma > 15 && dexterity > 10) {
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
                ", TYPE = WARRIOR" ;

    }
}

