/*
Здесь будут все 5 пункта т.е.
1)Считать запрос
2)Сделать запрос к серверу
3)Распарсить ответ
4)Вывести результат
5)Если найдена конкретная статья, распечатать её первый блок (краткая информация)зультат

 */

import com.google.gson.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;


public class Parser {
    public static String myContent(String language, String specific_topic){
        //Получение информации о версии страницы (revision и потом в конце plain без форматирования)
        //вся инфа будет в формате json
        return "https://" + language + ".wikipedia.org/w/api.php?action=opensearch&search=" + specific_topic + "&format=json";
    }

    public static String connectingUrl(String url2)throws IOException {
        //идет запрос к серверу. Нужно создать открыть соединение и получить InputStream
        url2 = url2.replaceAll(" ", "_");
        URL url = new URL(url2);
        HttpURLConnection connect = (HttpURLConnection)url.openConnection(); //используем HttpURLConnection для GET запроса
        connect.setRequestMethod("GET");
        // читаем данные в буфер
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connect.getInputStream())); // будем считывать данные из InputStream
        String reply = bufferedReader.readLine(); // чтение построчно
        bufferedReader.close();  // закрытие
        return reply;

    }


    public static Boolean searchOutput(String inputJSON) throws IOException {
        Gson gson = new Gson();
        ArrayList doneJSON = gson.fromJson(inputJSON, ArrayList.class);
        ArrayList searchOutputPageNames = new ArrayList((Collection) doneJSON.get(1));
        if (searchOutputPageNames.size() > 1) {
            System.out.println(searchOutputPageNames);
        }
        switch (searchOutputPageNames.size()){

            //такой статьи нет
            case 0:
                System.out.println("Ничего не найдено");
                return false;


//                одна статья в выдаче
            case 1:
                printPage(searchOutputPageNames.get(0).toString());
                return true;

//                в выдаче больше одной статьи
            default:


                Boolean check = false;
                while (!check)
                    try {
                        Scanner in = new Scanner(System.in);
                        String pageChoice = in.nextLine();
                        for(Object item : searchOutputPageNames) {
                            if(item.toString().equalsIgnoreCase(pageChoice)){
                                printPage(pageChoice);
                                check = true;
                            }
                        }

                    }catch (IndexOutOfBoundsException e){
                        check = false;
                        System.out.println("Попробуйте снова");
                    }
                return true;

        }

    }

    public static void printPage(String pageName) throws IOException {
        String rawJSON = connectingUrl("https://en.wikipedia.org/api/rest_v1/page/summary/" + pageName);
//        System.out.println(rawJSON);
        Gson gson = new Gson();

        JsonObject json = gson.fromJson(rawJSON, JsonObject.class);
        String preOutput = json.get("extract").toString();
        String output = "\n\n";
        int breakSize = 40;
        for (int i = 1; i < preOutput.length(); i++) {
            if ( i % breakSize == 0 ){

                String temp = preOutput.substring(i, (i + breakSize)<preOutput.length() ? i + breakSize : preOutput.length()).replaceFirst(" ", "\n");
                output +=  preOutput.substring(i-breakSize, i) + temp;
            }
        }

        System.out.println(output);

    }

}