import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileDAO {

    private final String path = "src/main/resources/db/wiseSaying/";
    private final String extension = ".json";

    ObjectMapper mapper = new ObjectMapper();
    int id;

    public FileDAO() throws FileNotFoundException {
        mapper.enable(SerializationFeature.INDENT_OUTPUT, SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS);
        id = new Scanner(new File(path + "lastId.txt")).nextInt();
    }

    public int create(String content, String author) throws IOException {
        WiseSaying wiseSaying = new WiseSaying(id, content, author);
        mapper.writeValue(new File(path + id + extension), wiseSaying);
        id++;
        updateLastId();
        return wiseSaying.id();
    }

    public WiseSaying read(int readId) throws IOException {
        File file = new File(path + readId + extension);
        return mapper.readValue(file, WiseSaying.class);
    }

    public List<WiseSaying> readAll() throws IOException {
        List<WiseSaying> wiseSayingList = new ArrayList<>();
        for (File file : readFilesWithoutBuild())
            wiseSayingList.add(mapper.readValue(file, WiseSaying.class));
        return wiseSayingList;
    }

    public void update(int updateId, String newContent, String newAuthor) throws IOException {
        File file = new File(path + updateId + extension);
        ObjectNode objectNode = (ObjectNode) mapper.readTree(file);

        objectNode.put("content", newContent);
        objectNode.put("author", newAuthor);

        mapper.writeValue(file, objectNode);
    }

    public void delete(int deleteId) throws FileNotFoundException {
        File file = new File(path + deleteId + extension);
        if (!file.delete())
            throw new FileNotFoundException(deleteId + "번 명언은 존재하지 않습니다.");
    }

    public void build() throws IOException {
        ArrayNode arrayNode = mapper.createArrayNode();
        for (File file : readFilesWithoutBuild())
            arrayNode.add(mapper.readTree(file));
        mapper.writeValue(new File(path + "data" + extension), arrayNode);
    }

    private void updateLastId() throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(path + "lastId.txt");
        writer.println(id);
        writer.flush();
        writer.close();
    }

    private File[] readFilesWithoutBuild() {
        File directory = new File(path);
        return directory.listFiles((dir, name) -> name.endsWith(".json") && !name.equals("data.json"));
    }
}
