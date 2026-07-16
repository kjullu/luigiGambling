import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
// import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
// import org.json.JSONException;
// import org.json.JSONObject;
import java.util.ArrayList;

public class GameServer {
    public static void main(String[] args) {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
            // For now, we attach one simple handler to /join so we can test it
            server.createContext("/draw", new Draw());

            server.setExecutor(null);
            server.start();
            System.out.println("Server started on port 8000");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class Draw implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if (!"GET".equals(exchange.getRequestMethod())) {
                String msg = "{\"error\": \"Only GET allowed\"}";
                sendJson(exchange, 405, msg);
                return;
            }

            GameEngine engine = new GameEngine(); 
            ArrayList<Integer> cards = new ArrayList<Integer>();
            cards = engine.makeCards();

            // For now, just echo back a fake playerId
            String response = "{\"status\": \"ok\", \"cards\": \"" + cards + "\"}";

            sendJson(exchange, 200, response);
        }
    }

    static void sendJson(HttpExchange exchange, int statusCode, String json) throws IOException {
        byte[] bytes = json.getBytes(StandardCharsets.UTF_8);

        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(statusCode, bytes.length);

        OutputStream os = exchange.getResponseBody();
        os.write(bytes);
        os.close();
    }
}
