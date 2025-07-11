package pl.life.model;

import java.util.Arrays;
import java.util.Random;

public final class LifeModel {
    private final boolean[][] current;
    private final boolean[][] next;
    private final int rows;
    private final int cols;
    private final RuleSet ruleSet;

    public LifeModel(int rows, int cols, RuleSet ruleSet) {
        this.rows = rows;
        this.cols = cols;
        this.ruleSet = ruleSet;
        this.current = new boolean[rows][cols];
        this.next = new boolean[rows][cols];
    }

    public int rows() {
        return rows;
    }

    public int cols() {
        return cols;
    }

    public boolean[][] board() {
        return current;
    }

    public RuleSet ruleSet() {
        return ruleSet;
    }

    public void toggleCell(int r, int c) {
        current[r][c] = !current[r][c];
    }

    public void step() {
        for (int r = 0; r < rows; r++)
            for (int c = 0; c < cols; c++)
                next[r][c] = ruleSet.nextState(current[r][c], neighbours(r, c));
        for (int r = 0; r < rows; r++)
            System.arraycopy(next[r], 0, current[r], 0, cols);
    }

    public void clear() {
        for (var row : current) Arrays.fill(row, false);
    }

    public void randomizeCentral(int size, double density) {
        var rng = new Random();
        int r0 = (rows - size) / 2;
        int c0 = (cols - size) / 2;
        for (int r = r0; r < r0 + size; r++)
            for (int c = c0; c < c0 + size; c++)
                current[r][c] = rng.nextDouble() < density;
    }

    private int neighbours(int r, int c) {
        int sum = 0;
        for (int dr = -1; dr <= 1; dr++)
            for (int dc = -1; dc <= 1; dc++)
                if (dr != 0 || dc != 0)
                    sum += current[Math.floorMod(r + dr, rows)][Math.floorMod(c + dc, cols)] ? 1 : 0;
        return sum;
    }
}
