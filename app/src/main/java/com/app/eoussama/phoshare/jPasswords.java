package com.app.eoussama.phoshare;

import java.util.Random;

public abstract class jPasswords {

    public static final String LOWER_CASE = "abcdefghijklmnopqrstuvwxyz";
    public static final String UPPER_CASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String SYMBOLS = "!@#$%^&* ";
    public static final String NUMBERS = "0123456789";

    public static String Generate(int chars, String[] entries) {
        String __generatedString = "";
        StringBuilder __entries = new StringBuilder();
        Random rand = new Random();

        if (entries == null || entries.length == 0) __entries.append(LOWER_CASE+UPPER_CASE+SYMBOLS+NUMBERS);
        else __entries.append(String.join("", entries));
        while(chars-- > 0) __generatedString += Character.toString(__entries.charAt(rand.nextInt(__entries.length())));

        return __generatedString;
    }

    public static String Hash(String password, String salt, int chars) {
        StringBuilder __hashedString = new StringBuilder();
        int count = 0;

        __hashedString.append(password + ((salt == null) ? "" : salt));
        chars = chars == -1 ? __hashedString.length() : chars;

        while(count++ < __hashedString.length() - 1) {
            char __character = (char)((int)__hashedString.charAt(count) + __hashedString.length());
            Random rand = new Random();

            __character = __character > 126 || __character < 32 ? (char)jPasswords.OverWrap((int)__character, 126, 32) : __character;
            __character = __character == '\'' || __character == '"' ? '_' : __character;
            __hashedString.setCharAt(count, __character);
        }

        return (chars > -1) ? __hashedString.reverse().toString().substring(0, chars) : __hashedString.reverse().toString();
    }

    public static boolean Authenticate(String password, String salt, String target) {
        return jPasswords.Hash(password, salt, -1).equals(target);
    }

    private static int OverWrap(int num, int max, int min) {
        int output = min;

        while(num-- > 0) output = output > max ? min : output + 1;

        return output;
    }
}
