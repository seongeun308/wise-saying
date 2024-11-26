package service;

import domain.WiseSaying;
import repository.WiseSayingRepository;

import java.util.List;

public class WiseSayingService {
    private final WiseSayingRepository wiseSayingRepository = new WiseSayingRepository();

    public int post(String content, String author) {
        return wiseSayingRepository.create(content, author);
    }

    public WiseSaying read(int id) {
        return wiseSayingRepository.readById(id);
    }

    public List<WiseSaying> readAll(){
        return wiseSayingRepository.readAll();
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
}
