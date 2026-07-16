import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import org.json.JSONException;
import org.json.JSONObject;

public class GameServer {

    public static void main(String[] args) {
        try {
            HttpServer server = HttpServer.create(
                new InetSocketAddress(8000),
                0
            );
            GameEngine engine = new GameEngine();

            // For now, we attach one simple handler to /join so we can test it
            server.createContext("/join", new JoinHandler(engine));
            server.createContext("/status", new Status());
            server.createContext("/draw", new Draw());

            server.setExecutor(null);
            server.start();
            System.out.println("Server started on port 8000");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // A simple inner handler for testing POST /join
    static class JoinHandler implements HttpHandler {

        private GameEngine engine;

        public JoinHandler(GameEngine engine) {
            this.engine = engine;
        }

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            // Only allow POST
            if (!"POST".equals(exchange.getRequestMethod())) {
                String msg = "{\"error\": \"Only POST allowed\"}";
                sendJson(exchange, 405, msg);
                return;
            }

            // Read the request body
            String body;
            try (InputStream is = exchange.getRequestBody()) {
                body = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            }
            System.out.println("Received: " + body);
            String name;
            try {
                JSONObject json = new JSONObject(body);
                name = json.getString("name");
            } catch (JSONException e) {
                sendJson(exchange, 400, "\"{error\": \"Not JSON\"}");
                return;
            }

            int playerId = engine.addPlayer(name);

            // For now, just echo back a fake playerId
            String response = "{\"playerId\": 1, \"status\": \"waiting\"}";

            sendJson(exchange, 200, response);
        }
    }

    static class Status implements HttpHandler {

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if (!"GET".equals(exchange.getRequestMethod())) {
                String msg = "{\"error\": \"Only GET allowed\"}";
                sendJson(exchange, 405, msg);
                return;
            }

            // For now, just echo back a fake playerId
            String response = "{\"playerId\": 1, \"status\": \"waiting\"}";
            System.out.println("Status gotten");

            sendJson(exchange, 200, response);
        }
    }

    static class Draw implements HttpHandler {

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            // Only allow POST
            if (!"POST".equals(exchange.getRequestMethod())) {
                String msg = "{\"error\": \"Only POST allowed\"}";
                sendJson(exchange, 405, msg);
                return;
            }

            // For now, just echo back a fake playerId
            String response = "{\"playerId\": 1, \"status\": \"waiting\"}";
            System.out.println("Status gotten");

            sendJson(exchange, 200, response);
        }
    }

    static void sendJson(HttpExchange exchange, int statusCode, String json)
        throws IOException {
        byte[] bytes = json.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(statusCode, bytes.length);
        OutputStream os = exchange.getResponseBody();
        os.write(bytes);
        os.close();
    }
}
