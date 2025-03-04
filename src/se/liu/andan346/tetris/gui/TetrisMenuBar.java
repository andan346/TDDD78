package se.liu.andan346.tetris.gui;

import se.liu.andan346.tetris.Board;
import se.liu.andan346.tetris.util.BoardListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class TetrisMenuBar extends JMenuBar implements BoardListener
{
    private JLabel scoreLabel = null;
    private Board board;

    private List<ButtonListener> listeners = new ArrayList<>();

    public TetrisMenuBar(Board board) {
        this.board = board;
        build();
    }

    private void build() {
        setBackground(Color.decode("#ffffff"));

        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        final JButton exit = new DefaultButton("Avsluta");
        exit.addActionListener(this::onExit);
        add(exit);

        final JButton restart = new DefaultButton("Börja om");
        restart.addActionListener(this::onRestart);
        add(restart);

        add(Box.createHorizontalGlue());

        scoreLabel = new JLabel("Poäng: " + 0);
        FontMetrics metrics = scoreLabel.getFontMetrics(scoreLabel.getFont());
        scoreLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, metrics.stringWidth("00")));
        add(scoreLabel);
    }

    private class DefaultButton extends JButton {
        private Color background = Color.decode("#fafafa");
        private Color backgroundHover = Color.decode("#eeeeee");
        private static Dimension preferredSize = new Dimension(76, 26);

        private DefaultButton(String text) {
            super(text);
            //setPreferredSize(preferredSize);
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
        if (!board.isGamePaused)
            board.togglePause();

        int choice = JOptionPane.showConfirmDialog(
                null,
                "Är du säker på att du vill avsluta?",
                "Konfirmation",
                JOptionPane.YES_NO_OPTION);

        if (board.isGamePaused)
            board.togglePause();

        if (choice == JOptionPane.YES_OPTION)
            System.exit(0);
    }

    private void onRestart(final ActionEvent e) {
        if (!board.isGamePaused)
            board.togglePause();

        int choice = JOptionPane.showConfirmDialog(
                null,
                "Är du säker på att du vill börja om?",
                "Konfirmation",
                JOptionPane.YES_NO_OPTION);

        if (board.isGamePaused)
            board.togglePause();

        if (choice != JOptionPane.YES_OPTION)
            return;

        for (ButtonListener listener : listeners) {
            listener.onRestart();
        }
    }

    public void addButtonListener(ButtonListener bl) {
        this.listeners.add(bl);
    }

    @Override public void boardChanged(Board board) {
        scoreLabel.setText("Poäng: " + board.getScore());
    }

    @Override public void onGameOver(final Board board) {
        // Do nothing
    }
}
