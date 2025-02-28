package org.example.graphicInterface;

import org.example.characters.Mage;
import org.example.characters.Rogue;
import org.example.game.Grid;
import org.example.player.Account;
import org.example.characters.Characterrr;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class CharacterSelectionPage extends JFrame {
    private static Account account;

    public CharacterSelectionPage(Account account) {
        this.account = account;

        setTitle("League of Warriors - Character Selection");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1500, 1000);
        setLocationRelativeTo(null);

        // fundal
        JPanel backgroundPanel = new JPanel();
        backgroundPanel.setBackground(new Color(58, 0, 0));
        backgroundPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 50));
        add(backgroundPanel);

        // titlul paginii
        JLabel pageTitle = new JLabel("Select Your Character", SwingConstants.CENTER);
        pageTitle.setFont(new Font("Serif", Font.BOLD, 36));
        pageTitle.setForeground(Color.WHITE);
        pageTitle.setPreferredSize(new Dimension(1500, 100));
        backgroundPanel.add(pageTitle, BorderLayout.NORTH);

        //lista de caractere
        JPanel charactersPanel = new JPanel();
        charactersPanel.setBackground(new Color(58, 0, 0));
        charactersPanel.setLayout(new GridLayout(0, 3, 20, 20)); // 3 coloane
        charactersPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        JScrollPane scrollPane = new JScrollPane(charactersPanel);
        scrollPane.setBorder(null);
        scrollPane.setBackground(new Color(58, 0, 0));
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        backgroundPanel.add(scrollPane, BorderLayout.CENTER);

        // afisare fiecare caracter
        for (int i = 1; i <= account.getNumberOfCharacters(); i++) {
            Characterrr character = account.getCharacter(i - 1);
            JPanel characterPanel = createCharacterPanel(character);
            charactersPanel.add(characterPanel);
        }

        setVisible(true);
    }

    public static Account getAccount() {
        return account;
    }

    private JPanel createCharacterPanel(Characterrr character) {
        /// panou pentru caracter
        JPanel characterPanel = new JPanel();
        characterPanel.setBackground(new Color(108, 0, 0, 255));
        characterPanel.setPreferredSize(new Dimension(300, 400));
        characterPanel.setLayout(null);
        characterPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));

        // poza
        JLabel characterImage = new JLabel();
        characterImage.setBounds(50, 20, 200, 200);
        characterImage.setOpaque(true);
        characterImage.setBackground(Color.DARK_GRAY);

        // incarcare poza
        String photoPath = character.getPhoto();
        ImageIcon originalIcon = new ImageIcon(photoPath);
        Image scaledImage = originalIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        characterImage.setIcon(new ImageIcon(scaledImage));
        characterPanel.add(characterImage);

        // nume caracter
        JLabel nameLabel = new JLabel(character.getName());
        nameLabel.setFont(new Font("Serif", Font.BOLD, 18));
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        nameLabel.setBounds(20, 230, 260, 30);
        characterPanel.add(nameLabel);

        String type;
        if(character instanceof Mage) {
            type = "Mage";
        } else if (character instanceof Rogue) {
            type = "Rogue";
        } else {
            type = "Warrior";
        }

        // detalii caracter
        JLabel detailsLabel = new JLabel(
                String.format("<html>Level: %d<br>Experience: %d<br>Type: %s</html>",
                        character.getLevel(),
                        character.getExperience(),
                        type)
        );
        detailsLabel.setFont(new Font("Serif", Font.PLAIN, 14));
        detailsLabel.setForeground(Color.LIGHT_GRAY);
        detailsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        detailsLabel.setBounds(20, 270, 260, 80);
        characterPanel.add(detailsLabel);

        // buton selectie
        JButton selectButton = new JButton("Select");
        selectButton.setFont(new Font("Serif", Font.BOLD, 16));
        selectButton.setForeground(Color.WHITE);
        selectButton.setBackground(new Color(58, 0, 0));
        selectButton.setBounds(50, 360, 200, 30);
        selectButton.setFocusPainted(false);
        selectButton.addActionListener(e -> selectCharacter(character));
        characterPanel.add(selectButton);

        return characterPanel;
    }

    private void selectCharacter(Characterrr character) {
        JOptionPane.showMessageDialog(this, "Character " + character.getName() + " selected!");
        //PENTRU GENERARE HARTA HARDCODATA:
//        Grid grid = Grid.createGrid(5, 5, character);
//        new GamePage(grid, character);
//        dispose();

        //PENTRU GENERARE HARTA RANDOM:
        Random r = new Random();
        Grid grid = Grid.createRandomGrid(4 + r.nextInt(6), 4 + r.nextInt(6), character);
        new GamePage(grid, character);
        dispose();
    }

}
