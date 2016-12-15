package com.karenfreemansmith.githubchallenge.models;

import com.karenfreemansmith.githubchallenge.Constants;

import java.util.Random;

/**
 * Created by Guest on 12/9/16.
 */
public class Player {
    private String mPlayerName;
    private int mPlayerId;
    private int mPlayerRepos;
    private int mPlayerFollowers;
    private int mPlayerFavorites;
    private int mPlayerGists;
    private boolean mPlayerEmail;
    private boolean mPlayerBlog;
    private boolean mPlayerCompany;
    private boolean mPlayerBio;
    private boolean mPlayerLocation;



    public Player(String name, int id) {
        mPlayerName = name;
        mPlayerId = id;
    }

    public Player () {
    }

    public String getPlayerName() {
        return mPlayerName;
    }

    public void setPlayerName(String mPlayerName) {
        this.mPlayerName = mPlayerName;
    }

    public int getPlayerId() {
        return mPlayerId;
    }

    public void setPlayerId(int mPlayerId) {
        this.mPlayerId = mPlayerId;
    }

    public int getPlayerRepos() {
        return mPlayerRepos;
    }

    public void setPlayerRepos(int mPlayerRepos) {
        this.mPlayerRepos = mPlayerRepos;
    }

    public int getPlayerFollowers() {
        return mPlayerFollowers;
    }

    public void setPlayerFollowers(int mPlayerFollowers) {
        this.mPlayerFollowers = mPlayerFollowers;
    }

    public int getPlayerFavorites() {
        return mPlayerFavorites;
    }

    public void setPlayerFavorites(int mPlayerFavorites) {
        this.mPlayerFavorites = mPlayerFavorites;
    }

    public int getPlayerGists() {
        return mPlayerGists;
    }

    public void setPlayerGists(int mPlayerGists) {
        this.mPlayerGists = mPlayerGists;
    }

    public boolean isPlayerEmail() {
        return mPlayerEmail;
    }

    public void setPlayerEmail(boolean mPlayerEmail) {
        this.mPlayerEmail = mPlayerEmail;
    }

    public boolean isPlayerBlog() {
        return mPlayerBlog;
    }

    public void setPlayerBlog(boolean mPlayerBlog) {
        this.mPlayerBlog = mPlayerBlog;
    }

    public boolean isPlayerCompany() {
        return mPlayerCompany;
    }

    public void setPlayerCompany(boolean mPlayerCompany) {
        this.mPlayerCompany = mPlayerCompany;
    }

    public boolean isPlayerBio() {
        return mPlayerBio;
    }

    public void setPlayerBio(boolean mPlayerBio) {
        this.mPlayerBio = mPlayerBio;
    }

    public boolean isPlayerLocation() {
        return mPlayerLocation;
    }

    public void setPlayerLocation(boolean mPlayerLocation) {
        this.mPlayerLocation = mPlayerLocation;
    }

    public static Player getRandomStarter() {
        Random randomGenerator = new Random();
        String[] names = {"wycats", "brianredbeard", "karenfreemansmith", "ejlaw01", "PerrySetGo", "dacravey",
            "SummerBr", "mzphittstudios", "michaelkn", "mgoren", "ericelliott", "sajoy", "hdngr", "chalkers",
            "yqedan", "craigdennis", "Pyrrus", "benjakuben", "JKonTiki", "nickpettit", "jillkuchman",
            "courtneyphillips", "jfranti", "jrjamespdx", "kdv24", "dianedouglass", "tcsuder", "ewajm",
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
