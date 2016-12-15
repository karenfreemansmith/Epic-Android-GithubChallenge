package com.karenfreemansmith.githubchallenge.models;

import java.util.Random;

/**
 * Created by Guest on 12/9/16.
 */
public class Player {
    private String PlayerName;
    private int PlayerId;
    private int PlayerRepos;
    private int PlayerFollowers;
    private int PlayerFavorites;
    private int PlayerGists;
    private boolean PlayerEmail;
    private boolean PlayerBlog;
    private boolean PlayerCompany;
    private boolean PlayerBio;
    private boolean PlayerLocation;
    private String pushId;


    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }

    public Player(String name, int id) {
        PlayerName = name;
        PlayerId = id;

    }

    public Player () {
    }

    public String getPlayerName() {
        return PlayerName;
    }

    public void setPlayerName(String mPlayerName) {
        this.PlayerName = mPlayerName;
    }

    public int getPlayerId() {
        return PlayerId;
    }

    public void setPlayerId(int mPlayerId) {
        this.PlayerId = mPlayerId;
    }

    public int getPlayerRepos() {
        return PlayerRepos;
    }

    public void setPlayerRepos(int mPlayerRepos) {
        this.PlayerRepos = mPlayerRepos;
    }

    public int getPlayerFollowers() {
        return PlayerFollowers;
    }

    public void setPlayerFollowers(int mPlayerFollowers) {
        this.PlayerFollowers = mPlayerFollowers;
    }

    public int getPlayerFavorites() {
        return PlayerFavorites;
    }

    public void setPlayerFavorites(int mPlayerFavorites) {
        this.PlayerFavorites = mPlayerFavorites;
    }

    public int getPlayerGists() {
        return PlayerGists;
    }

    public void setPlayerGists(int mPlayerGists) {
        this.PlayerGists = mPlayerGists;
    }

    public boolean isPlayerEmail() {
        return PlayerEmail;
    }

    public void setPlayerEmail(boolean mPlayerEmail) {
        this.PlayerEmail = mPlayerEmail;
    }

    public boolean isPlayerBlog() {
        return PlayerBlog;
    }

    public void setPlayerBlog(boolean mPlayerBlog) {
        this.PlayerBlog = mPlayerBlog;
    }

    public boolean isPlayerCompany() {
        return PlayerCompany;
    }

    public void setPlayerCompany(boolean mPlayerCompany) {
        this.PlayerCompany = mPlayerCompany;
    }

    public boolean isPlayerBio() {
        return PlayerBio;
    }

    public void setPlayerBio(boolean mPlayerBio) {
        this.PlayerBio = mPlayerBio;
    }

    public boolean isPlayerLocation() {
        return PlayerLocation;
    }

    public void setPlayerLocation(boolean mPlayerLocation) {
        this.PlayerLocation = mPlayerLocation;
    }

    public static Player getRandomStarter() {
        Random randomGenerator = new Random();
        String[] names = {"wycats", "brianredbeard", "karenfreemansmith", "ejlaw01", "PerrySetGo", "dacravey",
            "SummerBr", "mzphittstudios", "michaelrkn", "mgoren", "ericelliott", "sajoy", "hdngr", "chalkers",
            "yqedan", "craigdennis", "Pyrrus", "benjakuben", "JKonTiki", "nickpettit", "jillkuchman",
            "courtneyphillips", "jfranti", "jrjamespdx", "kdv24", "dianedouglas", "tcsuder", "ewajm",
            "eeronomicon", "helloapro"};
        int[] ids = {4, 57542, 6623141, 20765651, 11791430, 8962975, 12618246, 10455028, 1266530, 10257491,
            364727, 8292389, 1875033, 9805, 2849864, 8494909, 4381257, 794480, 20776648, 41124, 11277133,
            8429820, 11276180, 12029466, 6107653, 6743582, 16562093, 13293915, 20058281, 18670615};
        int rand = randomGenerator.nextInt(30);

        Player randPlayer = new Player();

        randPlayer.setPlayerName(names[rand]);
        randPlayer.setPlayerId(ids[rand]);

        return randPlayer;
    }
}
