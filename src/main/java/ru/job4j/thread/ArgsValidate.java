package ru.job4j.thread;

import java.net.URL;

public class ArgsValidate {
    public static void validate(String[] args) {
        int number;
        if (args.length < 2) {
            throw new IllegalArgumentException("Arguments not passed to program");
        }
        try {
            new URL(args[0]);
        } catch (Exception e) {
            throw new IllegalArgumentException(args[0] + " is not a valid URL");
        }
        try {
            number = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Argument " + args[1] + " is not a valid number");
        }
        if (number <= 0) {
            throw new IllegalArgumentException("Argument " + args[1] + " speed must be greater than 0");
        }
    }
}
