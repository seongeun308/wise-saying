import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Application {
    public Application() {
    }

    public static void main(String[] args) throws IOException {
        System.out.println("== 명언 앱 ==");
        Scanner scanner = new Scanner(System.in);

        ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT, SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS);
        int id = new Scanner(new File("src/main/resources/db/wiseSaying/lastId.txt")).nextInt();

        while (true) {
            System.out.print("명령) ");
            String command = scanner.next();
            if (command.equals("종료")) break;
            if (command.equals("등록")) {
                System.out.print("명언 : ");
                String content = scanner.next();
                System.out.print("작가 : ");
                String author = scanner.next();

                WiseSaying wiseSaying = new WiseSaying(id, content, author);
                mapper.writeValue(new File("src/main/resources/db/wiseSaying/" + id + ".json"), wiseSaying);

                System.out.println(id++ + "번 명언이 등록되었습니다.");
            }
            if (command.equals("목록")) {
                System.out.println("번호 / 작가 / 명언");
                System.out.println("----------------------");
                File directory = new File("src/main/resources/db/wiseSaying");
                File[] files = directory.listFiles((dir, name) -> name.endsWith(".json"));

                for (File file : files) {
                    WiseSaying wiseSaying = mapper.readValue(file, WiseSaying.class);
                    System.out.println(wiseSaying.getId() + "/ "
                            + wiseSaying.getAuthor() + " / "
                            + wiseSaying.getContent());
                }
            }
            if (command.startsWith("삭제")) {
                int removeId  = Integer.parseInt(command.substring(6));
                File file = new File("src/main/resources/db/wiseSaying/" + removeId + ".json");
                if (file.delete()) {
                    System.out.println(removeId + "번 명언이 삭제되었습니다.");
                }
                else {
                    System.out.println(removeId + "번 명언은 존재하지 않습니다.");
                }
            }
            if (command.startsWith("수정")) {
                int updateId  = Integer.parseInt(command.substring(6));
                File file = new File("src/main/resources/db/wiseSaying/" + updateId + ".json");
                ObjectNode objectNode = (ObjectNode) mapper.readTree(file);

                System.out.println("명언(기존) : " + objectNode.get("content"));
                System.out.print("명언 : ");
                String newContent = scanner.next();

                System.out.println("작가(기존) : " + objectNode.get("author"));
                System.out.print("작가 : ");
                String newAuthor = scanner.next();

                objectNode.put("content", newContent);
                objectNode.put("author", newAuthor);

                mapper.writeValue(file, objectNode);
            }
            if (command.equals("빌드")) {
                File directory = new File("src/main/resources/db/wiseSaying");
                File[] files = directory.listFiles((dir, name) -> name.endsWith(".json") && !name.equals("data.json"));
                ArrayNode arrayNode = mapper.createArrayNode();

                for (File file : files)
                    arrayNode.add(mapper.readTree(file));
                mapper.writeValue(new File("src/main/resources/db/wiseSaying/data.json"), arrayNode);
                System.out.println("data.json 파일의 내용이 갱신되었습니다.");
            }
        }
        PrintWriter writer = new PrintWriter("src/main/resources/db/wiseSaying/lastId.txt");
        writer.println(id);
        writer.flush();
        writer.close();
        scanner.close();
    }
}
