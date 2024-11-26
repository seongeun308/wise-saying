import domain.FilePath;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

    @AfterEach
    void afterEach() {
        File directory = new File(FilePath.DIR);
        directory.listFiles(file -> file.delete());
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


}
