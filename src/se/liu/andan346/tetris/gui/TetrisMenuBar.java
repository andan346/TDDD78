package se.liu.andan346.tetris.gui;

import se.liu.andan346.tetris.Board;
import se.liu.andan346.tetris.util.BoardListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TetrisMenuBar extends JMenuBar implements BoardListener
{
    private JLabel scoreLabel = null;

    public TetrisMenuBar() {
        build();
    }

    private void build() {
        setBackground(Color.decode("#ffffff"));

        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        final JButton exit = new DefaultButton("Avsluta");
        exit.addActionListener(this::onExit);
        add(exit);

        add(Box.createHorizontalGlue());

        scoreLabel = new JLabel("Poäng: " + 0);
        scoreLabel.setPreferredSize(DefaultButton.preferredSize);
        add(scoreLabel);
    }

    private class DefaultButton extends JButton {
        private Color background = Color.decode("#fafafa");
        private Color backgroundHover = Color.decode("#eeeeee");
        private static Dimension preferredSize = new Dimension(76, 26);

        private DefaultButton(String text) {
            super(text);
            setPreferredSize(preferredSize);
            setFocusable(false);
            setBackground(background);
            setOpaque(true);
            setBorderPainted(false);

            addMouseListener(new MouseAdapter()
            {
                @Override public void mouseEntered(final MouseEvent e) {
                    super.mouseEntered(e);
                    setBackground(backgroundHover);
                }

                @Override public void mouseExited(final MouseEvent e) {
                    super.mouseExited(e);
                    setBackground(background);
                }
            });
        }
    }

    private JButton getDefaultButton() {
        JButton button = new JButton();
        button.setBackground(Color.decode("#eeeeee"));
        return button;
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

    @Override public void boardChanged(Board board) {
        scoreLabel.setText("Poäng: " + board.getScore());
    }

    @Override public void onGameOver(final Board board) {
        // Do nothing
    }
}
