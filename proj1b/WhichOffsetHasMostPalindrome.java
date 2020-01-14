import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class WhichOffsetHasMostPalindrome {
    public static void main(String[] args) {
        int minLength = 4;
        In in = new In("words.txt");
        Palindrome palindrome = new Palindrome();

        List<String> stringArrayList = new ArrayList<String>();

        while (!in.isEmpty()) {
            String word = in.readString();
            if (word.length() >= minLength) {
                stringArrayList.add(word);
            }
        }

        for (int i =0; i < 26; ++i) {
            CharacterComparator cc = new  OffByN(i);
            int count = 0;
            for (String word : stringArrayList) {
                boolean result = palindrome.isPalindrome(word, cc);
                if (result) ++count;
            }
            System.out.println(String.format("offset %d has %d palindrome(s)", i, count));
        }
    }
}
