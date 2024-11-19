public class WiseSaying {
    private final int id;
    private final String writer;
    private final String body;

    public WiseSaying(int id, String writer, String body) {
        this.id = id;
        this.writer = writer;
        this.body = body;
    }

    public int getId() {
        return id;
    }

    public String getWriter() {
        return writer;
    }

    public String getBody() {
        return body;
    }
}
