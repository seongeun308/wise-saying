import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public record WiseSaying(int id, String content, String author) {
    @JsonCreator
    public WiseSaying(@JsonProperty("id") int id,
                      @JsonProperty("content") String content,
                      @JsonProperty("author") String author) {
        this.id = id;
        this.content = content;
        this.author = author;
    }
}
