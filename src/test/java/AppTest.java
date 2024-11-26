import config.AppConfig;
import domain.FilePath;

import java.io.ByteArrayOutputStream;

public class AppTest {

    public static String run(String input) {
        System.setIn(TestUtil.genScanner(input + "종료"));
        ByteArrayOutputStream out = TestUtil.setOutToByteArray();

        // 테스트 전용 경로 설정
        AppConfig.setTest();
        FilePath.setTestFilePath();

        App app = new App();
        app.run();

        // 운영 전용 경로로 다시 설정
        AppConfig.cancelTest();
        FilePath.setOperationFilePath();

        String result = out.toString().trim();
        TestUtil.clearSetOutToByteArray(out);

        return result;
    }
}
