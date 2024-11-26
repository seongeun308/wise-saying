import config.AppConfig;
import domain.FilePath;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import static org.assertj.core.api.Assertions.assertThat;

public class ApplicationTest {
    @BeforeEach
    void setUp() {
        try (PrintWriter writer = new PrintWriter(FilePath.LAST_ID)) {
            writer.println(1);
            writer.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @BeforeAll
    static void beforeAll() {
        // 테스트 전용 경로 설정
        AppConfig.setTest();
        FilePath.setTestFilePath();
    }

    @AfterEach
    void afterEach() {
        File directory = new File(FilePath.DIR);
        directory.listFiles(file -> file.delete());
    }

    @AfterAll
    static void afterAll() {
        // 운영 전용 경로로 다시 설정
        AppConfig.cancelTest();
        FilePath.setOperationFilePath();
    }

    @Test
    void 등록() {
        final String out = AppTest.run("""
                등록
                현재를 사랑하라.
                작자미상
                """);

        assertThat(out)
                .contains("명언 :")
                .contains("작가 :")
                .contains("1번 명언이 등록되었습니다.");
    }
    @Test
    void 목록() {
        final String out = AppTest.run("""
                등록
                현재를 사랑하라.
                작자미상
                목록
                """);

        assertThat(out)
                .contains("번호 / 작가 / 명언")
                .contains("----------------------")
                .contains("1 / 작자미상 / 현재를 사랑하라.");
    }

    @Test
    void 수정() {
        final String out = AppTest.run("""
                등록
                현재를 사랑하라.
                작자미상
                수정?id=1
                미래를 사랑하라.
                작자
                """);

        assertThat(out)
                .contains("명언(기존) : 현재를 사랑하라")
                .contains("명언 :")
                .contains("작가(기존) : 작자미상")
                .contains("작가 :");
    }
    @Test
    void 삭제() {
        final String out = AppTest.run("""
                등록
                현재를 사랑하라.
                작자미상
                삭제?id=1
                """);

        assertThat(out)
                .contains("1번 명언이 삭제되었습니다.");
    }

    @Test
    void 없는_명언_삭제_테스트() {
        final String out = AppTest.run("""
                삭제?id=1
                """);

        assertThat(out)
                .contains("1번 명언은 존재하지 않습니다.");
    }

    @Test
    void 빌드() {
        final String out = AppTest.run("""
                빌드
                """);

        assertThat(out)
                .contains("data.json 파일의 내용이 갱신되었습니다.");
    }

    @Test
    void 검색() {
        final String out = AppTest.run("""
                등록
                현재를 사랑하라.
                작자미상
                등록
                과거에 집착하지 마라.
                작자미상
                목록?keywordType=content&keyword=과거
                목록?keywordType=author&keyword=작자
                """);

        assertThat(out)
                .contains("----------------------")
                .contains("검색타입 : content")
                .contains("검색어 : 과거")
                .contains("----------------------")
                .contains("번호 / 작가 / 명언")
                .contains("----------------------")
                .contains("2 / 작자미상 / 과거에 집착하지 마라.")
                .contains("----------------------")
                .contains("검색타입 : author")
                .contains("검색어 : 작자")
                .contains("----------------------")
                .contains("번호 / 작가 / 명언")
                .contains("2 / 작자미상 / 과거에 집착하지 마라.")
                .contains("1 / 작자미상 / 현재를 사랑하라.");
    }

    @Test
    void 페이징() {
        for (int i = 1; i <= 10; i++) {
            AppTest.run("""
                등록
                명언 %d
                작자미상 %d
                """.formatted(i, i));
        }

        final String out = AppTest.run("""
                목록
                목록?page=2
                """);

        assertThat(out)
                .contains("번호 / 작가 / 명언")
                .contains("----------------------")
                .contains("10 / 작자미상 10 / 명언 10")
                .contains("9 / 작자미상 9 / 명언 9")
                .contains("8 / 작자미상 8 / 명언 8")
                .contains("7 / 작자미상 7 / 명언 7")
                .contains("6 / 작자미상 6 / 명언 6")
                .contains("----------------------")
                .contains("페이지 : [1] / 2")
                .contains("번호 / 작가 / 명언")
                .contains("----------------------")
                .contains("5 / 작자미상 5 / 명언 5")
                .contains("4 / 작자미상 4 / 명언 4")
                .contains("3 / 작자미상 3 / 명언 3")
                .contains("2 / 작자미상 2 / 명언 2")
                .contains("1 / 작자미상 1 / 명언 1")
                .contains("----------------------")
                .contains("페이지 : 1 / [2]");
    }
}
