package com.example.psasa.Model;

public class BetHistory {
    private String game;
    private String time;
    private String Status;
    private String boxPlaced;
    private String boxWon;
    private long amount;
    private String winnings;

    public BetHistory() {
    }

    public BetHistory(String game, String time, String status, String boxPlaced, String boxWon, long amount, String winnings) {
        this.game = game;
        this.time = time;
        Status = status;
        this.boxPlaced = boxPlaced;
        this.boxWon = boxWon;
        this.amount = amount;
        this.winnings = winnings;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public void setBoxPlaced(String boxPlaced) {
        this.boxPlaced = boxPlaced;
    }

    public void setBoxWon(String boxWon) {
        this.boxWon = boxWon;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public void setWinnings(String winnings) {
        this.winnings = winnings;
    }

    public String getGame() {
        return game;
    }

    public String getTime() {
        return time;
    }

    public String getStatus() {
        return Status;
    }

    public String getBoxPlaced() {
        return boxPlaced;
    }

    public String getBoxWon() {
        return boxWon;
    }

    public long getAmount() {
        return amount;
    }

    public String getWinnings() {
        return winnings;
    }

    @Override
    public String toString() {
        return "BetHistory{" +
                "game='" + game + '\'' +
                ", time='" + time + '\'' +
                ", Status='" + Status + '\'' +
                ", boxPlaced='" + boxPlaced + '\'' +
                ", boxWon='" + boxWon + '\'' +
                ", amount='" + amount + '\'' +
                ", winnings='" + winnings + '\'' +
                '}';
    }
}
