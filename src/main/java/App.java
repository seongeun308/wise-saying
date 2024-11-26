import controller.WiseSayingController;
import view.InputView;

import java.util.Scanner;

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
                if (command.matches("수정\\?id=\\d+")) controller.update(command);
                if (command.matches("삭제\\?id=\\d+")) controller.delete(command);
                if (command.matches("목록\\?keywordType=[a-zA-Z]+&keyword=\\p{L}+")) controller.search(command);
                if (command.equals("빌드")) controller.build();
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
            }
        }
        inputView.close();
    }
}
