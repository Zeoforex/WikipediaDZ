import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        ArrayList languageList = new ArrayList<>(Arrays.asList("en", "ru"));
        String language="lang";
        Scanner in = new Scanner(System.in);
        while (!languageList.contains(language)) {
            System.out.println("Введите en чтобы продолжить");
            language = in.nextLine();
        }
        Boolean areWeDone = false;
        while (!areWeDone) {
            System.out.println("Введите запрос");
            String specificTopic = in.nextLine();

            String link = Parser.myContent(language, specificTopic);
            try {
                String requestRes = Parser.connectingUrl(link);

                areWeDone = Parser.searchOutput(requestRes);
            }catch (IOException e){
                areWeDone = false;
            }
        }
    }
}
