package se.liu.andan346.tetris.gui;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class TetrisSplashComponent extends JComponent
{
    private final JFrame frame;
    private float opacity = 0.0f;
    private final static int ANIM_DURATION = 3000;
    private final static int FADE_DURATION = 1000;
    private final static int FPS = 30;

    public TetrisSplashComponent(Dimension size, JFrame frame) {
        this.frame = frame;
        setPreferredSize(size);
    }

    public void show() {
        final JRootPane rootPane = frame.getRootPane();
        final JComponent glassPane = (JComponent) rootPane.getGlassPane();

        SwingUtilities.invokeLater(new Runnable() {
            @Override public void run() {
                glassPane.setLayout(new BorderLayout());
                glassPane.add(TetrisSplashComponent.this, BorderLayout.CENTER);
                glassPane.setVisible(true);
                glassPane.revalidate();
                glassPane.repaint();
            }
        });

        try {
            animate();
        } catch (InterruptedException e) {
            System.err.println("Splash screen animation was interrupted!");
            e.printStackTrace();
	}

        Container parent = this.getParent();
        parent.remove(this);
        parent.revalidate();
        parent.repaint();
    }


    private void animate() throws InterruptedException {
        float deltaOpacity = (1/(float)FPS) / (FADE_DURATION/(float)1000);

        while (opacity < 1) {
            opacity += deltaOpacity;
            repaint();
            Thread.sleep(1000 / FPS);
        }

        Thread.sleep(ANIM_DURATION - 2*FADE_DURATION);

        while (opacity > 0) {
            opacity -= deltaOpacity;
            repaint();
            Thread.sleep(1000 / FPS);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        final Graphics2D g2d = (Graphics2D) g;

        // Draw background
        g2d.setColor(getBackground());
        g2d.fillRect(0, 0, getWidth(), getHeight());

        // Draw splash image
        ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource("images/splash.png"));
        Point pos = new Point(getWidth()/2 - icon.getIconWidth()/2,
                              getHeight()/2 - icon.getIconHeight()/2);
        setOpacity(g2d);
        icon.paintIcon(this, g2d, pos.x, pos.y);
    }

    private void setOpacity(Graphics2D g2d) {
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, Math.clamp(opacity, 0, 1)));
    }
}
