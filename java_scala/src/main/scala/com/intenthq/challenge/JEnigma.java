package com.intenthq.challenge;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Collections;

public class JEnigma {

    // We have a system to transfer information from one place to another. This system
    // involves transferring only list of digits greater than 0 (1-9). In order to decipher
    // the message encoded in the list you need to have a dictionary that will allow
    // you to do it following a set of rules:
    //    > Sample incoming message: (​1,2,3,7,3,2,3,7,2,3,4,8,9,7,8)
    //    > Sample dictionary (​23->‘N’,234->‘ ’,89->‘H’,78->‘Q’,37 ->‘A’)
    //  - Iterating from left to right, we try to match sublists to entries of the map.
    //    A sublist is a sequence of one or more contiguous entries in the original list,
    //    eg. the sublist (1, 2) would match an entry with key 12, while the sublist (3, 2, 3)
    //    would match an entry with key 323.
    //  - Whenever a sublist matches an entry of the map, it’s replaced by the entry value.
    //    When that happens, the sublist is consumed, meaning that its elements can’t be used
    //    for another match. The elements of the mapping however, can be used as many times as needed.
    //  - If there are two possible sublist matches, starting at the same point, the longest one
    //    has priority, eg 234 would have priority over 23.
    //  - If a digit does not belong to any matching sublist, it’s output as is.
    //

    // DEV Notes: Again, would be easier with Regex, but so much slower, especially if strings are big enough.
    // This solution probably ain't very perfect either, but it's promisingly faster since it iterates through string only once.

    private final Map<Integer, Character> map;

    private JEnigma(final Map<Integer, Character> map) {
        this.map = map;
    }

    public static JEnigma decipher(final Map<Integer, Character> map) {
        return new JEnigma(map);
    }

    public String deciphe(List<Integer> message) {
        // Nothing to check for if map or message is empty.
        if (message.isEmpty() || map.isEmpty()) {
            return "";
        }

        String result = "";
		// Iteration size of the message.
        int messageSize = message.size();

		//Sorting map to retrieve the biggest chunk of string we can take
        Map<Integer, Character> sortedMap = getSortedMapByKey(map);
        int firstKeylength = (int)(Math.log10(sortedMap.entrySet().iterator().next().getKey()) + 1);
		
		// Turning array of integers into string - a bit easier to take chunks
        String messageString = message.stream().map(e -> e.toString()).reduce("", String::concat);

        for (int i = 0; i < messageSize; i++) {
            int numberOfSimbols = (i + firstKeylength > messageSize) ? messageSize - i : firstKeylength;

            for (int j = numberOfSimbols; j > 0; j--) {
				// Key to check for value
                String subString = messageString.substring(i, i + j);
                Integer messageChunk = Integer.parseInt(subString);
                Character foundValue = map.get(messageChunk);

                if (foundValue != null) {
                    result += foundValue;
					// Skip some of the encoded numbers if it's already transformed into value
                    i += j - 1;
                    break;
                }

				// If nothing found and it's already the last element - just insert it.
                if (foundValue == null && j == 1) {
                    result += subString;
                }
            }
        }

        return result;
    }

    private Map<Integer, Character> getSortedMapByKey(Map<Integer, Character> map) {
        Map<Integer, Character> sortedMap = new TreeMap<Integer, Character>(Collections.reverseOrder());
        sortedMap.putAll(map);
        return sortedMap;
    }
}