package org.example.graphicInterface;

import org.example.characters.Characterrr;
import org.example.characters.Enemy;
import org.example.game.Cell;
import org.example.game.CellEntityType;
import org.example.game.Grid;
import java.io.PrintStream;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

import static org.example.game.Events.setComingFromInterface;
import static org.example.graphicInterface.CharacterSelectionPage.getAccount;

public class GamePage extends JFrame {

    private JButton[][] gridButtons;
    private Grid gameGrid;
    private Characterrr character;

    private JLabel levelLabel;
    private JLabel experienceLabel;
    private JLabel healthLabel;
    private JLabel manaLabel;
    private JLabel bottomLabel;

    public GamePage(Grid gameGrid, Characterrr character) {
        this.gameGrid = gameGrid;
        this.character = character;

        int rows = gameGrid.getM();
        int cols = gameGrid.getN();

        setTitle("League of Warriors - Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1500, 1000);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // panoul principal
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.RED);
        add(mainPanel);

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout());
        leftPanel.setBackground(new Color(58, 0, 0));

        // panoul pentru partea de jos
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(88, 0, 0));
        bottomPanel.setPreferredSize(new Dimension(mainPanel.getWidth(), 100));
        bottomLabel = new JLabel("SELECT YOUR MOVE");
        bottomLabel.setForeground(Color.WHITE);
        bottomLabel.setFont(new Font("Arial", Font.BOLD, 16));
        bottomLabel.setHorizontalAlignment(SwingConstants.CENTER);

        PrintStream labelStream = new PrintStream(new LabelOutputStream(bottomLabel));
        System.setOut(labelStream);

        bottomPanel.add(bottomLabel);

        leftPanel.add(bottomPanel, BorderLayout.SOUTH);

        // dimensiunea fiecarui buton
        final int CELL_SIZE = 80;

        // panoul pentru tabla de joc
        JPanel gameBoardPanel = new JPanel();
        gameBoardPanel.setLayout(new GridLayout(rows, cols, 6, 6));
        gameBoardPanel.setBackground(new Color(108, 0, 0));
        gameBoardPanel.setBorder(BorderFactory.createLineBorder(new Color(108, 0, 0), 6));
        gridButtons = new JButton[rows][cols];

        // calculare dimensiune tabla
        int boardWidth = cols * (CELL_SIZE + 5);
        int boardHeight = rows * (CELL_SIZE + 5);
        gameBoardPanel.setPreferredSize(new Dimension(boardWidth, boardHeight));

        // initializare tabla de joc
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                gridButtons[i][j] = new JButton();
                gridButtons[i][j].setFont(new Font("Arial", Font.BOLD, 14));
                gridButtons[i][j].setEnabled(false);
                gridButtons[i][j].setPreferredSize(new Dimension(CELL_SIZE, CELL_SIZE));
                gameBoardPanel.add(gridButtons[i][j]);
            }
        }

        updateGridUI();

        // panou pentru controale
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout(10, 10));
        rightPanel.setBackground(new Color(108, 0, 0));
        rightPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3));
        rightPanel.setPreferredSize(new Dimension(250, 800));

        // panou pentru butoanele de mișcare
        JPanel movementPanel = new JPanel(new GridLayout(4, 1, 15, 15));
        movementPanel.setPreferredSize(new Dimension(150, 500));
        movementPanel.setBackground(new Color(108, 0, 0));
        Dimension buttonSize = new Dimension(150, 90);

        JButton moveNorthButton = createMoveButton("North", "W", buttonSize);
        JButton moveSouthButton = createMoveButton("South", "S", buttonSize);
        JButton moveEastButton = createMoveButton("East", "D", buttonSize);
        JButton moveWestButton = createMoveButton("West", "A", buttonSize);

        movementPanel.add(moveNorthButton);
        movementPanel.add(moveSouthButton);
        movementPanel.add(moveEastButton);
        movementPanel.add(moveWestButton);

        rightPanel.add(movementPanel, BorderLayout.NORTH);

        // panou pentru detaliile caracterului
        JPanel characterDetailsPanel = new JPanel();
        characterDetailsPanel.setLayout(new GridLayout(4, 1, 5, 5));
        characterDetailsPanel.setBackground(new Color(108, 0, 0));
        characterDetailsPanel.setPreferredSize(new Dimension(300, 200));
        characterDetailsPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.WHITE),
                "Character Details", 0, 0,
                new Font("Arial", Font.BOLD, 16), Color.WHITE));

        // initializam label-urile pentru detalii
        levelLabel = new JLabel("Level: " + character.getLevel());
        experienceLabel = new JLabel("Experience: " + character.getExperience());
        healthLabel = new JLabel("Health: " + character.getCurrentHealth());
        manaLabel = new JLabel("Mana: " + character.getCurrentMana());

        // le stilizam
        levelLabel.setForeground(Color.WHITE);
        levelLabel.setFont(new Font("Arial", Font.BOLD, 16));
        experienceLabel.setForeground(Color.WHITE);
        experienceLabel.setFont(new Font("Arial", Font.BOLD, 16));
        healthLabel.setForeground(Color.WHITE);
        healthLabel.setFont(new Font("Arial", Font.BOLD, 16));
        manaLabel.setForeground(Color.WHITE);
        manaLabel.setFont(new Font("Arial", Font.BOLD, 16));

        // le adaugam in panoul pentru detalii
        characterDetailsPanel.add(levelLabel);
        characterDetailsPanel.add(experienceLabel);
        characterDetailsPanel.add(healthLabel);
        characterDetailsPanel.add(manaLabel);

        rightPanel.add(characterDetailsPanel, BorderLayout.SOUTH);

        JPanel boardContainer = new JPanel(new GridBagLayout());
        boardContainer.setBackground(new Color(58, 0, 0)); // Rama
        boardContainer.add(gameBoardPanel);
        leftPanel.add(boardContainer, BorderLayout.CENTER);

        // adaugam la panoul principal
        mainPanel.add(rightPanel, BorderLayout.EAST);
        mainPanel.add(leftPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    // creare buton de miscare
    private JButton createMoveButton(String text, String direction, Dimension size) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(58, 0, 0));
        button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3));
        button.setFocusPainted(false);
        button.setPreferredSize(size);
        button.addActionListener(e -> {
            bottomLabel.setText("");
            setComingFromInterface(true);
            gameGrid.makeMove(direction);
            setComingFromInterface(false);

            CellEntityType cellType = gameGrid.getOldType();
            handleCellEvent(cellType);

            updateGridUI();
            updateDetails();
        });
        updateGridUI();
        return button;
    }

    // actualizarea interfetei grafice
    private void updateGridUI() {
        int rows = gameGrid.getM();
        int cols = gameGrid.getN();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Cell cell = gameGrid.getCell(i, j);
                JButton button = gridButtons[i][j];
                String type;

                if(cell.getType().equals(CellEntityType.PLAYER)) {
                    type = "PLAYER";
                } else if (cell.isVisited()) {
                    type = "VISITED";
                } else {
                    type = "NOT VISITED";
                }

                switch (type) {
                    case "VISITED":
                        button.setText(" ");
                        button.setBackground(new Color(38, 0, 0));
                        break;
                    case "PLAYER":
                        button.setText("P");
                        button.setBackground(new Color(152, 30, 30));
                        break;
                    default:
                        button.setText("?");
                        button.setBackground(new Color(58, 0, 0));
                        break;
                }
            }
        }
    }

    private void handleCellEvent(CellEntityType cellType) {
        if (cellType == CellEntityType.ENEMY) {
            Enemy enemy = new Enemy();
            enemy.generateImagePath();
            enemy.generateRandomSpells();
            character.generateRandomSpells();

            EnemyEventPage fightPage = new EnemyEventPage(this, character, enemy);
            // blocam executia pana cand fereastra este inchisa
            fightPage.setVisible(true);

            // dupa lupta
            if (character.getCurrentHealth() <= 0) {
                JOptionPane.showMessageDialog(this,
                        "You have died! Returning to character selection.");

                // resetare viata si mana
                character.regenerateHealth(100);
                character.regenerateMana(100);

                // inchidem fereastra curentam si revenim la pagina de selectie a caracterului
                dispose();
                SwingUtilities.invokeLater(() -> new CharacterSelectionPage(getAccount()));
            } else {
                int experience = character.increaseExperienceRandom();
                JOptionPane.showMessageDialog(this,
                        "You have now more experience: " + experience);
                if (character.getExperience() >= 100) {
                    character.setExperience(character.getExperience() - 100);
                    character.increaseLevel();
                    JOptionPane.showMessageDialog(this,
                            "New level unlocked for your character: " + character.getLevel());
                }

                character.regenerateMana(100);
                character.regenerateHealth(character.getCurrentHealth());
            }
        } else if (cellType == CellEntityType.PORTAL) {
            JOptionPane.showMessageDialog(this,
                    "Congratulations! You have reached the portal and won the game.");

            // crestem numarul de jocuri jucate
            getAccount().increasePlayedGames();

            int choice = JOptionPane.showOptionDialog(
                    this,
                    "Do you want to continue playing with the current character or return to character selection?",
                    "Game Won",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    new Object[]{"Continue Playing", "Choose Another Character"},
                    "Continue Playing"
            );

            dispose();

            if (choice == JOptionPane.NO_OPTION) {
                SwingUtilities.invokeLater(() -> new CharacterSelectionPage(getAccount()));
            } else {
                character.regenerateHealth(100);
                character.regenerateMana(100);

                // generam o tabla noua
                Random r = new Random();
                Grid newGrid = Grid.createRandomGrid(4 + r.nextInt(6), 4 + r.nextInt(6), character);
                SwingUtilities.invokeLater(() -> new GamePage(newGrid, character));
            }
        }
    }

    private void updateDetails() {
        levelLabel.setText("Level: " + character.getLevel());
        experienceLabel.setText("Experience: " + character.getExperience());
        healthLabel.setText("Health: " + character.getCurrentHealth());
        manaLabel.setText("Mana: " + character.getCurrentMana());
    }
}
