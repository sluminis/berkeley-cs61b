package creatures;

import huglife.*;

import java.awt.*;
import java.security.DigestException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;

public class Clorus extends Creature {

    /**
     * red color.
     */
    private int r;
    /**
     * green color.
     */
    private int g;
    /**
     * blue color.
     */
    private int b;

    private double moveLose = 0.05;

    private double stayLose = 0.02;

    private double replicateEnergy = 3.;

    private int child;

    /**
     * Creates a creature with the name N. The intention is that this
     * name should be shared between all creatures of the same type.
     *
     */
    public Clorus(double e) {
        super("clorus");
        r = 34;
        g = 0;
        b = 200;
        energy = e;
        child = 2;
    }

    public Clorus() {
        this(1);
    }

    @Override
    public Color color() {
        b = 200 + (int)energy;
        b = Math.min(255, b);
        return color(r, g, b);
    }

    @Override
    public void move() {
        double energyAfterMove = energy - moveLose;
        energy = energyAfterMove;
    }

    @Override
    public void attack(Creature c) {
        double creatureEnergy = c.energy() / 3.;
        energy += creatureEnergy;
    }

    @Override
    public Creature replicate() {
        energy = energy / 2.;
        child -= 1;
        return new Clorus(energy);
    }

    @Override
    public void stay() {
        energy -= stayLose;
    }

    @Override
    public Action chooseAction(Map<Direction, Occupant> neighbors) {
        // Rule 1
        Deque<Direction> emptyNeighbors = new ArrayDeque<Direction>();
        Deque<Direction> plipNeighbors = new ArrayDeque<Direction>();
        // TODO
        // (Google: Enhanced for-loop over keys of NEIGHBORS?)
        // for () {...}
        for (Map.Entry<Direction, Occupant> entry : neighbors.entrySet()) {
            String occupantName = entry.getValue().name();
            if (occupantName.equals("empty")) {
                emptyNeighbors.add(entry.getKey());
            }
            else if (occupantName.equals("plip")) {
                plipNeighbors.add(entry.getKey());
            }
        }

        // Rule 2
        // HINT: randomEntry(emptyNeighbors)
        if (child > 0 && !emptyNeighbors.isEmpty() && energy >= replicateEnergy) {
            Direction direction = HugLifeUtils.randomEntry(emptyNeighbors);
            return new Action(Action.ActionType.REPLICATE, direction);
        }

        // Rule 3
        if (!plipNeighbors.isEmpty()) {
            Direction direction = HugLifeUtils.randomEntry(plipNeighbors);
            return new Action(Action.ActionType.ATTACK, direction);
        }

        if (emptyNeighbors.isEmpty()) {
            return new Action(Action.ActionType.STAY);
        }

        // Rule 4
        Direction direction = HugLifeUtils.randomEntry(emptyNeighbors);
        return new Action(Action.ActionType.MOVE, direction);
    }

}
