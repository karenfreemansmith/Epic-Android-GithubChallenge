package com.karenfreemansmith.githubchallenge.services;

import android.util.Log;

import com.karenfreemansmith.githubchallenge.models.Player;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Karen Freeman-Smith on 12/5/2016.
 */

public class GithubService {

    public static void getPlayers(String username, String type, Callback cb) {
        String githubUrl = "https://api.github.com/users/" + username + "/" + type;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(githubUrl)
                .build();

        Call call = client.newCall(request);
        call.enqueue(cb);
    }

    public ArrayList<Player> getPlayers(Response response) {
        ArrayList<Player> players = new ArrayList<>();

        try{
            String jsonData = response.body().string();
            if(response.isSuccessful()) {
                JSONArray playersJSON = new JSONArray(jsonData);
                for(int i=0; i<playersJSON.length(); i++) {
                    JSONObject playerJSON = playersJSON.getJSONObject(i);
                    String playerName = playerJSON.getString("login");
                    int playerID = playerJSON.getInt("id");
                    Player player = new Player(playerName, playerID);
                    players.add(player);
                }
            }
        } catch(IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return players;
    }
}
