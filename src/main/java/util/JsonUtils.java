package util;

import domain.WiseSaying;

import java.util.List;

public class JsonUtils {
    private static final String ID = "\"id\": ";
    private static final String CONTENT = "\"content\": \"";
    private static final String AUTHOR = "\"author\": \"";

    public static String serialize(WiseSaying wiseSaying) {
        return "{\n" +
                "  \"id\": "+ wiseSaying.getId() +",\n" +
                "  \"content\": \"" + wiseSaying.getContent() +"\",\n" +
                "  \"author\": \"" + wiseSaying.getAuthor() + "\"\n" +
                "}";
    }

    public static String toJsonList(List<String> jsonList) {
        return "[\n  " +
                String.join(",\n  ",
                        jsonList.stream()
                                .map(json -> json.replace("\n", "\n  "))
                                .toList()
                )
                +"\n]";
    }

    public static WiseSaying deserialize(String json) {
        int id = extractIntValue(json, ID);
        String content = extractStringValue(json, CONTENT);
        String author = extractStringValue(json, AUTHOR);
        return new WiseSaying(id, content, author);
    }

    private static int extractIntValue(String json, String key) {
        int startIndex = json.indexOf(key) + key.length();
        int endIndex = json.indexOf(",", startIndex);
        if (endIndex == -1)
            endIndex = json.indexOf("}", startIndex);
        String value = json.substring(startIndex, endIndex).trim();
        return Integer.parseInt(value);
    }

    private static String extractStringValue(String json, String key) {
        int startIndex = json.indexOf(key) + key.length();
        int endIndex = json.indexOf("\"", startIndex);
        return json.substring(startIndex, endIndex);
    }
}
