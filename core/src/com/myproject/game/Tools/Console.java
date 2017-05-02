package com.myproject.game.Tools;

/**
 * Created by leon on 5/2/17.
 */
import java.util.Scanner;

public final class Console {
    private static final Scanner scanner = new Scanner(System.in);

    /**
     * @param val : text to display on console
     * @param args : optional arguments to send for string formatting
     */
    public static void print(String val, Object... args) {
        System.out.format(val, args);
    }

    /**
     * @param val : text to display on console
     * @param args : optional arguments to send for string formatting
     */
    public static void println(String val, Object... args) {
        print(val + "\n", args);
    }

    /**
     * @param prompt : text to display to user
     * @param args : optional arguments to send for string formatting
     * @return user's input as String
     */
    public static String getStringInput(String prompt, Object... args) {
        println(prompt, args);
        return scanner.nextLine();
    }

    /**
     * @param prompt : text to display to user
     * @param args : optional arguments to send for string formatting
     * @return user's input as integer
     */
    public static Integer getIntegerInput(String prompt, Object... args) {
        return getLongInput(prompt, args).intValue();
    }

    /**
     * @param prompt : text to display to user
     * @param args : optional arguments to send for string formatting
     * @return user's input as double
     */
    public static Double getDoubleInput(String prompt, Object... args) {
        return getFloatInput(prompt, args).doubleValue();
    }

    /**
     * @param prompt : text to display to user
     * @param args : optional arguments to send for string formatting
     * @return user's input as float
     */
    public static Float getFloatInput(String prompt, Object... args) {
        final String stringInput = getStringInput(prompt, args);
        try {
            return Float.parseFloat(stringInput);
        } catch (NumberFormatException nfe) {
            println("[ %s ] is an invalid input!", stringInput);
            println("Try inputting a numeric value!");
            return getFloatInput(prompt, args);
        }
    }

    /**
     * @param prompt : text to display to user
     * @param args : optional arguments to send for string formatting
     * @return user's input as long
     */
    public static Long getLongInput(String prompt, Object... args) {
        final String stringInput = getStringInput(prompt, args);
        try {
            return Long.parseLong(stringInput);
        } catch (NumberFormatException nfe) {
            println("[ %s ] is an invalid input!", stringInput);
            println("Try inputting an integer value!");
            return getLongInput(prompt, args);
        }
    }
}