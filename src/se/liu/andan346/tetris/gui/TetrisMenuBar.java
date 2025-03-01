package se.liu.andan346.tetris.gui;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class TetrisMenuBar extends JMenuBar
{
    public TetrisMenuBar() {
        build();
    }

    private void build() {
        final JButton exit = new JButton("Avsluta");
        exit.setFocusable(false);
        exit.addActionListener(this::onExit);
        add(exit);
    }

    private void onExit(final ActionEvent e) {
        int choice = JOptionPane.showConfirmDialog(
                null,
                "Är du säker på att du vill avsluta?",
                "Konfirmation",
                JOptionPane.YES_NO_OPTION);

        if (choice == JOptionPane.YES_OPTION)
            System.exit(0);
    }
}
