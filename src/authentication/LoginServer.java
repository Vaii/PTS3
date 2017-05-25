package authentication;

import authentication.repository.LoginMongoContext;
import authentication.repository.LoginRepository;
import com.mongodb.util.JSON;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import domain.Config;
import domain.User;
import javafx.scene.web.WebView;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Vai on 5/25/17.
 */
public class LoginServer {

    private Map<String, String> parms;
    private HttpServer server;
    private int port = 44300;
    private LoginRepository lRepo;
    private WebView auth;
    private FHICTController contr;

    public LoginServer(LoginRepository lRepo, WebView auth, FHICTController contr) throws IOException {
        this.lRepo = lRepo;
        this.auth = auth;
        this.contr = contr;
        server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/", new EchoPostHandler());
        server.start();
    }

    public class EchoPostHandler implements HttpHandler{

        @Override
        public void handle(HttpExchange httpExchange) throws IOException {

            String response = auth.getEngine().getLocation();
            String[] splitResponse = response.split("#");
            Map<String, String> result = new HashMap<>();
            for(String param: splitResponse[1].split("&")){
                String pair[] = param.split("=");
                if(pair.length > 1){
                    result.put(pair[0], pair[1]);
                }
                else{
                    result.put(pair[0], "");
                }
            }

            if(result.size() != 0){
                Config.setAccesToken(result.get("access_token"));

                server.stop(0);
                contr.succesfullToken();
            }

        }
    }
}
