package repository;

import domain.WiseSaying;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class WiseSayingRepositoryTest {
    private final WiseSayingRepository repository = new WiseSayingRepository();

    @AfterEach
    void afterAll() {
        File directory = new File("src/main/resources/db/wiseSaying");
        Arrays.stream(directory.listFiles()).forEach(file -> file.delete());
        try (PrintWriter writer = new PrintWriter("src/main/resources/db/wiseSaying/lastId.txt")){
            writer.println(1);
        } catch (FileNotFoundException ignore) {}
    }

    @Test
    void 명언_등록_성공_테스트() {
        String content = "명언1";
        String author = "작가1";

        int id = repository.create(content, author);

        assertThat(id).isEqualTo(1);
    }

    @Test
    void 명언_읽어오기_성공_테스트() {
        String content = "명언2";
        String author = "작가2";
        int id = repository.create(content, author);

        WiseSaying wiseSaying = repository.read(id);

        assertThat(wiseSaying.getContent()).isEqualTo(content);
        assertThat(wiseSaying.getAuthor()).isEqualTo(author);
    }

    @Test
    void 명언_읽어오기_실패_시_null_반환() {
        assertThat(repository.read(-1)).isEqualTo(null);
    }

    @Test
    void 명언_목록_읽어오기_성공_테스트() {
        WiseSaying wiseSaying1 = new WiseSaying(1, "test1", "test1");
        WiseSaying wiseSaying2 = new WiseSaying(2, "test2", "test2");
        WiseSaying wiseSaying3 = new WiseSaying(3, "test3", "test3");
        repository.create(wiseSaying1);
        repository.create(wiseSaying2);
        repository.create(wiseSaying3);

        List<WiseSaying> wiseSayings = repository.readAll();

        assertThat(wiseSayings).contains(wiseSaying1, wiseSaying2, wiseSaying3);
    }

    @Test
    void 명언_목록_읽어오기_실패_시_빈_리스트_반환() {
        assertThat(repository.readAll()).isEmpty();
    }

    @Test
    void 명언_수정_성공_테스트() {
        int id = repository.create(new WiseSaying(1, "test1", "test1"));

        String newContent = "test2";
        String newAuthor = "test2";
        WiseSaying newWiseSaying = repository.update(id, newContent, newAuthor);

        assertThat(newWiseSaying.getContent()).isEqualTo(newContent);
        assertThat(newWiseSaying.getAuthor()).isEqualTo(newAuthor);
    }

    @Test
    void 명언_수정_실패_시_null_반환() {
        assertThat(repository.update(-1, "test2", "test2")).isEqualTo(null);
    }

    @Test
    void 명언_삭제_성공_테스트() {
        int id = repository.create(new WiseSaying(1, "test1", "test1"));

        assertDoesNotThrow(() -> repository.delete(id));
    }

    @Test
    void 명언_삭제_실패_시_예외_발생() {
        Exception e = assertThrows(Exception.class, () -> repository.delete(-1));
        assertThat(e.getMessage()).isEqualTo("-1번 명언은 존재하지 않습니다.");
    }

    @Test
    void 명언_빌드_성공_테스트() {
        repository.create(new WiseSaying(1, "test1", "test1"));
        repository.create(new WiseSaying(2, "test2", "test2"));
        repository.create(new WiseSaying(3, "test3", "test3"));

        assertTrue(repository.build());
    }
}