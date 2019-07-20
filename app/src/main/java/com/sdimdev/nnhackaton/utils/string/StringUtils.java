package com.sdimdev.nnhackaton.utils.string;

import java.util.Arrays;

public class StringUtils {
    public static String repeat(int n, Character character) {
        if (n < 0)
            n = 0;
        char[] charArray = new char[n];
        Arrays.fill(charArray, character);
        return new String(charArray);
    }
}
