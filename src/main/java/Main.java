import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class Main {

  public static void main(String[] args) {

    try (ServerSocket serverSocket = new ServerSocket(8989)) {
      while (true) {
        try (Socket connection = serverSocket.accept();
             BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
             PrintWriter out = new PrintWriter(connection.getOutputStream(), true)) {

          System.out.println("Подключен новый клиет!");
          String word = in.readLine();
          BooleanSearchEngine engine = new BooleanSearchEngine(new File("pdfs"));
          List<PageEntry> resultPage = engine.search(word);
          Type type = new TypeToken<List<PageEntry>>() {}.getType();
          Gson gson = new GsonBuilder().create();
          out.println(gson.toJson(resultPage, type));
        } catch (IOException exception) {
          exception.printStackTrace();
        }
      }
    } catch (IOException e) {
      System.out.println("Сервер не может быть запущен!");
      e.printStackTrace();
    }

  }
}