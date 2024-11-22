package service;

import domain.WiseSaying;
import repository.WiseSayingRepository;

import java.util.List;

public class WiseSayingService {
    private final WiseSayingRepository wiseSayingRepository;

    public WiseSayingService(WiseSayingRepository wiseSayingRepository) {
        this.wiseSayingRepository = wiseSayingRepository;
    }

    public int post(String content, String author) throws Exception {
        int id = wiseSayingRepository.create(content, author);
        if (id == -1)
            throw new Exception("명언 등록 실패");
        return id;
    }

    public WiseSaying read(int id) throws Exception {
        WiseSaying wiseSaying = wiseSayingRepository.read(id);
        if (wiseSaying == null)
            throw new Exception("명언 읽기 실패");
        return wiseSaying;
    }

    public List<WiseSaying> readAll(){
        return wiseSayingRepository.readAll();
    }

    public void build() throws Exception {
        boolean isBuild = wiseSayingRepository.build();
        if (!isBuild)
            throw new Exception("명언 빌드 실패");
    }

    public void delete(int deleteId) throws Exception {
        wiseSayingRepository.delete(deleteId);
    }

    public void update(int updateId, String newContent, String newAuthor) throws Exception {
        WiseSaying newWiseSaying = wiseSayingRepository.update(updateId, newContent, newAuthor);
        if (newWiseSaying == null)
            throw new Exception("명언 수정 실패");
    }
}
