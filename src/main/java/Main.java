
import jsonparsing.GsonParser;
import five_day_forecast.Root;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.ExecutionException;

public class Main {
    public static void main(String[] args) throws URISyntaxException, ExecutionException, InterruptedException{


        String APIkey = new String();
        File file = new File("C:\\\\Users\\User\\APIkey.txt");
        try (FileReader reader = new FileReader(file))
        {
            char[] chars = new char[(int) file.length()];
            reader.read(chars);

            APIkey = new String(chars);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        var client = HttpClient.newHttpClient();
        var request = HttpRequest
                .newBuilder()
                .uri(new URI("https://api.openweathermap.org/data/2.5/forecast?q=Budënnovsk&lang=ru&units=metric" +
                        "&appid=" + APIkey))
                .header("accept", "application/json")
                .build();
        var response = client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .get();

        System.out.println(response);
        System.out.println("-------------------------------------");

        request = HttpRequest
                .newBuilder()
                .uri(new URI("https://api.openweathermap.org/data/2.5/weather?q=Budënnovsk&lang=ru&units=metric" +
                "&appid=" + APIkey))
                .header("accept", "application/json")
                .build();

        response = client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .get();

        System.out.println(response);
        System.out.println("-------------------------------------");

        GsonParser parser  = new GsonParser();
        //  Root root = parser.parse_fd_forecast();

    }
}