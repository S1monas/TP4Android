package com.example.simonas.tp4android.model;

/**
 * Created by Simonas on 2018.04.04.
 */

public class Tournament {
    private int gameid;
    private String game;
    private String user;
    private String format;
    private String currency;
    private String buyin;
    private double result;

    @Override
    public String toString() {
        return "Tournament{" +
                "gameid=" + gameid +
                ", game='" + game + '\'' +
                ", format='" + format + '\'' +
                ", currency='" + currency + '\'' +
                ", buyin='" + buyin + '\'' +
                ", result=" + result +
                '}';
    }

    public Tournament () {
    }

    //4 JUnit
    public Tournament (String game, String format, String currency, String buyin, double result) {
        this.game = game;
        this.format = format;
        this.currency = currency;
        this.buyin = buyin;
        this.result = result;
    }

    public Tournament (int gameid, String game, String user, String format, String currency, String buyin, double result) {
        this.gameid = gameid;
        this.game = game;
        //this.user = user;
        this.format = format;
        this.currency = currency;
        this.buyin = buyin;
        this.result = result;

    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public int getGameid() {
        return gameid;
    }

    public void setGameid(int gameid) {
        this.gameid = gameid;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getBuyin() {
        return buyin;
    }

    public void setBuyin(String buyin) {
        this.buyin = buyin;
    }

    public double getResult() {
        return result;
    }

    public void setResult(double result) {
        this.result = result;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Tournament) {
            Tournament tournament = (Tournament) o;
            boolean game = tournament.getGame().equals(getGame());
            boolean format = tournament.getFormat().equals(getFormat());
            boolean currency = tournament.getCurrency().equals(getCurrency());
            boolean buyin = tournament.getBuyin().equals(getBuyin());
            boolean result = Double.compare(tournament.getResult(),getResult())==0;
            return (game
                    &&format
                    &&currency
                    &&buyin
                    &&result);
        }
        return false;
    }
}

