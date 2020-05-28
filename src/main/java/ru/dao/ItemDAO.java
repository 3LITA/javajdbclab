package ru.dao;

import java.util.ArrayList;

public interface ItemDAO {

    void initConnection();

    void close();

    MusicItem searchById(int id);

    ArrayList<MusicItem> searchByKeyword(String keyword);

    void create(MusicItem item);
}

