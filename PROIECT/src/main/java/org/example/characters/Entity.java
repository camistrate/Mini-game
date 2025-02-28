package org.example.characters;

import org.example.characters.spells.Earth;
import org.example.characters.spells.Fire;
import org.example.characters.spells.Ice;
import org.example.characters.spells.Spell;

import java.util.ArrayList;
import java.util.Random;

public abstract class Entity implements Battle, Element<Entity> {
    protected ArrayList<Spell> abilities;

    protected int currentHealth;
    protected int maxHealth;
    protected int currentMana;
    protected int maxMana;

    public boolean earth;
    public boolean fire;
    public boolean ice;

    protected String photo;

    public Entity (int maxHealth, int maxMana, boolean earth, boolean fire, boolean ice) {
        this.currentHealth = maxHealth;
        this.currentMana = maxMana;
        this.maxHealth = maxHealth;
        this.maxMana = maxMana;

        this.earth = earth;
        this.fire = fire;
        this.ice = ice;
    }

    public Entity (int maxHealth, int maxMana) {
        this.currentHealth = maxHealth;
        this.currentMana = maxMana;
        this.maxHealth = maxHealth;
        this.maxMana = maxMana;
    }

    public void regenerateHealth (int newHealth) {
        if (newHealth + currentHealth > maxHealth) {
            currentHealth = maxHealth;
        } else {
            currentHealth = newHealth + currentHealth;
        }
    }

    public void regenerateMana (int newMana) {
        if (newMana + currentMana > maxMana) {
            currentMana = maxMana;
        } else {
            currentMana = newMana + currentMana;
        }
    }

    public int getCurrentHealth () {
        return currentHealth;
    }

    public ArrayList<Spell> getAbilities () {
        return abilities;
    }

    public int getCurrentMana () {
        return currentMana;
    }

    public String getPhoto () {
        return photo;
    }

    @Override
    public void accept(Visitor<Entity> visitor) {
        visitor.visit(this);
    }

    // metoda pentru generarea abilitatilor
    public void generateRandomSpells() {
        Random rand = new Random();
        ArrayList<Spell> randomSpells = new ArrayList<>();

        for (int i = 0; i < rand.nextInt(3) + 3; i++) {
            int damage = rand.nextInt(20) + 20;
            int costMana = rand.nextInt(20) + 30;
            int spellType = rand.nextInt(3);

            if (i == 0 || (spellType == 0 && i != 1 && i != 2)) {
                randomSpells.add(new Fire(damage, costMana));
            } else if (i == 1 || (spellType == 1 && i != 2)) {
                randomSpells.add(new Ice(damage, costMana));
            } else if (i == 2 || spellType == 2) {
                randomSpells.add(new Earth(damage, costMana));
            }
        }
        abilities = randomSpells;
    }

    public void showAbilities () {
        int i = 1;

        for (Spell spell : abilities) {
            if(verifyAbility(spell)) {
                System.out.print("-> ");
            } else {
                System.out.print("   ");
            }
            if(spell instanceof Ice) {
                System.out.print(i + ") ICE: ");
            } else if (spell instanceof Fire) {
                System.out.print(i + ") FIRE: ");
            } else if (spell instanceof Earth) {
                System.out.print(i + ") EARTH: ");
            }
            System.out.println(spell);
            i++;
        }
    }

    public Spell getAbility (int index) {
        return abilities.get(index);
    }

    public boolean hasAvailabaleAbility () {
        if(abilities.isEmpty())
            return false;
        for (Spell spell : abilities) {
            if (spell.getCostMana() <= currentMana) {
                return true;
            }
        }
        return false;
    }

    public Spell selectAbility () {
        Random rand = new Random();
        int number = rand.nextInt(abilities.size());
        while (!verifyAbility(abilities.get(number))) {
            number = rand.nextInt(abilities.size());
        }
        return abilities.get(number);
    }

    public boolean verifyAbility (Spell spell) {
        if(spell.getCostMana() > currentMana) {
            return false;
        } else {
            return true;
        }
    }

    public void useAbility (Spell ability, Entity enemy, boolean isComingFromInterface) {
        enemy.accept(ability);
        abilities.remove(ability);
        currentMana -= ability.getCostMana();
    }

    //generare poza
    public void generateImagePath() {
        Random rand = new Random();
        boolean nr = rand.nextBoolean();
        if (fire) {
            if(this instanceof Enemy) {
                if(nr)
                    photo = "src/main/resources/Photos/enemyFire1.jpg";
                else
                    photo = "src/main/resources/Photos/enemyFire2.jpg";
            } else {
                if(nr)
                    photo = "src/main/resources/Photos/warrior1.jpg";
                else
                    photo = "src/main/resources/Photos/warrior2.jpg";
            }
        } else if (ice) {
            if(this instanceof Enemy) {
                if(nr)
                    photo = "src/main/resources/Photos/enemyIce1.jpg";
                else
                    photo = "src/main/resources/Photos/enemyIce2.jpg";
            } else {
                if(nr)
                    photo = "src/main/resources/Photos/mage1.jpg";
                else
                    photo = "src/main/resources/Photos/mage2.jpg";
            }
        } else if (earth) {
            if(this instanceof Enemy) {
                if(nr)
                    photo = "src/main/resources/Photos/enemyEarth1.jpg";
                else
                    photo = "src/main/resources/Photos/enemyEarth2.jpg";
            } else {
                if(nr)
                    photo = "src/main/resources/Photos/rogue1.jpg";
                else
                    photo = "src/main/resources/Photos/rogue2.jpg";
            }
        } else if(this instanceof Enemy) {
            photo = "src/main/resources/Photos/enemyIce1.jpg";
        }
    }

}
