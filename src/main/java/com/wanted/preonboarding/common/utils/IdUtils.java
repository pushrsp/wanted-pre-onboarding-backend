package com.wanted.preonboarding.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IdUtils {
    public static boolean isOnlyNumber(String str) {
        Pattern pattern = Pattern.compile("^[0-9]+$");
        Matcher matcher = pattern.matcher(str);

        return matcher.matches();
    }
}
