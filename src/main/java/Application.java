import java.util.Scanner;

public class Application {
    public static void main(String[] args) {
        System.out.println("== 명언 앱 ==");
        Scanner scanner = new Scanner(System.in);

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
                System.out.println(id++ + "번 명언이 등록되었습니다.");
            }
        }
    }
}
