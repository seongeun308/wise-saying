package domain;

import config.AppConfig;

public class FilePath {
    public static String DIR = "src/main/resources/db/wiseSaying/";
    public static String WISE_SAYING = DIR + "%d.json";
    public static String BUILD = DIR + "data.json";
    public static String LAST_ID = DIR + "lastId.txt";

    public static void setTestFilePath() {
        if (AppConfig.isTesting()) {
            DIR = "src/test/resources/db/wiseSaying/";
            WISE_SAYING = DIR + "%d.json";
            BUILD = DIR + "data.json";
            LAST_ID = DIR + "lastId.txt";
        }
    }

    public static void setOperationFilePath() {
        if (!AppConfig.isTesting()) {
            DIR = "src/main/resources/db/wiseSaying/";
            WISE_SAYING = DIR + "%d.json";
            BUILD = DIR + "data.json";
            LAST_ID = DIR + "lastId.txt";
        }
    }
}
