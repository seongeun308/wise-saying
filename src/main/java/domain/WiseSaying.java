package domain;

import java.util.Objects;

public class WiseSaying {
    private int id;
    private String content;
    private String author;

    public WiseSaying() {
    }

    public WiseSaying(int id, String content, String author) {
        this.id = id;
        this.content = content;
        this.author = author;
    }

    public int getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getAuthor() {
        return author;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WiseSaying that = (WiseSaying) o;
        return id == that.id && Objects.equals(content, that.content) && Objects.equals(author, that.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, content, author);
    }
}
