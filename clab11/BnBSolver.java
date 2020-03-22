import java.util.ArrayList;
import java.util.List;

/**
 * BnBSolver for the Bears and Beds problem. Each Bear can only be compared to Bed objects and each Bed
 * can only be compared to Bear objects. There is a one-to-one mapping between Bears and Beds, i.e.
 * each Bear has a unique size and has exactly one corresponding Bed with the same size.
 * Given a list of Bears and a list of Beds, create lists of the same Bears and Beds where the ith Bear is the same
 * size as the ith Bed.
 */
public class BnBSolver {

    class Node {
        Bear bear;
        Bed bed;
        int left;
        int right;

        Node(Bear bear, Bed bed) {
            this.bear = bear;
            this.bed = bed;
            this.left = size;
            this.right = size;
        }
    }

    Node[] nodes;
    int pivot;
    int size;

    public BnBSolver(List<Bear> bears, List<Bed> beds) {
        pivot = size = bears.size();
        nodes = new Node[size + 1];
        for (int i=0; i<size; ++i) {
            nodes[i] = new Node(null, beds.get(i));
        }

        for (Bear bear : bears) {
            pivot = solverHelper(bear, pivot, 0, size - 1);
        }
    }

    private int solverHelper(Bear bear, int pivot, int start, int end) {
        if (pivot < size) {
            Node root = nodes[pivot];
            if (bear.compareTo(root.bed) > 0) {
                root.right = solverHelper(bear, root.right, pivot + 1, end);
            } else {
                root.left = solverHelper(bear, root.left, start, pivot - 1);
            }
            return pivot;
        }

        //partition
        int correctPivot = partition(bear, start, end);
        nodes[correctPivot].bear = bear;
        return correctPivot;
    }

    private int partition(Bear bear, int start, int end) {
        while (start < end) {
            while (start < end && bear.compareTo(nodes[start].bed) > 0) {
                start += 1;
            }
            while (start < end && bear.compareTo(nodes[end].bed) < 0) {
                end -= 1;
            }
            swap(start, end);
        }
        return end;
    }

    private void swap(int i, int j) {
        Node temp = nodes[i];
        nodes[i] = nodes[j];
        nodes[j] = temp;
    }

    /**
     *
     * Returns List of Bears such that the ith Bear is the same size as the ith Bed of solvedBeds().
     */
    public List<Bear> solvedBears() {
        List<Bear> answer = new ArrayList<>();
        for (int i = 0; i < size - 1; i += 1) {
            answer.add(nodes[i].bear);
        }
        return answer;
    }

    /**
     * Returns List of Beds such that the ith Bear is the same size as the ith Bear of solvedBears().
     */
    public List<Bed> solvedBeds() {
        List<Bed> answer = new ArrayList<>();
        for (int i = 0; i < size - 1; i += 1) {
            answer.add(nodes[i].bed);
        }
        return answer;
    }
}
