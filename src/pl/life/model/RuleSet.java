package pl.life.model;

import java.util.BitSet;
import java.util.stream.IntStream;

public record RuleSet(BitSet survive, BitSet birth) {
    public static RuleSet conway() {
        var survive = new BitSet(10);
        survive.set(2);
        survive.set(3);
        var birth = new BitSet(10);
        birth.set(3);
        return new RuleSet(survive, birth);
    }

    private static String ruleString(BitSet bitSet) {
        return IntStream.range(0, 10)
                .filter(bitSet::get)
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
    }

    public boolean nextState(boolean alive, int neighbours) {
        return (alive && survive.get(neighbours)) || (!alive && birth.get(neighbours));
    }

    public void updateSurvive(int count, boolean value) {
        survive.set(count, value);
    }

    public void updateBirth(int count, boolean value) {
        birth.set(count, value);
    }

    @Override
    public String toString() {
        var s = ruleString(survive);
        var b = ruleString(birth);
        return "%s/%s".formatted(s, b);
    }
}
