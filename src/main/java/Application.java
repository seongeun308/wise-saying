import java.util.Scanner;

public class Application {
    public static void main(String[] args) {
        System.out.println("== 명언 앱 ==");
        Scanner scanner = new Scanner(System.in);

        System.out.print("명령) ");
        String command = scanner.next();
        if (command.equals("종료")) System.exit(0);
    }
}
