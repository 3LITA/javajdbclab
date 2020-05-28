package ru.dao;

import java.util.Date;

public class MusicItem {

    private int id;
    private String title;
    private String artist;
    private Date releaseDate;
    private double listPrice;
    private double price;
    private int version;

    public MusicItem(String title, String artist, Date releaseDate, double listPrice, double price, int version) {
        this.title = title;
        this.artist = artist;
        this.releaseDate = releaseDate;
        this.listPrice = listPrice;
        this.price = price;
        this.version = version;
    }

    public MusicItem(int id, String title, String artist, Date releaseDate, double listPrice, double price, int version) {
        this(title, artist, releaseDate, listPrice, price, version);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public double getListPrice() {
        return listPrice;
    }

    public double getPrice() {
        return price;
    }

    public int getVersion() {
        return version;
    }

    @Override
    public String toString() {
        return "<MusicItem> {" + '\n' +
                "\tid=" + id + "," + '\n' +
                "\ttitle=" + title + "," + '\n' +
                "\tartist=" + artist + "," + '\n' +
                "\tdate=" + releaseDate + '\n' +
                "\tlistPrice=" + listPrice + '\n' +
                '}';
    }
}