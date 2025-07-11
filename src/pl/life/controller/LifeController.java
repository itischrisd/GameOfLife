package pl.life.controller;

import pl.life.model.LifeModel;
import pl.life.view.LifeView;

import javax.swing.SwingUtilities;

public final class LifeController {
    private final LifeModel model;
    private final LifeView view;

    public LifeController(LifeModel model, LifeView view) {
        this.model = model;
        this.view = view;

        view.bindRuleSet(model.ruleSet(), () -> {
        });
        view.setStepAction(this::step);
        view.setResetAction(this::reset);
        view.setRandomAction(this::randomize);
        view.setToggleAction(this::toggle);
    }

    public void start() {
        SwingUtilities.invokeLater(() -> view.render(model.board()));
    }

    private void step() {
        model.step();
        view.render(model.board());
    }

    private void reset() {
        model.clear();
        view.render(model.board());
    }

    private void randomize() {
        model.randomizeCentral(20, 0.35);
        view.render(model.board());
    }

    private void toggle(int r, int c) {
        model.toggleCell(r, c);
        view.render(model.board());
    }
}
