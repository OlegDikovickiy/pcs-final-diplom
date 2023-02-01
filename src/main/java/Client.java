import com.google.gson.Gson;
import org.json.simple.JSONArray;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

  public static void main(String[] args) {

    try (Socket client = new Socket("localhost", 8989);
         BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
         PrintWriter out = new PrintWriter(client.getOutputStream(), true)) {

      out.println("опыт");
      String result = in.readLine();

      Gson gson = new Gson();
      JSONArray jsonArray = gson.fromJson(result, JSONArray.class);

      for (Object jsonObject : jsonArray) {
        System.out.println(jsonObject);
      }

    } catch (IOException exception) {
      exception.printStackTrace();
    }
  }
}