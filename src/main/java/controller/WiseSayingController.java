package controller;

import domain.WiseSaying;
import service.WiseSayingService;
import view.InputView;
import view.OutputView;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WiseSayingController {
    private final WiseSayingService wiseSayingService = new WiseSayingService();
    private final InputView inputView;
    private final OutputView outputView = new OutputView();

    public WiseSayingController(InputView inputView) {
        this.inputView = inputView;
    }

    public void read(){
        outputView.printList(wiseSayingService.readAll());
    }

    public void build(){
        wiseSayingService.build();
        outputView.printBuild();
    }

    public void delete(String command){
        int deleteId = extractIdByCommand(command);
        wiseSayingService.delete(deleteId);
        outputView.printDelete(deleteId);
    }

    public void update(String command) {
        int updateId = extractIdByCommand(command);
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

    private int extractIdByCommand(String command) {
        Matcher matcher = Pattern.compile("\\d+").matcher(command);
        if (matcher.find())
            return Integer.parseInt(matcher.group());
        throw new RuntimeException("명령어에 id 값이 포함되어 있지 않습니다.");
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
