package authentication.repository;

import domain.Config;
import domain.User;
import domain.UserType;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Vai on 5/21/17.
 */
public class LoginRepository {

    private ILoginContext loginContext;

    public LoginRepository(ILoginContext loginContext){
        this.loginContext = loginContext;
    }

    public User loginuser(User user){
        return loginContext.loginUser(user);
    }

    public User getAuthorizedUser(String token) {

        try {

            String url = "https://api.fhict.nl/people/me";

            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            con.setRequestMethod("GET");
            con.setRequestProperty("Authorization", "Bearer " + token);

            int responsecode = con.getResponseCode();


            if(responsecode == 200){

                BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream())
                );
                String output;
                StringBuffer response = new StringBuffer();

                while ((output = in.readLine()) != null) {
                    response.append(output);
                }

                in.close();


                JSONObject object = new JSONObject(response.toString());

                JSONArray temp = object.getJSONArray("affiliations");
                int length = temp.length();
                String[] affiliations = new String[length];

                if(length > 0){
                    for(int i = 0; i < length; i++){
                        affiliations[i] = temp.getString(i);
                    }
                }


                User user = new User(object.get("givenName") + " " + object.get("surName"),
                        UserType.valueOf(affiliations[1]),
                        object.getString("id"));


                return user;

            }
            else if(responsecode == 403){

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Login error");
                        alert.setHeaderText("Er ging iets mis bij het inloggen");
                        alert.setContentText("Zorg er voor dat je de applicatie toestaat om data in te zien.");
                        alert.show();

                    }
                });

                return null;
            }
            else if(responsecode == 401){

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Login error");
                        alert.setHeaderText("Er ging iets mis bij het inloggen");
                        alert.setContentText("Probeer opnieuw in te loggen");
                        alert.show();
                    }
                });

                return null;
            }

        }
        catch(IOException e) {
            e.printStackTrace();
        }
        catch(JSONException e){
            e.printStackTrace();
        }
        return null;
    }

}
