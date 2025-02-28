    package org.example.characters;

    import java.util.Random;

    public class Enemy extends Entity implements Battle {
        public static Random rand = new Random();
        protected int damage;

        public Enemy() {
            super(
                    rand.nextInt(30) + 40, // maxHealth
                    rand.nextInt(30) + 60, // maxMana
                    rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean() //fire, ice, earth
            );
            damage = rand.nextInt(20) + 10;
        }

        @Override
        public int getDamage(boolean isComingFromInterface) {
            if(rand.nextDouble() < 0.5) {
                if(!isComingFromInterface) {
                    System.out.println("###  ENEMY DAMAGE: " + damage + "  ###");
                }
                return damage;
            } else {
                if(!isComingFromInterface) {
                    System.out.println("###  ENEMY DOUBLE DAMAGE: " + (damage * 2) + "  ###");
                }
                return damage * 2;
            }
        }

        @Override
        public void receiveDamage(int damage, boolean isComingFromInterface) {
            if(rand.nextDouble() < 0.5) {
                if(!isComingFromInterface) {
                    System.out.println("###  WOW! ENEMY RECEIVED " + damage + " DAMAGE  ###");
                }
                currentHealth = currentHealth - damage;
            } else {
                if(!isComingFromInterface) {
                    System.out.println("###  OH NO! THE ENEMY AVOIDED DAMAGE  ###");
                }
            }
        }

    }
