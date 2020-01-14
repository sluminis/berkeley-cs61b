import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 * Solver for the Flight problem (#9) from CS 61B Spring 2018 Midterm 2.
 * Assumes valid input, i.e. all Flight start times are >= end times.
 * If a flight starts at the same time as a flight's end time, they are
 * considered to be in the air at the same time.
 */
public class FlightSolver {

    private static class Node {
        int time;
        int change;
        boolean isStart;

        Node(int time, int change, boolean isStart) {
            this.time = time;
            this.change = change;
            this.isStart = isStart;
        }

        public int getTime() {
            return time;
        }

        public int getChange() {
            return change;
        }

        public boolean isStart() {
            return isStart;
        }
    }

    PriorityQueue<Node> priorityQueue;

    public FlightSolver(ArrayList<Flight> flights) {
        priorityQueue = new PriorityQueue<>((x, y) -> {
            int result = Integer.compare(x.getTime(), y.getTime());
            if (result == 0)
                return Boolean.compare(x.isStart(), y.isStart());
            return result;
        });
        for (Flight flight : flights) {
            Node start = new Node(flight.startTime(), flight.passengers(), true);
            Node end   = new Node(flight.endTime() + 1, -flight.passengers(), false);
            priorityQueue.add(start);
            priorityQueue.add(end);
        }
    }

    public int solve() {
        int max = 0;
        int count = 0;
        Node node;
        while ((node = priorityQueue.poll()) != null) {
            count += node.getChange();
            max = Math.max(max, count);
        }
        return max;
    }

}
