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

    public static void getPlayer(String username, Callback cb) {
        String githubUrl = "https://api.github.com/users/" + username;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(githubUrl)
                .build();

        Call call = client.newCall(request);
        call.enqueue(cb);
    }

    public Player makePlayer(Response response) {
        Player player = new Player();
        try {
            String jsonData = response.body().string();
            if(response.isSuccessful()) {
                JSONObject jsonPlayer = new JSONObject(jsonData);

                player.setPlayerName(jsonPlayer.getString("login"));
                player.setPlayerId(jsonPlayer.getInt("id"));
                player.setPlayerRepos(jsonPlayer.getInt("public_repos"));
                player.setPlayerGists(jsonPlayer.getInt("public_gists"));
                player.setPlayerFollowers(jsonPlayer.getInt("followers"));
                player.setPlayerFavorites(jsonPlayer.getInt("following"));
                if(jsonPlayer.getString("bio") != null) {
                    player.setPlayerBio(true);
                } else {
                    player.setPlayerBio(false);
                }
                if(jsonPlayer.getString("company") != null) {
                    player.setPlayerCompany(true);
                } else {
                    player.setPlayerCompany(false);
                }
                if(jsonPlayer.getString("blog") != null) {
                    player.setPlayerBlog(true);
                } else {
                    player.setPlayerBlog(false);
                }
                if(jsonPlayer.getString("location") != null) {
                    player.setPlayerLocation(true);
                } else {
                    player.setPlayerLocation(false);
                }
                if(jsonPlayer.getString("email") != null) {
                    player.setPlayerEmail(true);
                } else {
                    player.setPlayerEmail(false);
                }
            }
        } catch(IOException | JSONException e) {
            e.printStackTrace();
        }

        return player;
    }

    public ArrayList<Player> getPlayerList(Response response) {
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
