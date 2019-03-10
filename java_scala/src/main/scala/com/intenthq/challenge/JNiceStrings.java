package com.intenthq.challenge;

import java.util.List;
import java.util.HashSet;
import java.util.Arrays;
import java.util.Set;

public class JNiceStrings {
    // From http://adventofcode.com/day/5
    //  --- Day 5: Doesn't He Have Intern-Elves For This? ---
    //
    //  Santa needs help figuring out which strings in his text file are naughty or nice.
    //
    //    A nice string is one with all of the following properties:
    //
    //    It contains at least three vowels (aeiou only), like aei, xazegov, or aeiouaeiouaeiou.
    //  It contains at least one letter that appears twice in a row, like xx, abcdde (dd), or aabbccdd (aa, bb, cc, or dd).
    //    It does not contain the strings ab, cd, pq, or xy, even if they are part of one of the other requirements.
    //    For example:
    //
    //    ugknbfddgicrmopn is nice because it has at least three vowels (u...i...o...), a double letter (...dd...), and none of the disallowed substrings.
    //  aaa is nice because it has at least three vowels and a double letter, even though the letters used by different rules overlap.
    //    jchzalrnumimnmhp is naughty because it has no double letter.
    //    haegwjzuvuyypxyu is naughty because it contains the string xy.
    //    dvszwmarrgswjxmb is naughty because it contains only one vowel.
    //    How many strings are nice?


    // DEV Notes: Regexp would be so much easier here, but it's considerably slower.
    // Of course it wouldn't matter here with so little data given, but soon after that the difference clearly starts to show
    // Just wanna try Java functions and see how it plays out.

    private static final Set<String> NAUGHTY_VALUES = new HashSet<String>(
            Arrays.asList(
                    new String[]{"ab", "cd", "pq", "xy"}
            ));

    private static final Set<Character> VOWELS= new HashSet<Character>(
            Arrays.asList(
                    new Character[]{'a', 'e', 'i', 'o', 'u'}
            ));

    public static int nice(List<String> xs) {
		if (xs.isEmpty()) {
			return 0;
		}

        int noOfVowels = 0;
        boolean hasDoubleLetter = false;
        Character lastLetter = null;

        for (String stringValue : xs) {
            for (Character letter: stringValue.toCharArray()) {
                if(lastLetter != null) {
                    if (hasNaughtyString(new String(new char[]{lastLetter, letter}))) {
                        return 0;
                    }

                    if (hasDoubleLetter(letter, lastLetter)){
                        hasDoubleLetter = true;
                    }
                }

                if (containsVowel(letter)) {
                    noOfVowels++;
                }

                lastLetter = letter;
            }
        }

        if (noOfVowels >= 3 && hasDoubleLetter) {
            return 1;
        }

        return 0;
    }

    private static boolean hasNaughtyString(String checkedValue) {
        return NAUGHTY_VALUES.contains(checkedValue);
    }

    private static boolean hasDoubleLetter(Character firstLetter, Character secondLetter) {
        return firstLetter == secondLetter;
    }

    private static boolean containsVowel(Character letter) {
        return VOWELS.contains(letter);
    }
}