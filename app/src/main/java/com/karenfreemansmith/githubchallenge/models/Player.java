package com.karenfreemansmith.githubchallenge.models;

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

    public Player(String name) {
        mPlayerName = name;
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
}
