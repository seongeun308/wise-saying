package view;

import java.util.Scanner;

public class InputView {
    private final Scanner scanner = new Scanner(System.in);

    public InputView() {
        System.out.println("== 명언 앱 ==");
    }

    public String readCommand() {
        System.out.print("명령) ");
        return scanner.next();
    }

    public String readContent() {
        System.out.print("명언 : ");
        return scanner.next();
    }

    public String readAuthor() {
        System.out.print("작가 : ");
        return scanner.next();
    }

    public void close() {
        scanner.close();
    }
}
