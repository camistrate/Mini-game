package org.example.graphicInterface;

import org.example.characters.Characterrr;
import org.example.characters.Enemy;
import org.example.characters.Entity;
import org.example.characters.spells.Spell;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class EnemyEventPage extends JDialog {
    private Characterrr character;
    private Enemy enemy;
    private int turn;

    private JLabel playerHealthLabel, playerManaLabel, enemyHealthLabel, enemyManaLabel, turnLabel, roundLabel;
    private JTextArea messageLabel1, messageLabel2;
    private JButton attackButton, abilityButton;

    boolean canClose;

    public EnemyEventPage(Frame parent, Characterrr character, Enemy enemy) {
        super(parent, true);
        this.character = character;
        this.enemy = enemy;

        setTitle("Battle");
        setSize(1500, 1000);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // dezactivam butonul de inchidere
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                if (canClose) {
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(EnemyEventPage.this,
                            "You cannot close the battle until it's over!", "Warning", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        // panoul principal
        JPanel mainPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        mainPanel.setBackground(new Color(58, 0, 0));

        // panou pentru jucator
        JPanel characterPanel = createEntityPanel(character);
        mainPanel.add(characterPanel);

        // panou pentru buttoane si mesaje
        JPanel centerPanel = createCenterPanel();
        mainPanel.add(centerPanel);

        // panou pentru inamic
        JPanel enemyPanel = createEntityPanel(enemy);
        mainPanel.add(enemyPanel);

        add(mainPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    private JPanel createEntityPanel(Entity entity) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(58, 0, 0));
        panel.setPreferredSize(new Dimension(500, 1000));
        panel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));

        // poza
        JLabel characterImage = new JLabel();
        characterImage.setBounds(50, 20, 500, 500);
        characterImage.setOpaque(true);
        characterImage.setBackground(new Color(58, 0, 0));

        // incarcare poza
        String photoPath = entity.getPhoto();
        ImageIcon originalIcon = new ImageIcon(photoPath);
        Image scaledImage = originalIcon.getImage().getScaledInstance(500, 500, Image.SCALE_SMOOTH);
        characterImage.setIcon(new ImageIcon(scaledImage));
        panel.add(characterImage);

        // informațiile caracterului
        JPanel infoPanel = new JPanel(new GridLayout(2, 1));
        infoPanel.setBackground(new Color(108, 0, 0));

        JLabel healthLabel = new JLabel("Health: " + entity.getCurrentHealth());
        healthLabel.setFont(new Font("Arial", Font.BOLD, 20));
        healthLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel manaLabel = new JLabel("Mana: " + entity.getCurrentMana());
        manaLabel.setFont(new Font("Arial", Font.BOLD, 20));
        manaLabel.setHorizontalAlignment(SwingConstants.CENTER);

        setLabelStyle(healthLabel);
        setLabelStyle(manaLabel);

        infoPanel.add(healthLabel, BorderLayout.CENTER);
        infoPanel.add(manaLabel, BorderLayout.CENTER);

        if (entity instanceof Characterrr) {
            playerHealthLabel = healthLabel;
            playerManaLabel = manaLabel;
        } else if (entity instanceof Enemy) {
            enemyHealthLabel = healthLabel;
            enemyManaLabel = manaLabel;
        }

        panel.add(infoPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createCenterPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));
        panel.setBackground(new Color(58, 0, 0));

        //panoul de sus
        JPanel northPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        northPanel.setBackground(new Color(58, 0, 0));

        JLabel titlePanel = new JLabel("F I G H T !", JLabel.CENTER);
        titlePanel.setFont(new Font("Arial", Font.BOLD, 34));
        titlePanel.setForeground(Color.WHITE);
        titlePanel.setBackground(new Color(58, 0, 0));
        titlePanel.setPreferredSize(new Dimension(500, 300));

        roundLabel = new JLabel("~   R O U N D   1   ~", JLabel.CENTER);
        roundLabel.setFont(new Font("Arial", Font.BOLD, 28));
        roundLabel.setForeground(Color.WHITE);
        roundLabel.setBackground(new Color(58, 0, 0));
        roundLabel.setPreferredSize(new Dimension(500, 300));

        northPanel.add(titlePanel);
        northPanel.add(roundLabel);

        //panoul de la mijloc
        JPanel centerPanel = new JPanel(new GridLayout(2, 1));
        centerPanel.setBackground(new Color(58, 0, 0));

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        buttonPanel.setBackground(new Color(58, 0, 0));

        // buton atac
        attackButton = new JButton("ATTACK");
        attackButton.setFont(new Font("Arial", Font.BOLD, 16));
        attackButton.setBackground(new Color(108, 0, 0));
        attackButton.setForeground(Color.WHITE);
        attackButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3));
        attackButton.setFocusPainted(false);

        // buton abilitate
        abilityButton = new JButton("USE ABILITY");
        abilityButton.setFont(new Font("Arial", Font.BOLD, 16));
        abilityButton.setBackground(new Color(108, 0, 0));
        abilityButton.setForeground(Color.WHITE);
        abilityButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3));
        abilityButton.setFocusPainted(false);

        attackButton.addActionListener(e -> handleAttack());
        abilityButton.addActionListener(e -> handleAbility());

        // adaugam butoanele
        buttonPanel.add(attackButton);
        buttonPanel.add(abilityButton);

        turnLabel = new JLabel("I T ' S   Y O U R   T U R N   !", JLabel.CENTER);
        turnLabel.setFont(new Font("Arial", Font.BOLD, 28));
        turnLabel.setForeground(Color.WHITE);
        turnLabel.setBackground(new Color(58, 0, 0));
        turnLabel.setPreferredSize(new Dimension(500, 300));

        centerPanel.add(turnLabel);
        centerPanel.add(buttonPanel);

        // panoul de jos
        JPanel southPanel = new JPanel(new GridLayout(2, 1));
        southPanel.setBackground(new Color(58, 0, 0));

        messageLabel1 = new JTextArea();
        messageLabel1.setLineWrap(true);
        messageLabel1.setWrapStyleWord(true);
        messageLabel1.setEditable(false);
        messageLabel1.setBackground(new Color(58, 0, 0));
        messageLabel1.setForeground(Color.WHITE);
        messageLabel1.setFont(new Font("Arial", Font.BOLD, 14));
        messageLabel1.setBorder(null);

        southPanel.add(messageLabel1);

        messageLabel2 = new JTextArea();
        messageLabel2.setLineWrap(true);
        messageLabel2.setWrapStyleWord(true);
        messageLabel2.setEditable(false);
        messageLabel2.setBackground(new Color(58, 0, 0));
        messageLabel2.setForeground(Color.WHITE);
        messageLabel2.setFont(new Font("Arial", Font.BOLD, 14));
        messageLabel2.setBorder(null);

        southPanel.add(messageLabel2, CENTER_ALIGNMENT);

        panel.add(northPanel);
        panel.add(centerPanel);
        panel.add(southPanel);

        return panel;
    }

    private void handleAttack() {
        if (turn % 2 == 0) {
            int enemyInitialHealth = enemy.getCurrentHealth();
            int damage = character.getDamage(true);
            enemy.receiveDamage(damage, true);
            int damageToEnemy = enemyInitialHealth - enemy.getCurrentHealth();

            updateLabels();
            messageLabel1.setText("YOUR ATTACK POWER IS " + damage + " DAMAGE.");
            messageLabel2.setText("");

            checkBattleState();
            turn++;
            enemyTurn(damageToEnemy);
        }
    }


    private void handleAbility() {
        if (turn % 2 == 0) {
            JDialog abilityDialog = new JDialog(this, "Choose Ability", true);
            abilityDialog.setLayout(new GridLayout(character.getAbilities().size(), 1));

            for (Spell spell : character.getAbilities()) {
                JButton spellButton = new JButton(spell.getName() +
                        " (Cost: " + spell.getCostMana() + " Damage: " + spell.getDamage() + ")");
                spellButton.setBackground(new Color(108, 0, 0));
                spellButton.setForeground(Color.WHITE);
                spellButton.setFocusPainted(false);
                spellButton.addActionListener(e -> {
                    if (character.getCurrentMana() >= spell.getCostMana()) {
                        int enemyInitialHealth = enemy.getCurrentHealth();
                        character.useAbility(spell, enemy, true);
                        int damageToEnemy = enemyInitialHealth - enemy.getCurrentHealth();

                        updateLabels();
                        messageLabel1.setText("YOU USED " + spell.getName().toUpperCase() +
                                " AND THE ABILITY POWER IS " + spell.getDamage() + " DAMAGE.");
                        messageLabel2.setText("");
                        abilityDialog.dispose();

                        checkBattleState();
                        turn++;
                        enemyTurn(damageToEnemy);
                    } else {
                        JOptionPane.showMessageDialog(this,
                                "Not enough mana!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                });
                abilityDialog.add(spellButton);
            }

            abilityDialog.setSize(400, 300);
            abilityDialog.setLocationRelativeTo(this);
            abilityDialog.setVisible(true);
        }
    }

    private void enemyTurn(int damageToEnemy) {
        if (enemy.getCurrentHealth() > 0) {
            Timer initialDelay = new Timer(2000, e -> {
                SwingUtilities.invokeLater(() -> {
                    turnLabel.setText("I T ' S   E N E M Y ' S   T U R N   !");
                    attackButton.setBackground(new Color(58, 0, 0));
                    abilityButton.setBackground(new Color(58, 0, 0));
                    messageLabel1.setText("");
                    messageLabel2.setText("");
                });

                // prima intarziere dupa mesajul de mai sus
                Timer timer1 = new Timer(2000, ev1 -> {
                    int characterInitialHealth = character.getCurrentHealth();
                    int damageDealt;

                    if (enemy.hasAvailabaleAbility() && new Random().nextBoolean()) {
                        Spell spell = enemy.selectAbility();
                        enemy.useAbility(spell, character, true);
                        damageDealt = spell.getDamage();
                        messageLabel1.setText("ENEMY USED " + spell.getName().toUpperCase() +
                                " AND HIS ABILITY POWER IS " + damageDealt + " DAMAGE.");
                    } else {
                        damageDealt = enemy.getDamage(true);
                        character.receiveDamage(damageDealt, true);
                        messageLabel1.setText("ENEMY ATTACKED AND HIS ATTACK POWER IS " + damageDealt + " DAMAGE.");
                    }

                    // intarzierea a doua - dupa afisarea damage-ului
                    Timer timer2 = new Timer(2000, ev2 -> {
                        int characterDamageTaken = characterInitialHealth - character.getCurrentHealth();

                        messageLabel2.setText(" YOU RECEIVED -" + characterDamageTaken +
                                " DAMAGE AND ENEMY RECEIVED -" + damageToEnemy + " DAMAGE.");

                        updateLabels();
                        checkBattleState();

                        if (enemy.getCurrentHealth() <= 0 || character.getCurrentHealth() <= 0) {
                            dispose();
                            return;
                        }

                        turn++;
                        turnLabel.setText("I T ' S   Y O U R   T U R N   !");
                        attackButton.setBackground(new Color(108, 0, 0));
                        abilityButton.setBackground(new Color(108, 0, 0));
                    });
                    timer2.setRepeats(false);
                    timer2.start();
                });
                timer1.setRepeats(false);
                timer1.start();
            });
            initialDelay.setRepeats(false);
            initialDelay.start();
        }

    }


    private void updateLabels() {
        playerHealthLabel.setText("Health: " + character.getCurrentHealth());
        playerManaLabel.setText("Mana: " + character.getCurrentMana());
        enemyHealthLabel.setText("Health: " + enemy.getCurrentHealth());
        enemyManaLabel.setText("Mana: " + enemy.getCurrentMana());
        roundLabel.setText("~   R O U N D   " + (turn + 1) + "   ~");
        if(turn % 2 == 0) {
            turnLabel.setText("I T ' S   Y O U R   T U R N   !");
            attackButton.setBackground(new Color(108, 0, 0));
        } else {
            turnLabel.setText("I T ' S   E N E M Y ' S   T U R N   !");
            attackButton.setBackground(new Color(58, 0, 0));
        }
    }

    private void checkBattleState() {
        if (enemy.getCurrentHealth() <= 0) {
            JOptionPane.showMessageDialog(this,
                    "You win the battle! Press OK and then X to return to the game.");
            this.dispose();
            canClose = true;
        } else if (character.getCurrentHealth() <= 0) {
            JOptionPane.showMessageDialog(this,
                    "Game over! You died... Press OK and then X to return to the game.");
            this.dispose();
            canClose = true;
        }
    }

    private void setLabelStyle(JLabel label) {
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Arial", Font.BOLD, 16));
    }
}
