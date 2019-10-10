package com.xiao.java.utils;

import com.sun.javafx.binding.StringFormatter;

public class Log {

    public static void print(String tag, String message) {

        String logFormat = "%s -- %s";

        System.out.println(String.format(logFormat, tag, message));

    }
}
