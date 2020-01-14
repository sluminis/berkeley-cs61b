public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        Deque<Character> characterDeque = new LinkedListDeque<Character>();
//        Deque<Character> characterDeque = new ArrayDeque<Character>();
        for (Character c : word.toCharArray()) {
            characterDeque.addLast(c);
        }
        return characterDeque;
    }

    public boolean isPalindrome(String word) {
        char[] wordArray = word.toCharArray();
        int left = 0;
        int right = wordArray.length - 1;
        for (; left <= right; ++left, --right) {
            if (wordArray[left] != wordArray[right]) {
                return false;
            }
        }
        return true;
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        char[] wordArray = word.toCharArray();
        int left = 0;
        int right = wordArray.length - 1;
        for (; left <= right; ++left, --right) {
            if (!cc.equalChars(wordArray[left], wordArray[right])) {
                return false;
            }
        }
        return true;
    }
}
