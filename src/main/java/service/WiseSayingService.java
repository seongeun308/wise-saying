package service;

import domain.WiseSaying;
import repository.WiseSayingRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class WiseSayingService {
    private final WiseSayingRepository wiseSayingRepository = new WiseSayingRepository();

    public int post(String content, String author) {
        return wiseSayingRepository.create(content, author);
    }

    public WiseSaying read(int id) {
        return wiseSayingRepository.findById(id);
    }

    public List<WiseSaying> readByPaging(int page, int count){
        List<WiseSaying> all = new ArrayList<>(wiseSayingRepository.findAll());
        all.sort(Comparator.comparingInt(WiseSaying::getId).reversed());
        int startId = (page - 1) * 5;
        return all.stream()
                .skip(startId)
                .limit(count)
                .toList();
    }

    public List<WiseSaying> readByPaging(int page){
        return readByPaging(page, 5);
    }

    public void build() {
        wiseSayingRepository.build();
    }

    public void delete(int deleteId) {
        wiseSayingRepository.delete(deleteId);
    }

    public void update(int updateId, String newContent, String newAuthor) {
        WiseSaying newWiseSaying = new WiseSaying(updateId, newContent, newAuthor);
        wiseSayingRepository.update(newWiseSaying);
    }

    public List<WiseSaying> searchForKeyword(String keywordType, String keyword) {
        if (keywordType.equals("author"))
            return wiseSayingRepository.findByAuthor(keyword);
        if (keywordType.equals("content"))
            return wiseSayingRepository.findByContent(keyword);
        return List.of();
    }
}
