package repository;

import domain.WiseSaying;
import util.JsonUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class WiseSayingRepository {
    private final static String PATH = "src/main/resources/db/wiseSaying/";
    private final static String EXTENSION = ".json";

    public int create(String content, String author) {
        int id = getLastId();
        WiseSaying wiseSaying = new WiseSaying(id++, content, author);
        int createdId = create(wiseSaying);
        if (createdId == -1) return -1;
        updateLastId(id);
        return wiseSaying.getId();
    }

    public int create(WiseSaying wiseSaying) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PATH + wiseSaying.getId() + EXTENSION))) {
            writer.write(JsonUtils.serialize(wiseSaying));
        } catch (IOException e) {
            return -1;
        }
        return wiseSaying.getId();
    }

    public WiseSaying read(int id) {
        File file = new File(PATH + id + EXTENSION);
        if (!file.exists())  return null;
        return read(file);
    }

    public WiseSaying read(File file) {
        WiseSaying wiseSaying;
        try (InputStream inputStream = new FileInputStream(file)) {
            byte[] fileBytes = inputStream.readAllBytes();
            wiseSaying = JsonUtils.deserialize(new String(fileBytes, StandardCharsets.UTF_8));
        } catch (IOException e) {
            return null;
        }
        return wiseSaying;
    }

    public List<WiseSaying> readAll() {
        File[] files = getFiles();
        if (files == null)
            return new ArrayList<>();
        return Arrays.stream(files)
                .map(this::read)
                .toList();
    }

    public List<String> readAllJson() {
        return readAll().stream()
                .map(JsonUtils::serialize)
                .toList();
    }

    public WiseSaying update(int updateId, String newContent, String newAuthor) {
        WiseSaying oldWiseSaying = read(updateId);
        if (oldWiseSaying == null) return null;
        WiseSaying newWiseSaying = new WiseSaying(oldWiseSaying.getId(), newContent, newAuthor);
        create(newWiseSaying);
        return newWiseSaying;
    }

    public void delete(int deleteId) throws Exception {
        File file = new File(PATH + deleteId + EXTENSION);
        if (!file.exists())
            throw new Exception(deleteId + "번 명언은 존재하지 않습니다.");
        if (!file.delete())
            throw new Exception("명언 삭제 실패");
    }

    public boolean build() {
        String jsonList = JsonUtils.toJsonList(readAllJson());
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PATH + "data.json"))) {
            writer.write(jsonList);
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    private int getLastId() {
        File lastIdFile = new File(PATH + "lastId.txt");
        if (!lastIdFile.exists()) return -1;
        try (Scanner scanner = new Scanner(lastIdFile)) {
            return scanner.nextInt();
        } catch (FileNotFoundException e) {
            return -1;
        }
    }

    private boolean updateLastId(int id){
        try (PrintWriter writer = new PrintWriter(PATH + "lastId.txt")){
            writer.println(id);
        } catch (FileNotFoundException e) {
            return false;
        }
        return true;
    }

    private File[] getFiles() {
        File directory = new File(PATH);
        if (!directory.exists()) return null;
        return directory.listFiles((dir, name) -> name.endsWith(".json") && !name.endsWith("data.json"));
    }
}
