package pl.life.view;

import pl.life.model.RuleSet;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.Timer;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

public final class LifeView extends JFrame {
    private final LifeBoardPanel boardPanel;
    private final Timer timer;
    private final List<JCheckBox> surviveBoxes;
    private final List<JCheckBox> birthBoxes;

    private Runnable onStep = () -> {
    };
    private Runnable onReset = () -> {
    };
    private Runnable onRandom = () -> {
    };
    private BiConsumer<Integer, Integer> onToggle = (r, c) -> {
    };

    public LifeView(int boardW, int boardH, int rows, int cols) {
        super("Game of Life");

        boardPanel = new LifeBoardPanel(boardW, boardH, rows, cols);
        boardPanel.setBackground(Color.WHITE);
        boardPanel.setForeground(Color.BLACK);
        add(boardPanel, BorderLayout.CENTER);

        var speed = new JSlider(20, 1000, 200);
        var start = new JButton("Start");
        var reset = new JButton("Reset");
        var random = new JButton("Random");

        var top = new JPanel();
        top.add(new JLabel("Tempo (ms)"));
        top.add(speed);
        top.add(start);
        top.add(reset);
        top.add(random);

        surviveBoxes = IntStream.range(0, 10).mapToObj(i -> new JCheckBox(String.valueOf(i))).toList();
        birthBoxes = IntStream.range(0, 10).mapToObj(i -> new JCheckBox(String.valueOf(i))).toList();

        var sPane = new JPanel(new FlowLayout(FlowLayout.LEFT));
        sPane.add(new JLabel("Survive:"));
        surviveBoxes.forEach(sPane::add);

        var bPane = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bPane.add(new JLabel("Birth:     "));
        birthBoxes.forEach(bPane::add);

        var rulesPane = new JPanel(new GridLayout(2, 1));
        rulesPane.add(sPane);
        rulesPane.add(bPane);

        var ctrl = new JPanel(new BorderLayout());
        ctrl.add(top, BorderLayout.NORTH);
        ctrl.add(rulesPane, BorderLayout.SOUTH);
        add(ctrl, BorderLayout.SOUTH);

        timer = new Timer(speed.getValue(), e -> onStep.run());
        speed.addChangeListener(e -> timer.setDelay(speed.getValue()));

        start.addActionListener(e -> {
            if (timer.isRunning()) {
                timer.stop();
                start.setText("Start");
            } else {
                timer.start();
                start.setText("Stop");
            }
        });
        reset.addActionListener(e -> onReset.run());
        random.addActionListener(e -> onRandom.run());
        boardPanel.setToggleAction((r, c) -> onToggle.accept(r, c));

        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void render(boolean[][] board) {
        boardPanel.render(board);
    }

    public void bindRuleSet(RuleSet rules, Runnable onChange) {
        IntConsumer sync = i -> {
            surviveBoxes.get(i).setSelected(rules.survive().get(i));
            birthBoxes.get(i).setSelected(rules.birth().get(i));
        };
        IntStream.range(0, 10).forEach(sync);
        IntStream.range(0, 10).forEach(i ->
                surviveBoxes.get(i).addActionListener(e -> {
                    rules.updateSurvive(i, surviveBoxes.get(i).isSelected());
                    onChange.run();
                }));
        IntStream.range(0, 10).forEach(i ->
                birthBoxes.get(i).addActionListener(e -> {
                    rules.updateBirth(i, birthBoxes.get(i).isSelected());
                    onChange.run();
                }));
    }

    public void setStepAction(Runnable r) {
        onStep = r;
    }

    public void setResetAction(Runnable r) {
        onReset = r;
    }

    public void setRandomAction(Runnable r) {
        onRandom = r;
    }

    public void setToggleAction(BiConsumer<Integer, Integer> r) {
        onToggle = r;
    }
}
