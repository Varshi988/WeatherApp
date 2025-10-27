package Backend;
import java.io.*;
import java.net.*;
import com.sun.net.httpserver.*;
import java.util.Random;

public class WeatherServer {

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        System.out.println("✅ Weather Server started at http://localhost:8080");
        server.createContext("/weather", new WeatherHandler());
        server.setExecutor(null);
        server.start();
    }

    static class WeatherHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            Headers headers = exchange.getResponseHeaders();
            headers.add("Access-Control-Allow-Origin", "*");
            headers.add("Content-Type", "application/json");

            String query = exchange.getRequestURI().getQuery();
            String city = "Unknown";
            if (query != null && query.contains("city=")) {
                city = query.split("=")[1].replace("%20", " ");
            }

            // Simulate random temperature and weather condition
            Random random = new Random();
            double temp = 15 + random.nextDouble() * 20;
            String[] conditions = {"Sunny", "Cloudy", "Rainy", "Windy", "Foggy"};
            String condition = conditions[random.nextInt(conditions.length)];

            // Manually create JSON string
            String jsonResponse = String.format(
                "{\"city\":\"%s\", \"temperature\":\"%.1f\", \"condition\":\"%s\"}",
                city, temp, condition
            );

            byte[] response = jsonResponse.getBytes();
            exchange.sendResponseHeaders(200, response.length);
            OutputStream os = exchange.getResponseBody();
            os.write(response);
            os.close();
        }
    }
}
