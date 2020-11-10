package me.rages.antiaccountshare.player;

public class PlayerData {

    private long verifiedTime;
    private String address;

    public PlayerData(long verifiedTime, String address) {
        this.verifiedTime = verifiedTime;
        this.address = address;
    }

    public long getVerifiedTime() {
        return verifiedTime;
    }

    public String getAddress() {
        return address;
    }
}
