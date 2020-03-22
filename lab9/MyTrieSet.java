import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyTrieSet<V> implements TrieSet61B {

    private final static int CHAR_COUNT = 256;

    Node root = new Node('\0', false);

    static class Node {
        Object val;
        char ch;
        boolean isKey;
        Map<Character, Node> map = new HashMap<>();
//        @SuppressWarnings("unchecked")
//        Node[] next = (Node[]) Array.newInstance(Node.class, CHAR_COUNT);

        Node(char ch, boolean isKey) {
            this.ch = ch;
            this.isKey = isKey;
        }
    }

    @Override
    public void clear() {
        root = new Node('\0', false);
    }

    @Override
    public boolean contains(String key) {
        return containsHelper(key) != null;
    }

    private Node containsHelper(String key) {
        if (key == null || key.length() < 1) {
            return null;
        }
        Node curr = root;
        for (int i = 0, n = key.length(); i < n; ++i) {
            char c = key.charAt(i);
            if(!curr.map.containsKey(c)) {
                return null;
            }
            curr = curr.map.get(c);
        }
        return curr;
    }

    @Override
    public void add(String key) {
        if (key == null || key.length() < 1) {
            return;
        }
        Node curr = root;
        for (int i = 0, n = key.length(); i < n; ++i) {
            char c = key.charAt(i);
            if (!curr.map.containsKey(c)) {
                curr.map.put(c, new Node(c, false));
            }
            curr = curr.map.get(c);
        }
        curr.isKey = true;
    }

    @Override
    public List<String> keysWithPrefix(String prefix) {
        List<String> answerList = new ArrayList<>();
        Node prefixNode = containsHelper(prefix);
        keysWithPrefixHelper(prefixNode, prefix, answerList);
        return answerList;
    }

    private void keysWithPrefixHelper(Node node, String answer, List<String> answerList) {
        if (node.isKey) {
            answerList.add(answer);
        }
        for (Map.Entry<Character, Node> entry : node.map.entrySet()) {
            keysWithPrefixHelper(entry.getValue(), answer + entry.getKey(), answerList);
        }
    }

    @Override
    public String longestPrefixOf(String key) {
        throw new UnsupportedOperationException("don't support longestPrefixOf(String key)");
    }
}
