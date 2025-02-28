package org.example.characters;

import java.util.Random;

public class Mage extends Characterrr {
    public Mage(String name, Integer experience, int level) {
        super(name, experience, level);
        this.charisma = 4 * level;
        this.dexterity = 3 * level;
        this.strength = 2 * level;
        this.ice = true;
        this.earth = false;
        this.fire = false;
    }

    @Override
    public int getDamage(boolean isComingFromInterface) {
        if(charisma > 20) {
            Random random = new Random();
            int nr = random.nextInt(10);
            if(nr < 5){
                if(!isComingFromInterface){
                    System.out.println("###  WOW! DOUBLE DAMAGE: " + ((charisma + 10) * 2) + "  ###");
                }
                return (charisma + 10) * 2;
            }
        }
        if(!isComingFromInterface){
            System.out.println("###  DAMAGE: " + (charisma + 10) + "  ###");
        }
        return (charisma + 10);
    }

    @Override
    public void receiveDamage(int damage, boolean isComingFromInterface) {
        if(dexterity > 15 && strength > 10) {
            Random random = new Random();
            int nr = random.nextInt(10);
            if(nr < 5) {
                if(!isComingFromInterface){
                    System.out.println("###  LUCKY DAY! YOU RECEIVED LESS DAMAGE: " + (damage / 2) + "  ###");
                }
                currentHealth = currentHealth - (damage / 2);
            } else {
                if(!isComingFromInterface){
                    System.out.println("###  OH NO! YOU RECEIVED " + damage + " DAMAGE  ###");
                }
                currentHealth = currentHealth  - damage;
            }
        } else {
            if(!isComingFromInterface){
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
                ", TYPE = MAGE" ;

    }
}
