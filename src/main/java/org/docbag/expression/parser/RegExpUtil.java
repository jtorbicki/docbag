package org.docbag.expression.parser;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * RegExpUtil
 *
 * @author Jakub Torbicki
 */
public class RegExpUtil {
    private RegExpUtil() {
    }

    /**
     * Split input String using pattern as a delimiter, but opposed to the String.split()
     * also return the matching delimiter among tokens.
     */
    public static String[] inclusiveSplit(String input, Pattern pattern, int limit) {
        int index = 0;
        boolean matchLimited = limit > 0;
        ArrayList<String> matchList = new ArrayList<String>();
        Matcher m = pattern.matcher(input);
        // Add segments before each match found
        while (m.find()) {
            int end = m.end();
            if (!matchLimited || matchList.size() < limit - 1) {
                int start = m.start();
                String match = input.subSequence(index, start).toString();
                matchList.add(match);
                // add match to the list
                matchList.add(input.subSequence(start, end).toString());
                index = end;
            } else if (matchList.size() == limit - 1) { // last one
                String match = input.subSequence(index, input.length()).toString();
                matchList.add(match);
                index = end;
            }
        }
        // If no match was found, return this
        if (index == 0) {
            return new String[]{input};
        }
        // Add remaining segment
        if (!matchLimited || matchList.size() < limit) {
            matchList.add(input.subSequence(index, input.length()).toString());
        }
        // Construct result
        int resultSize = matchList.size();
        if (limit == 0) {
            while (resultSize > 0 && matchList.get(resultSize - 1).equals("")) {
                resultSize--;
            }
        }
        String[] result = new String[resultSize];
        return matchList.subList(0, resultSize).toArray(result);
    }
}
