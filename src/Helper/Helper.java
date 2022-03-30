package Helper;

import java.util.Scanner;

public class Helper {
    private static Scanner scan = new Scanner(System.in);

    public static void println(String s) {
        System.out.println(s);
    }

    public static void println(String s, int pad) {
        System.out.println(String.format("%-" + pad + "s", s));
    }

    public static void println() {
        System.out.println();
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
}
