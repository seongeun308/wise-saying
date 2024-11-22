import config.BeanInitialization;
import controller.WiseSayingController;
import view.InputView;

public class App {
    private final WiseSayingController controller;
    private final InputView inputView;

    public App() {
        BeanInitialization beanInitialization = new BeanInitialization();
        controller = beanInitialization.wiseSayingController();
        inputView = beanInitialization.inputView();
    }

    public void run() {
        while (true) {
            String command = inputView.readCommand();
            if (command.equals("종료")) break;
            else if (command.equals("등록")) controller.post();
            else if (command.equals("목록")) controller.read();
            else if (command.matches("수정\\?id=\\d+")) controller.update(command);
            else if (command.matches("삭제\\?id=\\d+")) controller.delete(command);
            else if (command.equals("빌드")) controller.build();
            else System.out.println("명령어를 잘못 입력했습니다.");
        }
        inputView.close();
    }
}
