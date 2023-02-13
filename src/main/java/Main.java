import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


public class Main {

  public static void main(String[] args) {

    try (ServerSocket serverSocket = new ServerSocket(8989)) {
      BooleanSearchEngine engine = new BooleanSearchEngine(new File("pdfs"));
      Gson gson = new GsonBuilder().create();


      while (true) {
        try (Socket connection = serverSocket.accept();
             BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
             PrintWriter out = new PrintWriter(connection.getOutputStream(), true)) {

          System.out.println("Подключен новый клиент!");
          String word = in.readLine();

          var resultPage = engine.search(word);
          out.println(gson.toJson(resultPage));
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