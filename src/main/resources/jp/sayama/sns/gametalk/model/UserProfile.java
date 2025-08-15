package jp.sayama.sns.gametalk.model;


public class UserProfile {
    private String name;
    private String games;
    private String comment;


    
    public UserProfile(String name, String games, String comment) {
        this.name = name;
        this.games = games;
        this.comment = comment;
    }

    public String getName() {
        return name;
    }

    public String getGames() {
        return games;
    }

    public String getComment() {
        return comment;
    }
}