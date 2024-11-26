import java.io.ByteArrayOutputStream;

public class AppTest {
    public static String run(String input) {
        System.setIn(TestUtil.genScanner(input + "종료"));
        ByteArrayOutputStream out = TestUtil.setOutToByteArray();

        App app = new App();
        app.run();

        String result = out.toString().trim();
        TestUtil.clearSetOutToByteArray(out);

        return result;
    }
}
