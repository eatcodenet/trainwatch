package net.eatcode.trainwatch.movement;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Strings {

    public static String titleCase(String words) {
        if (words == null || words.length() < 1)
            return words;
        return Arrays.asList(words.split("\\s+")).stream().map(Strings::capitalise).collect(Collectors.joining(" "));
    }

    public static String capitalise(String word) {
        return new StringBuffer(word.length())
                .append(Character.toTitleCase(word.charAt(0)))
                .append(word.substring(1).toLowerCase())
                .toString();
    }
}
