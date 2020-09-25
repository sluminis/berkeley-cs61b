package bearmaps.hw4;

import java.util.*;

import bearmaps.proj2ab.ArrayHeapMinPQ;
import bearmaps.proj2ab.ExtrinsicMinPQ;
import edu.princeton.cs.algs4.Stopwatch;

public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex> {

    private SolverOutcome outcome;
    private int numStatesExplored;
    private double timeSpent;

    Map<Vertex, Double> dist;
    Map<Vertex, WeightedEdge<Vertex>> predecessor;
    Vertex goal;

    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout) {
        // initialization
        Stopwatch stopwatch = new Stopwatch();
        dist = new HashMap<>();
        predecessor = new HashMap<>();
        ExtrinsicMinPQ<Vertex> priorityQueue = new ArrayHeapMinPQ<Vertex>();
        outcome = SolverOutcome.UNSOLVABLE;
        numStatesExplored = 0;
        goal = end;

        // algorithm start
        dist.put(start, 0.);
        priorityQueue.add(start, 0.);

        Vertex vertex;
        while((vertex = priorityQueue.removeSmallest()) != null) {
            if (vertex.equals(end)) {
                outcome = SolverOutcome.SOLVED;
                timeSpent = stopwatch.elapsedTime();
                return;
            }

            if ((timeSpent = stopwatch.elapsedTime()) > timeout) {
                outcome = SolverOutcome.TIMEOUT;
                return;
            }

            numStatesExplored += 1;

            for (WeightedEdge<Vertex> edge : input.neighbors(vertex)) {
                Vertex u = edge.from();
                double distToU = dist.get(u);
                Vertex v = edge.to();
                double distToV = dist.getOrDefault(v, Double.POSITIVE_INFINITY);
                double w = edge.weight();

                // System.out.println(String.format("%s -> %s", u.toString(), v.toString()));

                if (distToU + w < distToV) {
                    dist.put(v, distToU + w);
                    predecessor.put(v, edge);
                    if (priorityQueue.contains(v)) {
                        priorityQueue.changePriority(v, distToU + w + input.estimatedDistanceToGoal(v, end));
                    } else {
                        priorityQueue.add(v, distToU + w + input.estimatedDistanceToGoal(v, end));
                    }
                }
            }
        }
    }

    @Override
    public SolverOutcome outcome() {
        return outcome;
    }

    @Override
    public List<Vertex> solution() {
        LinkedList<Vertex> answer = new LinkedList<>();
        answer.addFirst(goal);
        WeightedEdge<Vertex> edge;
        Vertex vertex = goal;
        while ((edge = predecessor.get(vertex)) != null) {
            answer.addFirst(edge.from());
            vertex = edge.from();
        }
        return answer;
    }

    @Override
    public double solutionWeight() {
        return dist.get(goal);
    }

    @Override
    public int numStatesExplored() {
        return numStatesExplored;
    }

    @Override
    public double explorationTime() {
        return timeSpent;
    }
}