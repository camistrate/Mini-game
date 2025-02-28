This is a turn-based RPG game written in Java, featuring both a command-line and graphical interface (Swing). Players navigate a randomly generated or predefined map, fight enemies, and advance levels while managing their character's stats.

Game Logic
1. User Authentication
Users log in with credentials stored in a JSON file.
If authentication succeeds, they select a character.
2. Character Management
Each character (Warrior, Mage, Rogue) has attributes like:
Level, experience, strength, dexterity, charisma.
Characters gain experience and level up as they progress.
3. Map Generation
Randomly generated or predefined (5×5 grid).
Each cell has a specific type (ENEMY, SANCTUARY, PORTAL, etc.).
Players move using W, A, S, D keys.
4. Cell Interactions
ENEMY – Triggers a battle.
SANCTUARY – Restores health and mana.
PORTAL – Advances to the next level.
5. Combat System
Battles occur between player and enemy in multiple rounds.
Players choose between:
Standard attack or special ability (requires mana).
Enemies attack randomly using standard or special moves.
The battle continues until one runs out of health.
