package view;

import java.util.Scanner;

public class InputView {
    private final Scanner scanner;

    public InputView(Scanner scanner) {
        this.scanner = scanner;
    }

    public String readCommand() {
        System.out.print("명령) ");
        return scanner.nextLine();
    }

    public String readContent() {
        System.out.print("명언 : ");
        String next = scanner.nextLine();
        return next;
    }

    public String readAuthor() {
        System.out.print("작가 : ");
        return scanner.nextLine();
    }

    public void close() {
        scanner.close();
    }
}
