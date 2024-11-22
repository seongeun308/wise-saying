package controller;

import domain.WiseSaying;
import service.WiseSayingService;
import view.InputView;
import view.OutputView;

public class WiseSayingController {
    private final InputView inputView = new InputView();
    private final OutputView outputView = new OutputView();
    private final WiseSayingService wiseSayingService;

    public WiseSayingController(WiseSayingService wiseSayingService) {
        this.wiseSayingService = wiseSayingService;
    }

    public void run() {
        while (true) {
            String command = inputView.readCommand();
            if (command.equals("종료")) break;
            else if (command.equals("등록")) post();
            else if (command.equals("목록")) read();
            else if (command.startsWith("수정")) update(command);
            else if (command.startsWith("삭제")) delete(command);
            else if (command.equals("빌드")) build();
            else System.out.println("명령어를 잘못 입력했습니다.");
        }
        inputView.close();
    }

    private void read(){
        outputView.printList(wiseSayingService.readAll());
    }

    private void build(){
        try{
            wiseSayingService.build();
            outputView.printBuild();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void delete(String command){
        int deleteId  = Integer.parseInt(command.substring(6));
        try {
            wiseSayingService.delete(deleteId);
            outputView.printDelete(deleteId);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void update(String command) {
        int updateId  = Integer.parseInt(command.substring(6));
        WiseSaying oldWiseSaying;
        try {
            oldWiseSaying = wiseSayingService.read(updateId);
            outputView.printContent(oldWiseSaying.getContent());
            String newContent = inputView.readContent();
            outputView.printAuthor(oldWiseSaying.getAuthor());
            String newAuthor = inputView.readAuthor();
            wiseSayingService.update(updateId, newContent, newAuthor);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void post() {
        try {
            int wiseSayingId = wiseSayingService.post(inputView.readContent(), inputView.readAuthor());
            outputView.printPost(wiseSayingId);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
