import com.google.gson.Gson;
import org.json.simple.JSONArray;

import java.io.*;
import java.net.Socket;

public class Client {

  public static void main(String[] args) {

    try (Socket client = new Socket("localhost", 8989);
         BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
         PrintWriter out = new PrintWriter(client.getOutputStream(), true)) {

      out.println("БизНес");
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