package org.example.characters;

public interface Visitor <T extends Entity> {
    void visit(T entity);
}
