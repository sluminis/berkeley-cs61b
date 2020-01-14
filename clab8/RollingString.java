import edu.princeton.cs.algs4.In;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * A String-like class that allows users to add and remove characters in the String
 * in constant time and have a constant-time hash function. Used for the Rabin-Karp
 * string-matching algorithm.
 */
class RollingString{

    /**
     * Number of total possible int values a character can take on.
     * DO NOT CHANGE THIS.
     */
    static final int UNIQUECHARS = 128;

    /**
     * The prime base that we are using as our mod space. Happens to be 61B. :)
     * DO NOT CHANGE THIS.
     */
    static final int PRIMEBASE = 6113;

    private List<Character> charList = new LinkedList<>();

    private int maxLength;

    private int hash;

    /**
     * Initializes a RollingString with a current value of String s.
     * s must be the same length as the maximum length.
     */
    public RollingString(String s, int length) {
        assert(s.length() == length);
        maxLength = length;
        for (int i = 0; i < s.length(); ++i) {
            char c = s.charAt(i);
            charList.add(c);
            hash = Math.floorMod(hash*UNIQUECHARS + (int) c, PRIMEBASE);
        }
    }

    /**
     * Adds a character to the back of the stored "string" and 
     * removes the first character of the "string". 
     * Should be a constant-time operation.
     */
    public void addChar(char c) {
        if (charList.size() == maxLength) {
            char removed = charList.remove(0);
            hash -= (int) removed * safeModPower(UNIQUECHARS, maxLength - 1);
            hash = Math.floorMod(hash, PRIMEBASE);
        }
        charList.add(c);
        hash = Math.floorMod(hash * UNIQUECHARS + (int) c, PRIMEBASE);
    }


    /**
     * Returns the "string" stored in this RollingString, i.e. materializes
     * the String. Should take linear time in the number of characters in
     * the string.
     */
    public String toString() {
        StringBuilder strb = new StringBuilder();
        for (char c : charList) {
            strb.append(c);
        }
        return strb.toString();
    }

    /**
     * Returns the fixed length of the stored "string".
     * Should be a constant-time operation.
     */
    public int length() {
        return charList.size();
    }


    /**
     * Checks if two RollingStrings are equal.
     * Two RollingStrings are equal if they have the same characters in the same
     * order, i.e. their materialized strings are the same.
     */
    @Override
    public boolean equals(Object o) {
        if (null == o) return false;
        if (!getClass().equals(o.getClass())) return false;
        return charList.equals(((RollingString) o).charList);
    }

    /**
     * Returns the hashcode of the stored "string".
     * Should take constant time.
     */
    @Override
    public int hashCode() {
        return hash;
    }

    static int safeModPower(int a, int b) {
        int base = a;
        int ans = 1;
        while (b > 0) {
            if ((b & 1) == 1)
                ans = (ans * base) % PRIMEBASE;
            base = (base * base) % PRIMEBASE;
            b = b >>> 1;
        }
        return ans;
    }
}
