package org.example.game;

import org.example.characters.Characterrr;
import org.example.characters.Enemy;
import org.example.characters.Entity;
import org.example.characters.spells.Spell;
import org.example.exceptions.InvalidCommandException;
import org.example.Game;

import java.util.Random;
import java.util.Scanner;

public class Events {
    private final Characterrr character;

    private static boolean comingFromInterface;

    public Events(Characterrr character) {
        this.character = character;
    }

    public static void setComingFromInterface(boolean type) {
        comingFromInterface = type;
    }

    public void doEvent(CellEntityType eventType) {
        System.out.println();
        System.out.println("***  You are now on a " + eventType + " cell  ***");
        if(eventType == CellEntityType.ENEMY) {
            if(!comingFromInterface) {
                enemyEvent();
            }
        } else if (eventType == CellEntityType.SANCTUARY    ) {
            sanctuaryEvent();
        } else if (eventType == CellEntityType.PORTAL) {
            portalEvent();
        }
    }

    private void enemyEvent() {
        Enemy enemy = new Enemy();

        //generam abilitatile pe care le vor utiliza in batalie
        enemy.generateRandomSpells();
        character.generateRandomSpells();

        int i = 0;
        //cat timp au ambii viata
        while (enemy.getCurrentHealth() > 0 && character.getCurrentHealth() > 0) {
            System.out.println();
            System.out.println("*****  ROUND " + (i + 1) + "  *****");
            System.out.println("YOU:  HEALTH " + character.getCurrentHealth() + ",  MANA " + character.getCurrentMana());
            System.out.println("ENEMY:  HEALTH " + enemy.getCurrentHealth() + ",  MANA " + enemy.getCurrentMana());
            System.out.println();

            // randul jucatorului
            if (i % 2 == 0) {
                System.out.println("***  IT'S YOUR TURN  ***");
                System.out.println();

                if (character.hasAvailabaleAbility()) {
                    System.out.println("SELECT YOUR NEXT MOVE: ");
                    System.out.println("1) ATTACK");
                    System.out.println("2) USE ABILITY");

                    Scanner scanner = new Scanner(System.in);

                    int move;
                    while (true) {
                        try {
                            move = scanner.nextInt();
                            if (move != 1 && move != 2) {
                                throw new InvalidCommandException("Invalid input. Please select between 1 and 2");
                            }
                            break;
                        } catch (InvalidCommandException e) {
                            System.out.println(e.getMessage());
                        } catch (Exception e) {
                            System.out.println("Invalid input type. Please select between 1 and 2");
                            scanner.nextLine();
                        }
                    }

                    if (move == 1) {
                        enemy.receiveDamage(character.getDamage(false), false);
                    } else if (move == 2) {
                        ability(character, enemy);
                    }

                } else {
                    System.out.println("YOU CAN ONLY ATTACK YOUR ENEMY BECAUSE YOU DO NOT HAVE ENOUGH MANA FOR ANY ABLITY");
                    enemy.receiveDamage(character.getDamage(false), false);
                }
            } else {    //randul inamicului
                System.out.println("IT'S ENEMY'S TURN");
                System.out.println();
                System.out.print("HIS NEXT MOVE IS: ");

                if (enemy.hasAvailabaleAbility()) {
                    Random rand = new Random();
                    int move = rand.nextInt(2);

                    if (move == 1) {
                        System.out.println("ATTACK");
                        character.receiveDamage(enemy.getDamage(false), false);
                    } else {
                        System.out.println("USE ABILITY");
                        ability(enemy, character);
                    }
                } else {
                    System.out.println("ATTACK");
                    character.receiveDamage(enemy.getDamage(false), false);
                }
            }
            i++;
        }
            if (enemy.getCurrentHealth() <= 0) {
                System.out.println("YOU WIN THE BATTLE!!!");
                int experience = character.increaseExperienceRandom();
                System.out.println("YOU HAVE NOW MORE EXPERIENCE: +" + experience);
                character.regenerateMana(100);
                character.regenerateHealth(character.getCurrentHealth());
                character.verifyExperience();
            } else {
                System.out.println("GAME OVER! YOU DIED...");
                System.out.println("Going back to the main menu...");
                System.out.println();
                character.regenerateMana(100);
                character.regenerateHealth(100);
                Game.getCharacter();
            }

    }

    private void ability(Entity attacker, Entity enemy) {
        //daca a atacat caracterul
        if(enemy instanceof Enemy) {
            System.out.println("YOUR ABILITIES:");
            character.showAbilities();
            System.out.println("SELECT AN AVAILABLE ABILITY (one with ->) :");

            Scanner scanner = new Scanner(System.in);
            int ability;

            //verificam daca abilitatea aleasa e valida
            while(true) {
                try {
                    ability = scanner.nextInt();
                    if (!character.verifyAbility(character.getAbility(ability - 1))) {
                        throw new InvalidCommandException("Invalid input. Please select an available ability");
                    }
                    break;
                } catch (InvalidCommandException e) {
                    System.out.println(e.getMessage());
                } catch (Exception e) {
                    System.out.println("Invalid input type. Please select an available ability");
                    scanner.nextLine();
                }
            }
            System.out.println(character.getAbility(ability - 1).toString());
            character.useAbility(character.getAbility(ability - 1), enemy, false);
        } else {    //daca a atacat inamicul
            System.out.println("HIS ABILITIES:");
            attacker.showAbilities();
            System.out.print("HIS CHOICE: ");
            Spell choice = attacker.selectAbility();
            System.out.println(choice.toString());
            System.out.println();
            attacker.useAbility(choice, character, false);
        }
    }

    private void sanctuaryEvent() {
        Random rand = new Random();
        int incHealth = rand.nextInt(20) + 15;
        int incMana = rand.nextInt(20) + 15;

        System.out.println("***  YOU RECEIVED +" + incHealth + " HEALTH  AND  +" + incMana + " MANA  ***");
        System.out.println();

        character.regenerateHealth(incHealth);
        character.regenerateMana(incMana);
    }

    private void portalEvent() {
        //resetam viata si mana caracterului si crestem valorile atributelor
        character.regenerateHealth(100);
        character.regenerateMana(100);
        character.increaseAttributes();

        //crestem experienta jucatorului
        character.increaseExperience();
        character.verifyExperience();

        if(!comingFromInterface) {
            System.out.println("***  GOOD JOB! YOU FINISHED THIS LEVEL! ***");
            System.out.println();

            System.out.println("Do you want to go to the next level? (y/n)");

            Scanner input = new Scanner(System.in);
            String answer;
            while (true) {
                try {
                    answer = input.next().toUpperCase();
                    if (!answer.equals("Y") && !answer.equals("N")) {
                        throw new InvalidCommandException("Invalid input. Please select a valid option");
                    }
                    break;
                } catch (InvalidCommandException e) {
                    System.out.println(e.getMessage());
                } catch (Exception e) {
                    System.out.println("Invalid input type. Please select a valid option");
                    input.nextLine();
                }
            }
            Game.finishedLevel(answer);
        }
    }
}
