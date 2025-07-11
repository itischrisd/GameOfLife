package pl.life.view;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.function.BiConsumer;

public final class LifeBoardPanel extends JPanel {
    private final int rows;
    private final int cols;
    private final BufferedImage image;
    private BiConsumer<Integer, Integer> onToggle = (r, c) -> {
    };

    public LifeBoardPanel(int width, int height, int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        setPreferredSize(new Dimension(width, height));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int cellW = image.getWidth() / cols;
                int cellH = image.getHeight() / rows;
                int col = e.getX() / cellW;
                int row = e.getY() / cellH;
                if (row >= 0 && row < rows && col >= 0 && col < cols)
                    onToggle.accept(row, col);
            }
        });
    }

    public void setToggleAction(BiConsumer<Integer, Integer> action) {
        onToggle = action;
    }

    public void render(boolean[][] board) {
        int cellW = image.getWidth() / cols;
        int cellH = image.getHeight() / rows;
        var g = image.createGraphics();
        g.setColor(getBackground());
        g.fillRect(0, 0, image.getWidth(), image.getHeight());
        g.setColor(getForeground());
        for (int r = 0; r < rows; r++)
            for (int c = 0; c < cols; c++)
                if (board[r][c])
                    g.fillRect(c * cellW, r * cellH, cellW, cellH);
        g.dispose();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, null);
    }
}
