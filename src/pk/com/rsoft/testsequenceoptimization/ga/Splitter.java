package pk.com.rsoft.testsequenceoptimization.ga;

public class Splitter {

    // the given string will be split into substrings no longer than maxLen
    private int maxLen;

    // the default constructor uses this value to initialize maxLen
    private static int defaultMaxLen = 32*1024;

    // default constructor: make new Splitter with default maxLen value
    public Splitter() {
        maxLen = defaultMaxLen;
    }

    // custom constructor: make new Splitter with specified maxLen value
    public Splitter(int maxLen) {
        this.maxLen = maxLen;
    }

    // accessor method: get the current maxLen value
    public int getMaxLen() {
        return maxLen;
    }

    // mutator method: set maxLen to specified value
    public void setMaxLen(int maxLen) {
        this.maxLen = maxLen;
    }


    // the real work gets done here:
    //   -- split the given string into substrings no longer than maxLen
    //   -- return as an array of strings
    public String[] split(String str) {

        // get the length of the original string
        int origLen = str.length();

        // calculate the number of substrings we'll need to make
        int splitNum = origLen/maxLen;
        if (origLen % maxLen > 0)
            splitNum += 1;

        // initialize the result array
        String[] splits = new String[splitNum];

        // for each substring...
        for (int i = 0; i < splitNum; i++) {

            // the substring starts here
            int startPos = i * maxLen;

            // the substring ends here
            int endPos = startPos + maxLen;

            // make sure we don't cause an IndexOutOfBoundsException
            if (endPos > origLen)
                endPos = origLen;

            // make the substring
            String substr = str.substring(startPos, endPos);

            // stick it in the result array
            splits[i] = substr;
        }

        // return the result array
        return splits;
    }


}