package org.example.characters;

public class CharacterFactory {

    public static Characterrr createCharacter(String profession, String name, int experience, int level) {
        return switch (profession) {
            case "Warrior" -> new Warrior(name, experience, level);
            case "Rogue" -> new Rogue(name, experience, level);
            case "Mage" -> new Mage(name, experience, level);
            default -> throw new IllegalArgumentException("Unknown profession: " + profession);
        };
    }
}
