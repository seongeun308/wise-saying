package controller;

import domain.WiseSaying;
import service.WiseSayingService;
import view.InputView;
import view.OutputView;

import java.util.List;

public class WiseSayingController {
    private final WiseSayingService wiseSayingService = new WiseSayingService();
    private final InputView inputView;
    private final OutputView outputView = new OutputView();

    public WiseSayingController(InputView inputView) {
        this.inputView = inputView;
    }

    public void read(int page){
        outputView.printList(wiseSayingService.readByPaging(page));
        outputView.printPage(page);
    }

    public void read(){
        read(1);
    }

    public void build(){
        wiseSayingService.build();
        outputView.printBuild();
    }

    public void delete(int deleteId){
        wiseSayingService.delete(deleteId);
        outputView.printDelete(deleteId);
    }

    public void update(int updateId) {
        WiseSaying oldWiseSaying = wiseSayingService.read(updateId);
        outputView.printContent(oldWiseSaying.getContent());
        String newContent = inputView.readContent();
        outputView.printAuthor(oldWiseSaying.getAuthor());
        String newAuthor = inputView.readAuthor();
        wiseSayingService.update(updateId, newContent, newAuthor);
    }

    public void post() {
        int wiseSayingId = wiseSayingService.post(inputView.readContent(), inputView.readAuthor());
        outputView.printPost(wiseSayingId);
    }



    public void search(String command) {
        String[] words = command.split("[/?=&]");
        String keywordType = words[2];
        String keyword = words[4];
        List<WiseSaying> searched = wiseSayingService.searchForKeyword(keywordType, keyword);
        outputView.printSearch(keywordType, keyword);
        outputView.printList(searched);
    }
}
