package config;

public class AppConfig {
    private static boolean isTesting = false;

    public static void setTest() {
        isTesting = true;
    }

    public static void cancelTest() {
        isTesting = false;
    }

    public static boolean isTesting() {
        return isTesting;
    }
}
