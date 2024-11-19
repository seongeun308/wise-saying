import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class WiseSayingController {
    private final InputView inputView = new InputView();
    private final OutputView outputView = new OutputView();
    private final FileDAO fileDAO = new FileDAO();

    public WiseSayingController() throws FileNotFoundException {
    }

    public void run() throws IOException {
        while (true) {
            String command = inputView.readCommand();
            if (command.equals("종료")) break;
            if (command.equals("등록")) post();
            if (command.equals("목록")) read();
            if (command.startsWith("수정")) update(command);
            if (command.startsWith("삭제")) delete(command);
            if (command.equals("빌드")) build();

        }
        inputView.close();
    }

    private void read() throws IOException {
        List<WiseSaying> wiseSayingList = fileDAO.readAll();
        outputView.printList(wiseSayingList);
    }

    private void build() throws IOException {
        fileDAO.build();
        outputView.printBuild();
    }

    private void delete(String command){
        int deleteId  = Integer.parseInt(command.substring(6));
        try {
            fileDAO.delete(deleteId);
            outputView.printDelete(deleteId);
        } catch (FileNotFoundException ignored) {
            System.out.println(deleteId + "번 명언은 존재하지 않습니다.");
        }
    }

    private void update(String command) throws IOException {
        int updateId  = Integer.parseInt(command.substring(6));
        WiseSaying oldWiseSaying = fileDAO.read(updateId);
        outputView.printContent(oldWiseSaying.content());
        String newContent = inputView.readContent();
        outputView.printAuthor(oldWiseSaying.author());
        String newAuthor = inputView.readAuthor();
        fileDAO.update(updateId, newContent, newAuthor);
    }

    private void post() throws IOException {
        int wiseSayingId = fileDAO.create(inputView.readContent(), inputView.readAuthor());
        outputView.printPost(wiseSayingId);
    }
}
