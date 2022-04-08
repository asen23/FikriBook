package Helper;

import java.util.Arrays;
import java.util.Scanner;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Stream;

public class Helper {
    private static final Scanner scan = new Scanner(System.in);
    private static boolean isDummyEnabled = false;

    public static void toggleDummy() {
        isDummyEnabled = true;
    }

    public static boolean isDummyEnabled() {
        return isDummyEnabled;
    }

    public static void println(String s) {
        System.out.println(s);
    }

    public static void println(String[] s) {
        for (String string : s) {
            System.out.println(string);
        }
    }

    public static void printTable(
          String[] header,
          String headerSeparator,
          String[][] data,
          int[] pad,
          String separator
    ) {
        int totalLength = Arrays.stream(pad).sum() + header.length * separator.length() + 1;
        headerSeparator = repeatString(headerSeparator, totalLength);
        int[] fullPad = new int[header.length];
        for (int i = 0; i < header.length; i++) {
            if (i >= pad.length) {
                fullPad[i] = 0;
            } else {
                fullPad[i] = pad[i];
            }
        }
        Helper.println(headerSeparator);
        println(header, fullPad, separator);
        Helper.println(headerSeparator);
        for (String[] item : data) {
            println(item, fullPad, separator);
        }
        Helper.println(headerSeparator);
    }

    public static void println(String[] s, int[] pad, String separator) {
        int[] fullPad = new int[s.length];
        for (int i = 0; i < s.length; i++) {
            if (i >= pad.length) {
                fullPad[i] = 0;
            } else {
                fullPad[i] = pad[i];
            }
        }
        for (int i = 0; i < s.length; i++) {
            Helper.print(separator + pad(s[i], fullPad[i]));
        }
        Helper.println(separator);
    }

    public static void println(String[] s, String separator) {
        println(s, new int[0], separator);
    }

    public static void println(String s, int pad) {
        System.out.println(pad(s, pad));
    }

    public static void println() {
        System.out.println();
    }

    public static void prompt() {
        println();
        getString(() -> print("Press enter to continue..."));
    }

    public static void prompt(String s) {
        println(s);
        prompt();
    }

    public static String pad(String s, int pad) {
        return String.format("%" + -pad + "s", s);
    }

    public static void print(String s) {
        System.out.print(s);
    }

    public static String getString() {
        return getString(() -> {
        });
    }

    public static String getString(Runnable callback) {
        return getString(callback, s -> false, s -> false);
    }

    public static String getString(Runnable callback, Function<String, Boolean> validation) {
        return getString(callback, validation, s -> false);
    }

    public static String getString(
          Runnable callback,
          Function<String, Boolean> validation,
          Function<String, Boolean> cancelCondition
    ) {
        String result;
        do {
            callback.run();
            result = scan.nextLine();
            if (cancelCondition.apply(result)) return result;
        } while (validation.apply(result));
        return result;
    }

    public static int getInt() {
        return getInt(() -> {
        });
    }

    public static int getInt(Runnable callback) {
        return getInt(callback, 0, i -> false, i -> false);
    }

    public static int getInt(
          Runnable callback,
          int defaultValue,
          Function<Integer, Boolean> validation
    ) {
        return getInt(callback, defaultValue, validation, i -> false);
    }

    public static int getInt(
          Runnable callback,
          int defaultValue,
          Function<Integer, Boolean> validation,
          Function<Integer, Boolean> cancelCondition
    ) {
        int result = defaultValue;
        do {
            callback.run();
            if (scan.hasNextInt()) {
                result = scan.nextInt();
                scan.nextLine();
            } else {
                scan.nextLine();
            }
            if (cancelCondition.apply(result)) return result;
        } while (validation.apply(result));
        return result;
    }

    public static String generateId(String prefix) {
        return prefix + "-" + UUID.randomUUID();
    }

    public static String[] concatArray(String[] a, String[] b) {
        return Stream.concat(Arrays.stream(a), Arrays.stream(b)).toArray(String[]::new);
    }

    public static String repeatString(String s, int count) {
        return new String(new char[count]).replace("\0", s);
    }

    public static void cls() {
        Helper.print(repeatString("\r\n", 100));
    }

    public static void printHeader(String header) {
        println(header);
        println(repeatString("=", 50));
    }
}
