package org.example.graphicInterface;

import java.io.OutputStream;
import javax.swing.*;

public class LabelOutputStream extends OutputStream {
    private JLabel label;

    public LabelOutputStream(JLabel label) {
        this.label = label;
    }

    @Override
    public void write(int b) {
        // convertim byte-ul in caracter
        String text = String.valueOf((char) b);
        // actualizam JLabel-ul pe thread-ul de UI
        SwingUtilities.invokeLater(() -> label.setText(label.getText() + text));
    }

    @Override
    public void write(byte[] b, int off, int len) {
        // convertim byte-ul in string
        String text = new String(b, off, len);
        // actualizam JLabel-ul pe thread-ul de UI
        SwingUtilities.invokeLater(() -> label.setText(label.getText() + text));
    }
}
