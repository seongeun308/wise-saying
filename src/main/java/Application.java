import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Application {
    public static void main(String[] args) {
        System.out.println("== 명언 앱 ==");
        Scanner scanner = new Scanner(System.in);

        Map<Integer, WiseSaying> book = new HashMap<>();
        int id = 1;
        while (true) {
            System.out.print("명령) ");
            String command = scanner.next();
            if (command.equals("종료")) break;
            if (command.equals("등록")) {
                System.out.print("명언 : ");
                String body = scanner.next();
                System.out.print("작가 : ");
                String writer = scanner.next();

                book.put(id, new WiseSaying(id, body, writer));
                System.out.println(id++ + "번 명언이 등록되었습니다.");
            }
            if (command.equals("목록")) {
                System.out.println("번호 / 작가 / 명언");
                System.out.println("----------------------");
                book.values().forEach(wiseSaying ->
                        System.out.println(wiseSaying.getId() + "/ "
                                + wiseSaying.getWriter() + " / "
                                + wiseSaying.getBody())
                );
            }
            if (command.startsWith("삭제")) {
                int removeId  = Integer.parseInt(command.substring(6));
                WiseSaying removed = book.remove(removeId);
                if (removed == null)
                    System.out.println(removeId + "번 명언은 존재하지 않습니다.");
                else
                    System.out.println(removeId + "번 명언이 삭제되었습니다.");
            }
        }
    }
}
