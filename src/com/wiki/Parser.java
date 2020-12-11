package com.wiki;

/*
Здесь будут все 5 пункта т.е.
1)Считать запрос
2)Сделать запрос к серверу
3)Распарсить ответ
4)Вывести результат
5)Если найдена конкретная статья, распечатать её первый блок (краткая информация)зультат

 */

import com.google.gson.Gson;
import com.google.gson.JsonArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class Parser {
    public static String title_api_url(String language,String specific_topic){
        //генерируем нашу статью и тем самым потом получаем название статей
        return "https://" + language + ".wikipedia.org/w/api.php?action=opensearch&format=json&search=" + specific_topic;
    }
    public static String my_content(String language){
        //Получение информации о версии страницы (revision и потом в конце plain без форматирования)
        //вся инфа будет в формате json
        return "https://" + language + ".wikipedia.org/w/api.php?action=query&prop=revisions&explaintext&exsectionformat=plain" + "&prop=extracts&format=json&redirects&titles=";
    }
    public static String connecting_url(String url2)throws IOException {
        //идет запрос к серверу. Нужно создать открыть соединение и получить InputStream
        URL url = new URL(url2);
        HttpURLConnection connect = (HttpURLConnection)url.openConnection(); //используем HttpURLConnection для GET запроса
        connect.setRequestMethod("GET");
        // читаем данные в буфер
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connect.getInputStream())); // будем считывать данные из InputStream
        String reply = bufferedReader.readLine(); // чтение построчно
        bufferedReader.close();  // закрытие
        return reply;

    }

    public static void my_article(String language, String specific_topic) throws IOException {
        // будем использовать библу Gson так как сервер возвращает данные в формате json и лучше всего подойдет Gson
        Gson gson = new Gson();
        String my_api_title = title_api_url(language,specific_topic);
        String content = my_content(language);
        String my_topic = specific_topic;


    }
}
