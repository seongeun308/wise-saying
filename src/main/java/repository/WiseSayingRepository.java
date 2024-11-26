package repository;

import domain.FilePath;
import domain.WiseSaying;
import util.JsonUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class WiseSayingRepository {

    public int create(String content, String author) {
        int id = getLastId();
        WiseSaying wiseSaying = new WiseSaying(id++, content, author);
        createByWiseSaying(wiseSaying);
        updateLastId(id);
        return wiseSaying.getId();
    }

    private void createByWiseSaying(WiseSaying wiseSaying) {
        String path = FilePath.WISE_SAYING.formatted(wiseSaying.getId());
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            writer.write(JsonUtils.serialize(wiseSaying));
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException("명언 등록 실패했습니다.", e);
        }
    }

    public WiseSaying findById(int id) {
        File file = checkIsExistFile(id, FilePath.WISE_SAYING.formatted(id));
        return findByFile(file);
    }

    private WiseSaying findByFile(File file) {
        WiseSaying wiseSaying;
        try (InputStream inputStream = new FileInputStream(file)) {
            byte[] fileBytes = inputStream.readAllBytes();
            wiseSaying = JsonUtils.deserialize(new String(fileBytes, StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new RuntimeException("명언 읽기 실패했습니다.", e);
        }
        return wiseSaying;
    }

    public List<WiseSaying> findByAuthor (String keyword) {
        List<WiseSaying> all = findAll();
        return all.stream()
                .filter(wiseSaying -> wiseSaying.getAuthor().contains(keyword))
                .toList();
    }

    public List<WiseSaying> findByContent (String keyword) {
        List<WiseSaying> all = findAll();
        return all.stream()
                .filter(wiseSaying -> wiseSaying.getContent().contains(keyword))
                .toList();
    }

    public List<WiseSaying> findAll() {
        File[] files = getFiles();
        if (files == null)
            return new ArrayList<>();
        return Arrays.stream(files)
                .map(this::findByFile)
                .toList();
    }

    public void update(WiseSaying newWiseSaying) {
        createByWiseSaying(newWiseSaying);
    }

    public void delete(int deleteId){
        File file = checkIsExistFile(deleteId, FilePath.WISE_SAYING.formatted(deleteId));
        if (!file.delete())
            throw new RuntimeException("명언 삭제 실패했습니다.");
    }

    public void build() {
        String jsonList = JsonUtils.toJsonList(findAllForJson());
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FilePath.BUILD))) {
            writer.write(jsonList);
        } catch (IOException e) {
            throw new RuntimeException("명언 빌드에 실패했습니다.", e);
        }
    }

    private File checkIsExistFile(int id, String path) {
        File file = new File(path);
        if (!file.exists())
            throw new RuntimeException(id + "번 명언은 존재하지 않습니다.");
        return file;
    }

    private List<String> findAllForJson() {
        return findAll().stream()
                .map(JsonUtils::serialize)
                .toList();
    }

    private int getLastId() {
        try (Scanner scanner = new Scanner(new File(FilePath.LAST_ID))) {
            return scanner.nextInt();
        } catch (FileNotFoundException e) {
            throw new RuntimeException("마지막 아이디를 찾는데 실패했습니다.");
        }
    }

    private void updateLastId(int id){
        try (PrintWriter writer = new PrintWriter(FilePath.LAST_ID)){
            writer.println(id);
            writer.flush();
        } catch (FileNotFoundException e) {
            throw new RuntimeException("마지막 아이디를 저장하는데 실패했습니다.");
        }
    }

    private File[] getFiles() {
        File directory = new File(FilePath.DIR);
        return directory.listFiles((dir, name) -> name.endsWith(".json") && !name.endsWith("data.json"));
    }
}
