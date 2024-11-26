import controller.WiseSayingController;
import view.InputView;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class App {
    private final InputView inputView;
    private final WiseSayingController controller;

    public App() {
        this.inputView = new InputView(new Scanner(System.in));
        this.controller = new WiseSayingController(inputView);
    }

    public void run() {
        System.out.println("== 명언 앱 ==");
        while (true) {
            String command = inputView.readCommand();
            try {
                if (command.equals("종료")) break;
                if (command.equals("등록")) controller.post();
                if (command.equals("목록")) controller.read();
                if (command.matches("목록\\?page=\\d+")) controller.read(extractIntByCommand(command));
                if (command.matches("수정\\?id=\\d+")) controller.update(extractIntByCommand(command));
                if (command.matches("삭제\\?id=\\d+")) controller.delete(extractIntByCommand(command));
                if (command.matches("목록\\?keywordType=[a-zA-Z]+&keyword=\\p{L}+")) controller.search(command);
                if (command.equals("빌드")) controller.build();
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
            }
        }
        inputView.close();
    }

    private int extractIntByCommand(String command) {
        Matcher matcher = Pattern.compile("\\d+").matcher(command);
        if (matcher.find())
            return Integer.parseInt(matcher.group());
        throw new RuntimeException("명령어에 값이 포함되어 있지 않습니다.");
    }
}
