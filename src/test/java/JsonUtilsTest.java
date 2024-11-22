import domain.WiseSaying;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import util.JsonUtils;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class JsonUtilsTest {
    @Test
    void serialize() {
        WiseSaying wiseSaying = new WiseSaying(1, "content", "author");
        String json = JsonUtils.serialize(wiseSaying);
        assertThat(json).isEqualTo(
                "{\n" +
                "  \"id\": 1,\n" +
                "  \"content\": \"content\",\n" +
                "  \"author\": \"author\"\n" +
                "}");
    }

    @Test
    void serializeList() {
        WiseSaying wiseSaying1 = new WiseSaying(1, "content1", "author1");
        WiseSaying wiseSaying2 = new WiseSaying(2, "content2", "author2");
        String json1 = JsonUtils.serialize(wiseSaying1);
        String json2 = JsonUtils.serialize(wiseSaying2);

        List<String> jsonList = new ArrayList<>();
        jsonList.add(json1);
        jsonList.add(json2);

        Assertions.assertThat(JsonUtils.toJsonList(jsonList)).isEqualTo(
                "[\n" +
                "  {\n" +
                "    \"id\": 1,\n" +
                "    \"content\": \"content1\",\n" +
                "    \"author\": \"author1\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": 2,\n" +
                "    \"content\": \"content2\",\n" +
                "    \"author\": \"author2\"\n" +
                "  }\n" +
                "]");
    }

    @Test
    void deserialize() {
        WiseSaying wiseSaying = new WiseSaying(1, "명언", "작가");
        String json = JsonUtils.serialize(wiseSaying);

        WiseSaying deserialized = JsonUtils.deserialize(json);

        Assertions.assertThat(deserialized).isEqualTo(wiseSaying);
    }
}