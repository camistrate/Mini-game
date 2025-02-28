package org.example.characters;

public interface Element<T extends Entity> {
    void accept(Visitor<T> visitor);
}

