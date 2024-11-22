package controller;

import domain.WiseSaying;
import service.WiseSayingService;
import view.InputView;
import view.OutputView;

public class WiseSayingController {
    private final WiseSayingService wiseSayingService;
    private final InputView inputView;
    private final OutputView outputView = new OutputView();

    public WiseSayingController(WiseSayingService wiseSayingService, InputView inputView) {
        this.wiseSayingService = wiseSayingService;
        this.inputView = inputView;
    }

    public void read(){
        outputView.printList(wiseSayingService.readAll());
    }

    public void build(){
        try{
            wiseSayingService.build();
            outputView.printBuild();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void delete(String command){
        int deleteId  = Integer.parseInt(command.substring(6));
        try {
            wiseSayingService.delete(deleteId);
            outputView.printDelete(deleteId);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void update(String command) {
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

    public void post() {
        try {
            int wiseSayingId = wiseSayingService.post(inputView.readContent(), inputView.readAuthor());
            outputView.printPost(wiseSayingId);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
