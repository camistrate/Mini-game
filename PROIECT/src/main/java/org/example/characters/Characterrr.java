package org.example.characters;

import java.util.Random;

public abstract class Characterrr extends Entity implements Battle {
    protected String name;
    protected Integer experience;
    protected int level;
    protected int strength;
    protected int dexterity;
    protected int charisma;

    public Characterrr (String name, Integer experience, int level) {
        super(100, 100);
        this.name = name;
        this.experience = experience;
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public Integer getExperience() {
        return experience;
    }

    public int getLevel() {
        return level;
    }

    public String getType() {
        String type;
        if(this instanceof Mage) {
            type = "Mage";
        } else if (this instanceof Rogue) {
            type = "Rogue";
        } else {
            type = "Warrior";
        }
        return type;
    }

    public void increaseExperience() {
        experience += 5 * level;
    }

    public void verifyExperience() {
        if (experience >= 100) {
            experience -= 100;
            level++;
        System.out.println("***  NEW LEVEL UNLOCKED FOR YOUR CHARACTER: " + level + "  ***");
        System.out.println();
        }
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }

    public int increaseExperienceRandom() {
        Random random = new Random();
        int increment = random.nextInt(30) + 30;
        experience += increment;
        return increment;
    }

    public void increaseAttributes() {
        strength += 2 * level;
        dexterity += 2 * level;
        charisma += 2 * level;
    }

    public void increaseLevel() {
        level++;
    }

    @Override
    public String toString() {
        return name.toUpperCase() + ":  " +
                "EXPERIENCE = " + experience +
                ",  LEVEL = " + level;
    }

}
