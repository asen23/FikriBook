package Helper;

import java.util.Arrays;
import java.util.Scanner;
import java.util.UUID;

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

    public static void printTable(String[] header, String headerSeparator, String[][] data, int[] pad, String separator) {
        int totalLength = Arrays.stream(pad).sum() + header.length * separator.length() + 1;
        headerSeparator = new String(new char[totalLength]).replace("\0", headerSeparator);
        int[] fullPad = new int[header.length];
        for (int i = 0; i < header.length; i++) {
            if (i >= pad.length) {
                fullPad[i] = 0;
            } else {
                fullPad[i] = pad[i];
            }
        }
        Helper.println(headerSeparator);
        for (int i = 0; i < header.length; i++) {
            Helper.print(separator + pad(header[i], fullPad[i]));
        }
        Helper.println(separator);
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
        return scan.nextLine();
    }

    public static String getString(Runnable callback) {
        callback.run();
        return scan.nextLine();
    }

    public static int getInt() {
        int result;
        do {
            try {
                result = scan.nextInt();
            } catch (Exception ignored) {
                scan.nextLine();
                continue;
            }
            scan.nextLine();
            break;
        } while (true);
        return result;
    }

    public static int getInt(Runnable callback) {
        int result;
        do {
            try {
                callback.run();
                result = scan.nextInt();
            } catch (Exception ignored) {
                scan.nextLine();
                continue;
            }
            scan.nextLine();
            break;
        } while (true);
        return result;
    }

    public static String generateId(String prefix) {
        return prefix + UUID.randomUUID();
    }
}
