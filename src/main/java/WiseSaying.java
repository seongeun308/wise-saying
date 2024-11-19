import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class WiseSaying {
    private final int id;
    private final String content;
    private final String author;

    @JsonCreator
    public WiseSaying(@JsonProperty("id") int id,
                      @JsonProperty("content") String content,
                      @JsonProperty("author")String author) {
        this.id = id;
        this.content = content;
        this.author = author;
    }

    public int getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }
}
