public class RabinKarpAlgorithm {


    /**
     * This algorithm returns the starting index of the matching substring.
     * This method will return -1 if no matching substring is found, or if the input is invalid.
     */
    public static int rabinKarp(String input, String pattern) {
        int patternLength = pattern.length();
        if (patternLength > input.length())
            return -1;
        RollingString inputStr = new RollingString(input.substring(0, patternLength), patternLength);
        RollingString patternStr = new RollingString(pattern, patternLength);
        int targetHash = patternStr.hashCode();
//        System.out.println("target: " + targetHash);

        if (targetHash == inputStr.hashCode())
            return 0;

        for (int i = patternLength; i < input.length(); ++i) {
            inputStr.addChar(input.charAt(i));
//            System.out.println(inputStr.hashCode());
            if (targetHash == inputStr.hashCode()) {
                return i - patternLength + 1;
            }
        }
        return -1;
    }

}
