package view;

import domain.WiseSaying;

import java.util.List;

public class OutputView {
    public void printPost(int id) {
        System.out.println(id + "번 명언이 등록되었습니다.");
    }

    public void printList(List<WiseSaying> wiseSayingList) {
        System.out.println("번호 / 작가 / 명언");
        System.out.println("----------------------");

        wiseSayingList.forEach(wiseSaying -> System.out.println(wiseSaying.getId() + " / "
                + wiseSaying.getAuthor() + " / "
                + wiseSaying.getContent()));
    }

    public void printPage(int page) {
        System.out.println("----------------------");
        if (page % 2 != 0)
            System.out.printf("페이지 : [%d] / %d%n", page, page + 1);
        else
            System.out.printf("페이지 : %d / [%d]%n", page - 1, page);
    }

    public void printDelete(int id) {
        System.out.println(id + "번 명언이 삭제되었습니다.");
    }

    public void printContent(String  content) {
        System.out.println("명언(기존) : " + content);
    }

    public void printAuthor(String author) {
        System.out.println("작가(기존) : " + author);
    }

    public void printBuild() {
        System.out.println("data.json 파일의 내용이 갱신되었습니다.");
    }

    public void printSearch(String keywordType, String keyword) {
        System.out.println("----------------------");
        System.out.println("검색타입 : " + keywordType);
        System.out.println("검색어 : " + keyword);
        System.out.println("----------------------");
    }
}
