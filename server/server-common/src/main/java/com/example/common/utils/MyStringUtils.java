package com.example.common.utils;

import java.util.ArrayList;

public class MyStringUtils {
    public MyStringUtils() {
    }

    public static String firstLowerCase(String name) {
        name = name.substring(0, 1).toLowerCase() + name.substring(1);
        return name;
    }

    public static Boolean isEmpty(Object str) {
        if (str instanceof String) {
            if (str.toString().length() < 1) {
                return true;
            }
        } else if (str == null) {
            return true;
        }

        return false;
    }

    public static void append(StringBuffer stringBuffer, String text, String... param) {
        stringBuffer.append(String.format(text + "\n", param));
    }

    public static void append(StringBuffer stringBuffer, String text, int tab, String... param) {
        for(int i = 0; i < tab; ++i) {
            stringBuffer.append("\t");
        }

        append(stringBuffer, text, param);
    }

    public static String firstUpperCase(String name) {
        name = name.substring(0, 1).toUpperCase() + name.substring(1);
        return name;
    }

    public static String upperCase_(String name, boolean firstCase) {
        if (isEmpty(name)) {
            return "";
        } else {
            String[] s = name.split("_");
            StringBuffer stringBuffer = new StringBuffer();
            String[] var4 = s;
            int var5 = s.length;

            for(int var6 = 0; var6 < var5; ++var6) {
                String s1 = var4[var6];
                stringBuffer.append(s1.substring(0, 1).toUpperCase() + s1.substring(1));
            }

            return !firstCase ? firstLowerCase(stringBuffer.toString()) : stringBuffer.toString();
        }
    }

    public static ArrayList<String> splitByUpperCase(String str, Boolean isLowerCase) {
        ArrayList<String> rs = new ArrayList();
        int index = 0;
        int len = str.length();

        for(int i = 1; i < len; ++i) {
            if (Character.isUpperCase(str.charAt(i))) {
                if (isLowerCase) {
                    rs.add(firstLowerCase(str.substring(index, i)));
                } else {
                    rs.add(str.substring(index, i));
                }

                index = i;
            }
        }

        if (isLowerCase) {
            rs.add(firstLowerCase(str.substring(index, len)));
        } else {
            rs.add(str.substring(index, len));
        }

        return rs;
    }
}
